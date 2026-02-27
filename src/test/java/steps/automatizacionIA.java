package steps;

import org.testng.asserts.SoftAssert;
import io.cucumber.java.en.*;
import pages.basePage;

public class automatizacionIA {

    SoftAssert soft = new SoftAssert();
    basePage main = new basePage();

    @Given("^(?:El usuario abre el navegador y navega al sitio web|Usuario navega al sitio web)$")
    public void ingresoSitio(){
        main.startSession();
    }

    @Given("^(?:El usuario ha iniciado sesion|Usuario realiza el proceso de login)$")
    public void incioSesion(){
        main.loginProcess();
    }

    @When("^(?:Selecciona el producto mas costoso|Selecciona el producto de mayor valor)$")
    public void seleccionProducto(){
        main.productSearch();
    }

    @Then("^(?:Valida que solo haya un producto seleccionado en el carrito)$")
    public void cantidadProductos(){
        soft.assertTrue(main.numbersProducts(), "La cantidad de productos no coincide con la cantidad seleccionada");
        soft.assertAll();
    }

    @Then("^(?:Valida en el carrito que el producto seleccionado es el correcto)$")
    public void carritoCompras(){
        soft.assertTrue(main.productsCart(), "El nombre del producto del carrito no coincide con el seleccionado");
        soft.assertAll();
    }
    
}
