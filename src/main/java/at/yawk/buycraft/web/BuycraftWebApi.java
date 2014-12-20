package at.yawk.buycraft.web;

import at.yawk.buycraft.web.coupon.Coupon;
import at.yawk.buycraft.web.coupon.CouponException;
import java.io.IOException;

/**
 * @author yawkat
 */
public interface BuycraftWebApi {
    void registerCoupon(Coupon coupon) throws IOException, CouponException;

    void login(String email, String password, boolean remember) throws IOException, LoginException;

    void selectWebStore(int store) throws IOException, LoginException;
}
