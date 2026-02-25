package runner;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import pages.pasosBasicos;

public class hooks extends pasosBasicos {

    public hooks() {
        super(driver);
    }

    @After
public void tearDown(Scenario scenario) {
    esperar(2);
    
    // Capturar evidencia siempre
    final byte[] evidencia = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
    
    // Agregar metadata al reporte
    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    scenario.attach(("Ejecutado: " + timestamp).getBytes(), "text/plain", "Timestamp");
    
    // Agregar URL actual
    String urlActual = driver.getCurrentUrl();
    scenario.attach(("URL: " + urlActual).getBytes(), "text/plain", "URL del escenario");
    
    // Adjuntar screenshot
    scenario.attach(evidencia, "image/png", "Evidencia de prueba");
    
    if(scenario.isFailed()) {
        // Agregar información del error
        String errorMsg = "Escenario falló: " + scenario.getName();
        scenario.attach(errorMsg.getBytes(), "text/plain", "Error");
        
        // Log del error en consola
        /*logger.error("Escenario Fallo: {}", scenario.getName());
        logger.error("URL del error: {}", urlActual);*/
    } else {
        //logger.info("✅ Escenario Exitoso: {}", scenario.getName());
    }
}

}
