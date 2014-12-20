package at.yawk.buycraft.web.coupon;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

/**
 * @author yawkat
 */
public class Coupon {
    private final Set<BasketType> basketTypes; // non-empty

    private final EffectType effectType;
    private final int[] categoryIds;
    private final int[] packages;

    private final String code;

    private final DiscountType discountType;
    private final double discountAmount;
    private final double discountFraction;

    private final ExpiryType expiryType;
    private final LocalDate expiryDate;
    private final int expiryRedeemCount;

    private final double minimumBasketValue;
    private final LocalDate startDate;
    private final int maximumUserUses; // 0 = unlimited

    Coupon(
            Set<BasketType> basketTypes,
            EffectType effectType,
            int[] categoryIds,
            int[] packages,
            String code,
            DiscountType discountType,
            double discountAmount,
            double discountFraction,
            ExpiryType expiryType,
            LocalDate expiryDate,
            int expiryRedeemCount,
            double minimumBasketValue,
            LocalDate startDate,
            int maximumUserUses
    ) {
        this.basketTypes = Collections.unmodifiableSet(basketTypes);
        this.effectType = effectType;
        this.categoryIds = categoryIds;
        this.packages = packages;
        this.code = code;
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.discountFraction = discountFraction;
        this.expiryType = expiryType;
        this.expiryDate = expiryDate;
        this.expiryRedeemCount = expiryRedeemCount;
        this.minimumBasketValue = minimumBasketValue;
        this.startDate = startDate;
        this.maximumUserUses = maximumUserUses;
    }

    public Set<BasketType> getBasketTypes() {
        return basketTypes;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public int[] getCategoryIds() {
        if (effectType != EffectType.CATEGORIES) {
            throw new IllegalStateException("Effect type is not CATEGORIES!");
        }
        return categoryIds;
    }

    public int[] getPackages() {
        if (effectType != EffectType.PACKAGES) {
            throw new IllegalStateException("Effect type is not CATEGORIES!");
        }
        return packages;
    }

    public ExpiryType getExpiryType() {
        return expiryType;
    }

    public LocalDate getExpiryDate() {
        if (expiryType != ExpiryType.DATE) {
            throw new IllegalStateException("Expiry type is not DATE!");
        }
        return expiryDate;
    }

    public int getExpiryRedeemCount() {
        if (expiryType != ExpiryType.REDEEM_COUNT) {
            throw new IllegalStateException("Expiry type is not REDEEM_COUNT!");
        }
        return expiryRedeemCount;
    }

    public String getCode() {
        return code;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getDiscountAmount() {
        if (discountType != DiscountType.AMOUNT) {
            throw new IllegalStateException("Discount type is not AMOUNT!");
        }
        return discountAmount;
    }

    public double getDiscountFraction() {
        if (discountType != DiscountType.PERCENTAGE) {
            throw new IllegalStateException("Discount type is not PERCENTAGE!");
        }
        return discountFraction;
    }

    public double getMinimumBasketValue() {
        return minimumBasketValue;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getMaximumUserUses() {
        return maximumUserUses;
    }
}
