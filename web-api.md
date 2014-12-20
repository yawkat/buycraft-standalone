Web API
=======

The `at.yawk.buycraft.web` API is an api for some functions of the buycraft administration interface.

```
HttpWebApi api = new HttpWebApi();
api.login(username, password, true);
// This is the "web store" you select on login.
// Only needs to be added if you get prompted to select
// it when logging in with the credentials used above.
// This ID appears in the URL the "login" button in that
// selection links to.
api.selectWebStore(123456);
```

Coupons
-------

```
Coupon coupon = new CouponBuilder()
                    .minimumBasketValue(0.50);
                    .build();
api.registerCoupon(coupon);
```

Note that you need to use the `build` method again for the next coupon to get a different coupon code.