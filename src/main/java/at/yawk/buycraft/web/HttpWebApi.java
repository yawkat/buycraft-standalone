/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package at.yawk.buycraft.web;

import at.yawk.buycraft.web.coupon.BasketType;
import at.yawk.buycraft.web.coupon.Coupon;
import at.yawk.buycraft.web.coupon.CouponBuilder;
import at.yawk.buycraft.web.coupon.CouponException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;

/**
 * @author yawkat
 */
public class HttpWebApi implements BuycraftWebApi {
    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.DAY_OF_MONTH)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR)
            .appendLiteral('-')
            .appendValue(ChronoField.YEAR)
            .toFormatter();

    private final CookieStore cookieStore = new BasicCookieStore();
    private final HttpClient client = HttpClientBuilder.create()
            .setDefaultCookieStore(cookieStore)
            .setHostnameVerifier(new AbstractVerifier() {
                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) {}
            })
            .setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                protected boolean isRedirectable(final String method) {
                    return false;
                }
            })
            .build();

    public HttpWebApi() {}

    public void setCookie(String k, String v) {
        cookieStore.addCookie(new BasicClientCookie(k, v));
    }

    public void setTransientSessionId(String sessionId) {
        setCookie("PHPSESSID", sessionId);
    }

    public void setRememberSessionId(String sessionId) {
        setCookie("rememberme", sessionId);
    }

    private String requestCsrf() throws IOException, LoginException {
        HttpGet request = new HttpGet("https://server.buycraft.net/");
        HttpResponse response = client.execute(request);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        response.getEntity().writeTo(stream);
        String doc = stream.toString("UTF-8");
        int start = doc.indexOf("name=\"csrf\" value=\"") + 19;
        return doc.substring(start, doc.indexOf('"', start));
    }

    @Override
    public void login(String email, String password, boolean remember) throws IOException, LoginException {
        String csrf = requestCsrf();
        login(csrf, email, password, remember);
    }

    public void login(String csrfToken, String email, String password, boolean remember)
            throws IOException, LoginException {
        HttpPost request = new HttpPost("https://server.buycraft.net/index/submit");
        request.setEntity(new UrlEncodedFormEntity(Arrays.asList(
                new BasicNameValuePair("csrf", csrfToken),
                new BasicNameValuePair("email", email),
                new BasicNameValuePair("password", password),
                new BasicNameValuePair("rememberme", remember ? "1" : "0")
        )));
        HttpResponse response = client.execute(request);
        response.getEntity().writeTo(DiscardingOutputStream.instance);
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY) {
            throw new LoginException.BadCredentialsException("Not 302: " + response.getStatusLine());
        }
    }

    @Override
    public void selectWebStore(int store) throws IOException, LoginException {
        HttpGet get = new HttpGet("https://server.buycraft.net/credential/login/" + store);
        HttpResponse response = client.execute(get);
        Header location = response.getFirstHeader("Location");
        response.getEntity().writeTo(DiscardingOutputStream.instance);
        if (location == null || !location.getValue().equals("https://server.buycraft.net/dashboard")) {
            throw new LoginException("Invalid store ID");
        }
    }

    private String loadCouponCsrf() throws IOException {
        HttpGet request = new HttpGet("https://server.buycraft.net/coupons/add");
        HttpResponse response = client.execute(request);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        response.getEntity().writeTo(stream);
        String doc = stream.toString("UTF-8");

        int start = doc.indexOf("name=\"csrf\" value=\"") + 19;
        return doc.substring(start, doc.indexOf('"', start));
    }

    @Override
    public void registerCoupon(Coupon coupon) throws IOException, CouponException {
        List<NameValuePair> reqPairs = new ArrayList<>();

        String csrf = loadCouponCsrf();
        reqPairs.add(new BasicNameValuePair("csrf", csrf));

        if (coupon.getBasketTypes().contains(BasketType.SINGLE)) {
            if (coupon.getBasketTypes().contains(BasketType.SUBSCRIPTION)) {
                reqPairs.add(new BasicNameValuePair("basket_type", "both"));
            } else {
                reqPairs.add(new BasicNameValuePair("basket_type", "subscription"));
            }
        } else {
            // without basket type would be invalid, should be checked in CouponBuilder
            reqPairs.add(new BasicNameValuePair("basket_type", "single"));
        }
        reqPairs.add(new BasicNameValuePair("code", coupon.getCode()));
        switch (coupon.getDiscountType()) {
        case AMOUNT:
            reqPairs.add(new BasicNameValuePair("discount_type", "value"));
            reqPairs.add(new BasicNameValuePair("discount_amount", String.format("%.2f", coupon.getDiscountAmount())));
            reqPairs.add(new BasicNameValuePair("discount_percentage", "0"));
            break;
        case PERCENTAGE:
            reqPairs.add(new BasicNameValuePair("discount_type", "percentage"));
            reqPairs.add(new BasicNameValuePair("discount_amount", "0.00"));
            reqPairs.add(new BasicNameValuePair("discount_percentage",
                                                Double.toString(coupon.getDiscountFraction() * 100)));
            break;
        }

        switch (coupon.getExpiryType()) {
        case NEVER:
            reqPairs.add(new BasicNameValuePair("expire_type", "never"));
            reqPairs.add(new BasicNameValuePair("expire_limit", "0"));
            reqPairs.add(new BasicNameValuePair("expire_timestamp", LocalDate.now().format(DATE_FORMATTER)));
            break;
        case DATE:
            reqPairs.add(new BasicNameValuePair("expire_type", "timestamp"));
            reqPairs.add(new BasicNameValuePair("expire_timestamp", coupon.getExpiryDate().format(DATE_FORMATTER)));
            reqPairs.add(new BasicNameValuePair("expire_limit", "0"));
            break;
        case REDEEM_COUNT:
            reqPairs.add(new BasicNameValuePair("expire_type", "limit"));
            reqPairs.add(new BasicNameValuePair("expire_limit", Integer.toString(coupon.getExpiryRedeemCount())));
            reqPairs.add(new BasicNameValuePair("expire_timestamp", LocalDate.now().format(DATE_FORMATTER)));
            break;
        }

        reqPairs.add(new BasicNameValuePair("minimum", String.format("%.2f", coupon.getMinimumBasketValue())));
        reqPairs.add(new BasicNameValuePair("start_time", coupon.getStartDate().format(DATE_FORMATTER)));
        reqPairs.add(new BasicNameValuePair("user_limit", Integer.toString(coupon.getMaximumUserUses())));

        switch (coupon.getEffectType()) {
        case CART:
            reqPairs.add(new BasicNameValuePair("type", "cart"));
            break;
        case CATEGORIES:
            for (int category : coupon.getCategoryIds()) {
                reqPairs.add(new BasicNameValuePair("categories[]", Integer.toString(category)));
            }
            reqPairs.add(new BasicNameValuePair("type", "category"));
            break;
        case PACKAGES:
            for (int packageId : coupon.getPackages()) {
                reqPairs.add(new BasicNameValuePair("packages[]", Integer.toString(packageId)));
            }
            reqPairs.add(new BasicNameValuePair("type", "package"));
            break;
        }

        HttpPost request = new HttpPost("https://server.buycraft.net/coupons/add");
        request.addHeader("X-Requested-With", "XMLHttpRequest");
        request.setEntity(new UrlEncodedFormEntity(reqPairs));
        HttpResponse response = client.execute(request);

        HttpEntity entity = response.getEntity();
        Header encoding = entity.getContentEncoding();
        JsonObject json = new JsonParser().parse(new InputStreamReader(
                entity.getContent(),
                encoding == null ? "UTF-8" : encoding.getValue()
        )).getAsJsonObject();

        if (json.get("type").getAsString().equals("error")) {
            throw new CouponException(json.get("message").toString());
        }
    }
}
