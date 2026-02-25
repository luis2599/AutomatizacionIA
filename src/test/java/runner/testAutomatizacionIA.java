package runner;

// Importación de las clases necesarias para ejecutar pruebas con Cucumber y JUnit
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import pages.pasosBasicos;

// Anotación para indicar que se utilizará Cucumber como runner de pruebas
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"steps", "runner"},
    plugin = {"pretty", "html:target/cucumber-reports.html",
                "json:target/cucumber-reports/cucumber.json",
                "timeline:target/cucumber-timeline",
                "junit:target/cucumber-reports/cucumber.xml"}
)

public class testAutomatizacionIA {
    //Metodo que se ejecutará después de todas las pruebas para cerrar el navegador
    @AfterClass
    public static void tearDown() {
        pasosBasicos.closeBrowser();
    }
}
