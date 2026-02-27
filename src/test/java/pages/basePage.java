package pages;

import org.openqa.selenium.By;

public class basePage extends basicSteps{

    advancedSteps adv = new advancedSteps();

    // Variables de configuración
    String url = config.get("url.base");
    int timeout = config.getInt("timeout.explicit");
    String user = config.get("user_standard");
    String pass = config.get("pass");

    // Elementos locators
    By userLogin = locators.basePage.userInput;
    By passLogin = locators.basePage.passInput;
    By buttonLogin = locators.basePage.buttonLogin;
    By containerProducts = locators.basePage.containerProducts;
    By containerCart = locators.basePage.containerCart;
    By buttonCart = locators.basePage.buttonCart;

    public basePage(){
        super(driver);
    }

    public void startSession(){
        navigateTo(url);
        esperarCargaCompletaPagina(timeout);
    }

    public void loginProcess(){
        escribirTexto(userLogin, user);
        escribirTexto(passLogin, pass);
        click(buttonLogin);
        esperarCargaCompletaPagina(timeout);
    }

    public void productSearch(){
        adv.sampling(containerProducts);
        adv.productPresentation();
        adv.higherPrice();
        adv.selectProduct();
    }

    public boolean numbersProducts(){
        click(buttonCart);
        esperarCargaCompletaPagina(timeout);
        adv.cartSampling(containerCart);
        return adv.numberProductsCart();
    }

    public boolean productsCart(){
        return adv.shoppingCartComparison();
    }
    
}
