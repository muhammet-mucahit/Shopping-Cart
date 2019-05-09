public class Coupon {
    private int minimumPurchase;
    private double discount;
    private DiscountType discountType;

    Coupon(int minimumPurchase, int discount, DiscountType discountType) {
        this.minimumPurchase = minimumPurchase;
        this.discount = discountType == DiscountType.Rate ? discount / 100.0 : discount;
        this.discountType = discountType;
    }

    public int getMinimumPurchase() {
        return minimumPurchase;
    }

    public void setMinimumPurchase(int minimumPurchase) {
        this.minimumPurchase = minimumPurchase;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
