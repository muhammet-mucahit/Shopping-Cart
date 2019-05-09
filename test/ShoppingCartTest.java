import jdk.jfr.StackTrace;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ShoppingCartTest {

    Category food, beverage;
    Product apple, almond, cola, juice;
    ShoppingCart cart;
    Campaign campaign1, campaign2, campaign3, campaign4;
    Coupon coupon;

    @Before
    public void setUp() {
        food = new Category("food");
        beverage = new Category("beverage");
        Category kitchen = new Category("kitchen");
        food.setParentCategory(kitchen);
        beverage.setParentCategory(kitchen);

        // Products
        apple = new Product("Apple", 100.0, food);
        almond = new Product("Almonds",  150.0, food);
        cola = new Product("Cola", 100.0, beverage);
        juice = new Product("Juice",  50.0, beverage);

        // Shopping Card
        cart = new ShoppingCart();

        cart.addItem(apple,3);
        cart.addItem(almond,1);
        cart.addItem(cola,5);
        cart.addItem(juice,10);
        cart.addItem(apple,10);
        cart.addItem(cola,10);

        // Campaigns
        campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
        campaign2 = new Campaign(beverage, 50.0, 5, DiscountType.Rate);
        campaign3 = new Campaign(food, 5.0, 3, DiscountType.Amount);
        campaign4 = new Campaign(kitchen, 3000.0, 10, DiscountType.Amount);

        // Coupons
        coupon = new Coupon(100, 10, DiscountType.Rate);
    }

    @Test
    public void addItem() {
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(apple, 13);
        products.put(almond, 1);
        products.put(cola, 15);
        products.put(juice, 10);

        assertEquals(products, cart.getProducts());
    }

    @Test
    public void applyDiscounts() {
        // Apple - 13 - 1300
        // Almond - 1 - 150
        // Cola - 15 - 1500
        // Juice - 10 - 500 2450 - 245

        cart.applyDiscounts(campaign1, campaign2, campaign3, campaign4);

        assertEquals("3000.0", String.valueOf(cart.getCampaignDiscount()));
    }

    @Test
    public void getTotalPrice() {
        assertEquals("3450.0", String.valueOf(cart.getTotalPrice()));
    }

    @Test
    public void applyCoupon() {
        cart.applyCoupon(coupon);

        assertEquals("345.0", String.valueOf(cart.getCouponDiscount()));
    }

    @Test
    public void getTotalAmountAfterDiscounts() {
        cart.applyDiscounts(campaign1, campaign2, campaign3);
        cart.applyCoupon(coupon);
        assertEquals("2205.0", String.valueOf(cart.getTotalAmountAfterDiscounts()));
    }

    @Test
    public void getCouponDiscount() {
        cart.applyCoupon(coupon);
        assertEquals("345.0", String.valueOf(cart.getCouponDiscount()));
    }

    @Test
    public void getCampaignDiscount() {
        cart.applyDiscounts(campaign1, campaign2, campaign3);
        assertEquals("1000.0", String.valueOf(cart.getCampaignDiscount()));
    }

    @Test
    public void getDeliveryCost() {
        assertEquals("21.99", String.valueOf((float)cart.getDeliveryCost()));
    }
}