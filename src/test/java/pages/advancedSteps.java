package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class advancedSteps extends basicSteps {

    private int addedCount;
    private int addedCountCart;
    private int l;
    private String prueba;

    // locators
    private By name = locators.advancedSteps.name_Product;
    private By price = locators.advancedSteps.price_Product;
    private By button = locators.advancedSteps.button_Add_Cart;
    private By nameCart = locators.advancedSteps.name_Product_Cart;

    // List<WebElement>
    private List<WebElement> productContainers;
    private List<WebElement> cartContainers;
    private List<WebElement> productName = new ArrayList<>();
    private List<WebElement> productPrices = new ArrayList<>();
    private List<WebElement> productButtonCart = new ArrayList<>();
    private List<WebElement> productNameCart = new ArrayList<>();
    private List<WebElement> name_elements;
    private List<WebElement> price_elements;
    private List<WebElement> addCart_elements;
    private List<WebElement> name_cart_elements;
    private List<String> textName = new ArrayList<>();

    public advancedSteps() {
        super(driver);
    }

    public void sampling(By locator) {

        productContainers = driver.findElements(locator);

        // Este for recorre todos los elementos de la lista
        for (WebElement productos : productContainers) {
            try {
                name_elements = driver.findElements(name);
                price_elements = driver.findElements(price);
                addCart_elements = driver.findElements(button);
                addedCount = name_elements.size();
                // Se agregan los valores a las listas (Nombre y Precio)
                productName.addAll(name_elements);
                productPrices.addAll(price_elements);
                productButtonCart.addAll(addCart_elements);
                log.info("Se agregaron " + addedCount + " elementos.");
            } catch (Exception e) {
                log.error("Error en elemento: ", e.getMessage());
                e.printStackTrace();
            }
        }
        // Este for imprime los resultados que se agregaron anteriormente
        for (int j = 0; j < addedCount; j++) {
            log.info("Nombre producto: " + obtenerTextoWebElement(productName.get(j)));
            log.info("Precio producto(String): " + obtenerTextoWebElement(productPrices.get(j)));
            String number = obtenerTextoWebElement(productPrices.get(j));
            log.info("Precio producto(double): " + convertirStringADouble(number));
        }

    }

    //Este metodo compara todos los valores actualizados con anterioridad y guarda el de mayor precio
    public void higherPrice(By locator) {

        sampling(locator);
        double number2 = Double.MIN_VALUE;
        l = -1;
        //Este for realiza el bucle que valida cada valor
        for (int i = 0; i < addedCount; i++) {
            double number = convertirStringADouble(obtenerTextoWebElement(productPrices.get(i)));
            if (number > number2) {
                number2 = number;
                l = i;
            }

        }
        log.info("Este es el precio mayor: " + number2 + " Esta ubicado en el lugar: " + l);
        prueba = obtenerTextoWebElement(productName.get(l));
    }

    public void selectProduct(By locator){

        higherPrice(locator);
        // JavaScript para hacer scroll hasta el elemento y luego hacer clic
        ((JavascriptExecutor) basicSteps.driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});",
                    productButtonCart.get(l));
        productButtonCart.get(l).click();
        
    }

    public void cartSampling(By locator) {

        cartContainers = driver.findElements(locator);
        

        // Este for recorre todos los elementos de la lista
        for (WebElement productos : cartContainers) {
            try {
                name_cart_elements = driver.findElements(nameCart);
                WebElement name_cart_elements1 = productos.findElement(nameCart);
                addedCountCart = name_cart_elements.size();
                // Se agregan los valores a las listas (Nombre y Precio)
                productNameCart.addAll(name_cart_elements);
                textName.add(name_cart_elements1.getText());
            } catch (Exception e) {
                log.error("Error en elemento: ", e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public boolean shoppingCartComparison(By locator){
        cartSampling(locator);
        String productNameCart = textName.get(0);
        if(!productName.isEmpty() && !prueba .isEmpty()){
            Assert.assertTrue(productNameCart.toLowerCase().contains(prueba.toLowerCase()),"El nombre del producto no es el mismo");  
            log.info("El nombre del producto del carrito coincide con el seleccionado");    
        return true;
        } else {
            log.error("El producto es diferente al seleccionado");
            return false;
        }
        
    }
}
