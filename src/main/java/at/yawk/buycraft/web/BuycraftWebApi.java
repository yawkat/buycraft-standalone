/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

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
