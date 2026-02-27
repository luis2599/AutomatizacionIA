package pages;

import org.openqa.selenium.By;

public class locators {
    
    public static class basePage {
        public static final By userInput = By.xpath("//input[contains(@id, 'user-name')]");
        public static final By passInput = By.xpath("//input[contains(@id, 'password')]");
        public static final By buttonLogin = By.xpath("//input[contains(@id, 'login-button')]");
        public static final By containerProducts = By.xpath("//div[contains(@class, 'inventory_list')]");
        public static final By containerCart = By.xpath("//div[contains(@class, 'cart_list')]");
        public static final By buttonCart = By.xpath("//div[contains(@id, 'shopping_cart_container')]");
    }

    public static class advancedSteps{
        public static final By nameProduct = By.xpath("//div[contains(@class, 'inventory_item_name')]");
        public static final By priceProduct = By.xpath("//div[contains(@class, 'inventory_item_price')]");
        public static final By buttonAdd_Cart = By.xpath("//button[contains(@class, 'btn btn_primary btn_small btn_inventory')]");
        public static final By nameProductCart = By.xpath("//div[contains(@class, 'inventory_item_name')]");
    }

}
