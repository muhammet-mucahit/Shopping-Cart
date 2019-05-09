public class Main {

    public static void main(String[] args) {
        // Categories
        Category food = new Category("food");
        Category beverage = new Category("beverage");
        Category kitchen = new Category("kitchen");
        food.setParentCategory(kitchen);
        beverage.setParentCategory(kitchen);

        // Products
        Product apple = new Product("Apple", 100.0, food);
        Product almond = new Product("Almonds",  150.0, food);
        Product cola = new Product("Cola", 100.0, beverage);
        Product juice = new Product("Juice",  50.0, beverage);

        // Shopping Card
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(apple,3);
        cart.addItem(almond,1);
        cart.addItem(cola,5);
        cart.addItem(juice,10);
        cart.addItem(apple,10);
        cart.addItem(cola,10);

        // Campaigns
        Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
        Campaign campaign2 = new Campaign(beverage, 50.0, 5, DiscountType.Rate);
        Campaign campaign3 = new Campaign(food, 5.0, 3, DiscountType.Amount);
        Campaign campaign4 = new Campaign(kitchen, 3000.0, 10, DiscountType.Amount);
        cart.applyDiscounts(campaign1, campaign2, campaign3, campaign4);

        // Coupons
        Coupon coupon = new Coupon(100, 10, DiscountType.Rate);
        cart.applyCoupon(coupon);

        // Prints final cart
        cart.print();
    }
}