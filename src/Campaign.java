public class Campaign {
    private Category category;
    private double discount;
    private int minimumQuantity;
    private DiscountType discountType;

    Campaign() {
        category = null;
        discount = 0;
        minimumQuantity = 0;
        discountType = DiscountType.Rate;
    }

    Campaign(Category category, double discount, int minimumQuantity, DiscountType discountType) {
        this.category = category;
        this.discount = discountType == DiscountType.Rate ? discount / 100.0 : discount;
        this.minimumQuantity = minimumQuantity;
        this.discountType = discountType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
