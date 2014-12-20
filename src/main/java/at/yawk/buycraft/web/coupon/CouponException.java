package at.yawk.buycraft.web.coupon;

/**
 * @author yawkat
 */
public class CouponException extends Exception {
    public CouponException(String message) {
        super(message);
    }

    public CouponException(String message, Throwable cause) {
        super(message, cause);
    }
}
