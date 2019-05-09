import java.util.HashMap;

public class ShoppingCart {
    private HashMap<Product, Integer> products;
    private HashMap<Category, Integer> quantityByCategories;
    private HashMap<Category, Double> priceByCategories;

    private double totalPrice;

    private double couponDiscount;
    private double campaignDiscount;

    private int totalNumberOfProducts;
    private int totalNumberOfDeliveries;

    ShoppingCart() {
        products = new HashMap<>();
        quantityByCategories = new HashMap<>();
        priceByCategories = new HashMap<>();

        totalPrice = 0;
        totalNumberOfProducts = 0;
        totalNumberOfDeliveries = 0;
        couponDiscount = 0;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public int getTotalNumberOfProducts() {
        return totalNumberOfProducts;
    }

    public int getTotalNumberOfDeliveries() {
        return totalNumberOfDeliveries;
    }

    // Adds item to the shopping card
    // On background, groups products info by their categories
    public void addItem(Product product, int quantity) {

        if (!products.containsKey(product)) {
            products.put(product, quantity);
        }
        else {
            products.replace(product, products.get(product) + quantity);
        }

        totalPrice += product.getPrice() * quantity;
        totalNumberOfProducts = products.size();

        calculatePricesByCategories(product, quantity);
        calculateQuantitiesByCategories(product, quantity);
        totalNumberOfDeliveries = quantityByCategories.size();
    }

    // Set prices of parent category in grouping category
    private void setPricesAboutParentCategory(Product product, int quantity) {
        if (product.getCategory().hasParentCategory()) {
            if (!priceByCategories.containsKey(product.getCategory().getParentCategory())) {
                priceByCategories.put(product.getCategory().getParentCategory(), quantity * product.getPrice());
            }
            else {
                double newPriceByParentCategory =
                        priceByCategories.get(product.getCategory().getParentCategory()) +
                        quantity * product.getPrice();
                priceByCategories.replace(product.getCategory().getParentCategory(), newPriceByParentCategory);
            }
        }
    }

    // Set quantities of parent category in grouping category
    private void setQuantitiesAboutParentCategory(Product product, int quantity) {
        if (product.getCategory().hasParentCategory()) {
            if (!quantityByCategories.containsKey(product.getCategory().getParentCategory())) {
                quantityByCategories.put(product.getCategory().getParentCategory(), quantity);
            }
            else {
                int newQuantityByParentCategory = quantityByCategories.get(product.getCategory().getParentCategory()) + quantity;
                quantityByCategories.replace(product.getCategory().getParentCategory(), newQuantityByParentCategory);
            }
        }
    }

    // Calculates prices of all categories by grouping products
    private void calculatePricesByCategories(Product product, int quantity) {
        if (!priceByCategories.containsKey(product.getCategory())) {
            priceByCategories.put(product.getCategory(), quantity * product.getPrice());
            setPricesAboutParentCategory(product, quantity);
        }
        else {
            double newPrice = priceByCategories.get(product.getCategory()) + quantity * product.getPrice();
            priceByCategories.replace(product.getCategory(), newPrice);
            setPricesAboutParentCategory(product, quantity);
        }
    }

    // Calculates quantities of all categories by grouping products
    private void calculateQuantitiesByCategories(Product product, int quantity) {
        if (!quantityByCategories.containsKey(product.getCategory())) {
            quantityByCategories.put(product.getCategory(), quantity);
            setQuantitiesAboutParentCategory(product, quantity);
        }
        else {
            int newQuantity = quantityByCategories.get(product.getCategory()) + quantity;
            quantityByCategories.replace(product.getCategory(), newQuantity);
            setQuantitiesAboutParentCategory(product, quantity);
        }
    }

    // Choose best discount and apply it to the shopping cart
    // Apply discount only for related category
    public void applyDiscounts(Campaign... campaigns) {
        double maxDiscount = 0;

        for (var campaign : campaigns) {
            if (quantityByCategories.get(campaign.getCategory()) >= campaign.getMinimumQuantity()) {
                var discount = campaign.getDiscountType() == DiscountType.Rate ?
                        priceByCategories.get(campaign.getCategory()) * campaign.getDiscount() :
                        campaign.getDiscount();

                if (discount > maxDiscount) {
                    maxDiscount = discount;
                }
            }
        }

        campaignDiscount = maxDiscount;
    }

    // Apply coupon to the shopping card
    public void applyCoupon(Coupon coupon) {
        // First Campaign Applied
        if (getTotalPrice() - campaignDiscount > coupon.getMinimumPurchase()) {
            couponDiscount = coupon.getDiscountType() == DiscountType.Rate ?
                    (getTotalPrice() - campaignDiscount) * coupon.getDiscount() :
                    coupon.getDiscount();
        }
        else {
            System.err.println("The coupon is not applicable! The purchase is not sufficient!");
        }
    }

    public double getTotalPrice() { return totalPrice; }

    public double getTotalAmountAfterDiscounts() { return totalPrice - (couponDiscount + campaignDiscount); }

    public double getCouponDiscount() { return couponDiscount; }

    public double getCampaignDiscount() { return campaignDiscount; }

    public double getDeliveryCost() {
        DeliveryCostCalculator deliveryCostCalculator =
                new DeliveryCostCalculator(5, 1, 2.99);
        return deliveryCostCalculator.calculateFor(this);
    }

    public void print() {
        products.forEach(
                (k, v) -> {
                    System.out.println(k.getCategory().getTitle() + " - " +
                            k.getTitle() + " - " +
                            v + " - " +
                            k.getPrice() + " - " +
                            k.getPrice() * v);
                }
        );
        System.out.println("\nTotal: " + getTotalAmountAfterDiscounts() +
                " ----- Delivery: " + (float)getDeliveryCost());
    }
}
