package pages;

import org.openqa.selenium.By;

public class basePage extends pasosBasicos{

    String url = config.get("url.base");
    int timeout = config.getInt("timeout.explicit");
    String user = config.get("user_standard");
    String pass = config.get("pass");
    By user_login = locators.basePage.user_Input;
    By pass_login = locators.basePage.pass_Input;
    By button_login = locators.basePage.button_Login;

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
        By lista = By.xpath("//select[@class='product_sort_container']");
        click(lista);
    }
    
}
