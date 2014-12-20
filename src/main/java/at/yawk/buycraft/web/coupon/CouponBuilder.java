package at.yawk.buycraft.web.coupon;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

/**
 * @author yawkat
 */
public class CouponBuilder {
    private static final Random RNG = new Random();
    private static final String CODE_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Set<BasketType> basketTypes = EnumSet.allOf(BasketType.class);

    private EffectType effectType = EffectType.CART;
    private int[] categoryIds;
    private int[] packages;

    private String code = null;

    private DiscountType discountType = DiscountType.AMOUNT;
    private double discountAmount = 0.00;
    private double discountFraction;

    private ExpiryType expiryType = ExpiryType.NEVER;
    private LocalDate expiryDate;
    private int expiryRedeemCount;

    private double minimumBasketValue = 0.00;
    private LocalDate startDate = null;
    private int maximumUserUses = 0;

    public CouponBuilder basketType(Set<BasketType> basketTypes) {
        if (basketTypes.isEmpty()) {
            throw new IllegalArgumentException("Need at least one basket type");
        }
        this.basketTypes = EnumSet.copyOf(basketTypes);
        return this;
    }

    public CouponBuilder affectCategories(int... categoryIds) {
        this.categoryIds = categoryIds;
        effectType = EffectType.CATEGORIES;
        return this;
    }

    public CouponBuilder affectPackages(int... packages) {
        this.packages = packages;
        effectType = EffectType.PACKAGES;
        return this;
    }

    public CouponBuilder affectEverything() {
        effectType = EffectType.CART;
        return this;
    }

    public CouponBuilder code(String code) {
        this.code = code;
        return this;
    }

    public CouponBuilder discountAmount(double amount) {
        this.discountAmount = amount;
        discountType = DiscountType.AMOUNT;
        return this;
    }

    public CouponBuilder discountFraction(double fraction) {
        this.discountFraction = fraction;
        discountType = DiscountType.PERCENTAGE;
        return this;
    }

    public CouponBuilder expireNever() {
        expiryType = ExpiryType.NEVER;
        return this;
    }

    public CouponBuilder expireOn(LocalDate date) {
        expiryType = ExpiryType.DATE;
        this.expiryDate = date;
        return this;
    }

    public CouponBuilder expireAfterRedeemed(int maximumRedeemCount) {
        expiryType = ExpiryType.REDEEM_COUNT;
        this.expiryRedeemCount = maximumRedeemCount;
        return this;
    }

    public CouponBuilder minimumBasketValue(double minimumValue) {
        this.minimumBasketValue = minimumValue;
        return this;
    }

    public CouponBuilder startDate(LocalDate date) {
        startDate = date;
        return this;
    }

    public CouponBuilder maximumUsesPerUser(int maximum) {
        maximumUserUses = maximum;
        return this;
    }

    public Coupon build() {
        String code = this.code;
        if (code == null) {
            StringBuilder codeBuilder = new StringBuilder(20);
            for (int i = 0; i < 20; i++) {
                codeBuilder.append(CODE_CHARACTERS.charAt(RNG.nextInt(CODE_CHARACTERS.length())));
            }
            code = codeBuilder.toString();
        }
        LocalDate startDate = this.startDate;
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        return new Coupon(
                basketTypes,
                effectType,
                categoryIds,
                packages,
                code,
                discountType,
                discountAmount,
                discountFraction,
                expiryType,
                expiryDate,
                expiryRedeemCount,
                minimumBasketValue,
                startDate,
                maximumUserUses
        );
    }
}
