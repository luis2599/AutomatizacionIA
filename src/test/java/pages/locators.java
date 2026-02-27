package pages;

import org.openqa.selenium.By;

public class locators {
    
    public static class basePage {
        public static final By user_Input = By.xpath("//input[contains(@id, 'user-name')]");
        public static final By pass_Input = By.xpath("//input[contains(@id, 'password')]");
        public static final By button_Login = By.xpath("//input[contains(@id, 'login-button')]");
        public static final By container_products = By.xpath("//div[contains(@class, 'inventory_list')]");
        public static final By container_cart = By.xpath("//div[contains(@class, 'cart_list')]");
        public static final By button_cart = By.xpath("//div[contains(@id, 'shopping_cart_container')]");
    }

    public static class advancedSteps{
        public static final By name_Product = By.xpath("//div[contains(@class, 'inventory_item_name')]");
        public static final By price_Product = By.xpath("//div[contains(@class, 'inventory_item_price')]");
        public static final By button_Add_Cart = By.xpath("//button[contains(@class, 'btn btn_primary btn_small btn_inventory')]");
        public static final By name_Product_Cart = By.xpath("//div[contains(@class, 'inventory_item_name')]");
    }

}
