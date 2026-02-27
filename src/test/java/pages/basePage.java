package pages;

import org.openqa.selenium.By;

public class basePage extends basicSteps{

    advancedSteps adv = new advancedSteps();

    //config
    String url = config.get("url.base");
    int timeout = config.getInt("timeout.explicit");
    String user = config.get("user_standard");
    String pass = config.get("pass");

    //locators
    By user_login = locators.basePage.user_Input;
    By pass_login = locators.basePage.pass_Input;
    By button_login = locators.basePage.button_Login;
    By container_products = locators.basePage.container_products;
    By container_cart = locators.basePage.container_cart;
    By button_cart = locators.basePage.button_cart;

    public basePage(){
        super(driver);
    }

    public void startSession(){
        navigateTo(url);
        esperarCargaCompletaPagina(timeout);
    }

    public void loginProcess(){
        escribirTexto(user_login, user);
        escribirTexto(pass_login, pass);
        click(button_login);
        esperarCargaCompletaPagina(timeout);
    }

    public void productSearch(){
        adv.selectProduct(container_products);
    }

    public boolean productsCart(){
        click(button_cart);
        esperarCargaCompletaPagina(timeout);
        return adv.shoppingCartComparison(container_cart);
    }
    
}
