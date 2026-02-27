package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class advancedSteps extends basicSteps {

    // Variables de configuración
    private int quantityMaxProducts = config.getInt("maximum.quantity.of.products");

    // Variables de almacenamiento
    private int addedCount = 0;
    private int addedCountCart = 0;

    // Variables de apoyo 
    private int indMaxPro;
    private double valMaxPro = Double.MIN_VALUE;
    private String stringNameProduct;

    // Elementos locators
    private By locatorName = locators.advancedSteps.nameProduct;
    private By locatorPrice = locators.advancedSteps.priceProduct;
    private By locatorButton = locators.advancedSteps.buttonAdd_Cart;
    private By locatorNameCart = locators.advancedSteps.nameProductCart;

    // Elementos inventarios de productos
    private List<WebElement> productContainers = new ArrayList<>();
    private List<WebElement> productName = new ArrayList<>();
    private List<WebElement> productPrices = new ArrayList<>();
    private List<WebElement> productButtonCart = new ArrayList<>();
    private List<WebElement> productNameElements = new ArrayList<>();
    private List<WebElement> productPriceElements = new ArrayList<>();
    private List<WebElement> productAddCartElements = new ArrayList<>();
    
    // Elementos carrito de compras
    private List<WebElement> cartContainers = new ArrayList<>();
    private List<WebElement> cartNameElements = new ArrayList<>();
    private List<WebElement> cartNameProduct = new ArrayList<>();
    private List<String> cartTextName = new ArrayList<>();

    public advancedSteps() {
        super(driver);
    }

    // Este método recorre todos los productos y los almacenas para mostrar sus datos
    public void sampling(By locator) {

        productContainers = driver.findElements(locator);
        productName.clear();
        productPrices.clear();
        productButtonCart.clear();

        // Este for recorre todos los elementos de la lista
        for (WebElement products : productContainers) {
            try {
                productNameElements = products.findElements(locatorName);
                productPriceElements = products.findElements(locatorPrice);
                productAddCartElements = products.findElements(locatorButton);
                // Se agrega valor al contador de productos en el inventario
                addedCount += productNameElements.size();
                // Se agregan los valores a las listas (Nombre y Precio)
                productName.addAll(productNameElements);
                productPrices.addAll(productPriceElements);
                productButtonCart.addAll(productAddCartElements);
                log.info("Se agregaron " + addedCount + " elementos.");
            } catch (Exception e) {
                log.error("Error en elemento: ", e.getMessage());
                e.printStackTrace();
            }
        }

    }

    // Este método muestra los datos de todos los productos
    public void productPresentation(){

        // Este for imprime los resultados que se agregaron anteriormente
        for (int j = 0; j < addedCount; j++) {
            log.info("Nombre producto: " + obtenerTextoWebElement(productName.get(j)));
            log.info("Precio producto(String): " + obtenerTextoWebElement(productPrices.get(j)));
            String valPro = obtenerTextoWebElement(productPrices.get(j));
            log.info("Precio producto(double): " + convertirStringADouble(valPro));
        }
    }

    // Este método compara todos los valores actualizados con anterioridad y guarda el de mayor precio
    public void higherPrice() {

        indMaxPro = -1;
        // Este for realiza el bucle que valida cada valor
        for (int i = 0; i < addedCount; i++) {
            double number = convertirStringADouble(obtenerTextoWebElement(productPrices.get(i)));
            if (number > valMaxPro) {
                valMaxPro = number;
                indMaxPro = i;
            }

        }
        log.info("Este es el precio mayor: " + valMaxPro + " Esta ubicado en el lugar: " + indMaxPro);
        stringNameProduct = obtenerTextoWebElement(productName.get(indMaxPro));

    }

    // Este método se encarga de seleccionar el producto de mayor costo
    public void selectProduct() {

        // JavaScript para hacer scroll hasta el elemento y luego hacer clic
        ((JavascriptExecutor) basicSteps.driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});",
                productButtonCart.get(indMaxPro));
        productButtonCart.get(indMaxPro).click();

    }

    // Este método recorre todos los productos que se encuentran en el carrito
    public void cartSampling(By locator) {

        cartContainers = driver.findElements(locator);

        // Este for recorre todos los elementos de la lista
        for (WebElement products : cartContainers) {
            try {
                WebElement name_cart_webelement = products.findElement(locatorNameCart);
                cartNameElements = driver.findElements(locatorNameCart);
                addedCountCart += cartNameElements.size();
                // Se agregan los valores a las listas (Nombre y Precio)
                cartNameProduct.addAll(cartNameElements);
                cartTextName.add(name_cart_webelement.getText());
            } catch (Exception e) {
                log.error("Error en elemento: ", e.getMessage());
                e.printStackTrace();
            }
        }

    }

    // Este método realiza la validación del nombre del producto con mayor costo vs el producto que se encuentra en el carrito
    public boolean shoppingCartComparison() {

        String productNameCart = cartTextName.get(0);
        if (!productName.isEmpty() && !stringNameProduct.isEmpty()) {
            Assert.assertTrue(productNameCart.toLowerCase().equals(stringNameProduct.toLowerCase()),
                    "El nombre del producto no es el mismo");
            log.info("El nombre del producto del carrito coincide con el seleccionado");
            return true;
        } else {
            log.error("El producto es diferente al seleccionado");
            return false;
        }

    }

    // Este método valida la cantidad de productos seleccionados que se encuentran en el carrito de compras con la cantidad parametrizada en la configuración del proyecto
    public boolean numberProductsCart() {

        if (addedCountCart == quantityMaxProducts) {
            log.info("La cantidad de productos en el carrito corresponde a la cantidad de productos seleccionados");
            return true;
        } else if (addedCountCart > quantityMaxProducts) {
            log.error("Hay más productos en el carrito de los seleccionados");
            return false;
        } else {
            log.error("No se encuentran productos en el carrito");
            return false;
        }

    }
}
