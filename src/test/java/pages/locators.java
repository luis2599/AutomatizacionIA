package pages;

import org.openqa.selenium.By;

public class locators {
    
    public static class basePage {
        public static final By user_Input = By.xpath("//input[contains(@id, 'user-name')]");
        public static final By pass_Input = By.xpath("//input[contains(@id, 'password')]");
        public static final By button_Login = By.xpath("//input[contains(@id, 'login-button')]");
    }
}
