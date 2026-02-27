package pages;

// Importación de las librerias necesarias para el funcionamiento de: WebDriver, manejo de elementos web, y gestión de tiempos de espera.
import java.time.Duration;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;
//import java.util.logging.LogManager;

//importación de las clases necesarias para el manejo de Selenium WebDriver y WebDriverManager.
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import io.cucumber.java.sl.In;
//importación de la clase WebDriverManager para gestionar las dependencias del controlador del navegador.
import io.github.bonigarcia.wdm.WebDriverManager;

public class basicSteps {

    // Declaración de la variable estática 'driver' para el WebDriver y una
    // instancia de WebDriverWait con un tiempo de espera de 5 segundos.
    protected static WebDriver driver;
    public static final Logger log = LoggerFactory.getLogger(basicSteps.class);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    // Caché de elementos: mapea localizadores (By) a WebElement para reutilizar elementos encontrados y evitar búsquedas repetidas
    Map<By, WebElement> elementCache = new HashMap<>(); 

    // Configuración del WebDriver para Chrome usando WebDriverManager.
    static {
        // estas lineas son para ingreso en modo incognito y asi no genere problema con el login
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs); // opcionales para reducir detección de automatización
        options.addArguments("--incognito");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--window-size=1920,1080");
        options.setExperimentalOption("useAutomationExtension", false);
        WebDriverManager.chromedriver().setup();
        // inicializa el driver de Chrome con las opciones configuradas
        driver = new ChromeDriver(options);
        // elimina la propiedad navigator.webdriver para evitar la detección de
        // automatización
        ((JavascriptExecutor) driver).executeScript(
                "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        // maximiza la ventana del navegador
        driver.manage().window().maximize();
        driver.manage().getCookies();
    }

    // Inicializa la variable estática 'driver' con una instancia de ChromeDriver
    public basicSteps(WebDriver driver) {
        basicSteps.driver = driver;
    }

    // metodo para dar navegar a un sitio web
    public static void navigateTo(String url) {
        driver.get(url);
    }

    // metodo para esperar un tiempo determinado (en segundos) utilizando Thread.sleep, con manejo de excepciones.
    public static void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // metodo que espera que toda la pagina cargue
    public void esperarCargaCompletaPagina(int tiempocarga) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(tiempocarga));
            wait.until(webDriver -> js).executeScript("return document.readyState").equals("complete");
        } catch (Exception e) {
            //logger.error("Timeout esperando carga completa: ", e.getMessage());
            log.error("Timeout esperando carga completa: ", e.getMessage());
        }
    }

    // metodo para encontrar un elemento web utilizando un localizador
    public WebElement encontrar(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement elemento = driver.findElement(locator);
            log.info("Elemento encontrado: " + locator.toString());
            return elemento;
        } catch (Exception e) {
            throw new RuntimeException("Elemento no encontrado: " + locator.toString(), e);
        }
    }

    // metodo para obtener la URL actual del navegador
    public void obtenerURL() {
        String urlActual = driver.getCurrentUrl();
        System.out.println(urlActual);
        //logger.info("URL actual: ", urlActual);
    }

    // metodo esperar ventana emergente y cambiar a ella
    public void cambiarAVentanaEmergente(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // metodo para cambiar a la ventana principal
    public void cambiarAVentanaPrincipal(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // metodo para hacer clic en un elemento web utilizando un localizador
    public void click(By locator) {
        encontrar(locator).click();
    }

    // metodo para escribir texto en un elemento web utilizando un localizador
    public void escribirTexto(By locator, String texto) {
        encontrar(locator).clear();
        encontrar(locator).sendKeys(texto);
        log.info(texto);
    }

    // metodo para dar clic con la tecla enter
    public void enter(By locator) {
        encontrar(locator).sendKeys(Keys.ENTER);
    }

    // metodo para esperar a que un elemento web esté presente en la página
    // utilizando un localizador
    public void esperarElemento(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        // Usar JavaScript para interactuar con el select oculto
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Hacer visible temporalmente el select
        js.executeScript(
                "var select = document.getElementById('locator');" +
                        "select.style.display = 'block';" +
                        "select.style.visibility = 'visible';" +
                        "select.style.opacity = '1';" +
                        "select.style.height = 'auto';" +
                        "select.style.width = 'auto';");
    }

    // metodo para seleccionar un valor de lista despegable por valor de la lista
    public void seleccionarXValor(By locator, String valor) {
        esperarElemento(locator);
        Select dropdown = new Select(encontrar(locator));
        dropdown.selectByValue(valor);
    }

    // metodo para seleccionar un valor de lista despegable por numero de orden
    public void seleccionarXOrden(By locator, int valor) {
        Select dropdown = new Select(encontrar(locator));
        dropdown.selectByIndex(valor);
    }

    // Metodo para tomar un objeto de la pagina y transformala en texto
    public String obtenerTexto(By locator) {
        String texto = driver.findElement(locator).getText();
        return texto;
    }

    // Metodo para tomar un objeto de la pagina y transformala en texto, con
    // impresion del texto obtenido
    public String obtenerTextoWebElement(WebElement locator) {
        String texto = locator.getText();
        //logger.info("Texto obtenido: " + texto);
        return texto;
    }

    //Metodo para convertir un String en un valor double
    public double convertirStringADouble(String valor){
        // Elimina todo excepto dígitos y punto
        String conversion = valor.replaceAll("[^\\d.]", "");
        double doble = Double.parseDouble(conversion);
        return doble;
    }

    //Metodo para convertir un String en un valor double
    public double convertirWebElementADouble(WebElement locator){
        String texto = obtenerTextoWebElement(locator);
        String conversion = texto.replaceAll("[^\\d.]", "");
        double valor = Double.parseDouble(conversion.trim());
        return valor;
    }

    // metodo para tomar un objeto de la pagina y transformala en numero, con
    // impresion del numero obtenido
    public Integer obtenerNumero(By locator) {
        String texto = driver.findElement(locator).getText();
        String numeroTexto = texto.replaceAll("\\D+", ""); // Elimina todo excepto los dígitos
        Integer numero = Integer.parseInt(numeroTexto); // Convierte el texto a un número entero
        log.info("Número obtenido: " + numero);
        return numero;
    }

    // metodo para verificar si un elemento es visible en la pagina
    public boolean elementoVisible(By locator, int timeoutSegundos) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSegundos));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            //logger.error("Elemento no vidible: ",locator,", validado por: ",timeoutSegundos," segundos");
            return false;
        }
    }

    // metodo para verificar si una ventana emergente esta activa
    public void controlarMensajeToaster(By locator, By locatorCerrar) {
        try {
            encontrar(locator);
            String mensaje = obtenerTexto(locator);
            System.out.println(mensaje);
            //logger.info("Mensaje del toaster: ", mensaje);
            click(locatorCerrar);
        } catch (Exception e) {
            //logger.info("No se encontró el mensaje del toaster.");
        }
    }

    // metodo para tomar el valor de una tabla
    public Boolean obtenerValor(By locator) {
        Boolean elemento = driver.findElements(locator).size() > 0;
        //logger.info("Valor obtenido: ", elemento);
        return elemento;
    }

    // metodo para tomar la cantidad de elementos encontrados en una tabla
    public Integer obtenerCantidad(By locator) {
        Integer elemento = driver.findElements(locator).size();
        //logger.info("Valor obtenido: ", elemento);
        return elemento;
    }

    // metodo para hacer scroll hacia un elemento web utilizando un localizador
    public void scrollHaciaElemento(By locator) {
        WebElement elemento = encontrar(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'nearest'});", elemento);
    }

    // metodo para cerrar el navegador
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    // metodo para refrescar la pagina
    public static void refrescarPagina() {
        driver.navigate().refresh();
    }

    // Limpiar caché cuando sea necesario
    public void limpiarCache() {
        elementCache.clear();
    }

}
