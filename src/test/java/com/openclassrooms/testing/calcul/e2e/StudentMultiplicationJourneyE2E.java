package com.openclassrooms.testing.calcul.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.bonigarcia.wdm.WebDriverManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class StudentMultiplicationJourneyE2E {

	@LocalServerPort
	private Integer port;
	private WebDriver webDriver =null;
	private String baseUrl;
	
	@BeforeAll
	public static void setUpFirefoxDriver() {
		WebDriverManager.firefoxdriver().setup();
	}
	
	@BeforeEach
	public void setUpWebDriver() {
		webDriver = new FirefoxDriver();
		baseUrl="http://localhost:"+port+ "/calculator"; // icinon indique l'url pour accéder au site
	}
	
	@AfterEach
	public void quiteDriver() {
		if(webDriver !=null) {
			webDriver.quit();
		}
	}
	
	
	
	@Test 
	public void aStudentusesTheCalculatorToMultiplyTwoBySixteen() {
		//GIVEN
		webDriver.get(baseUrl);
		WebElement leftField= webDriver.findElement(By.id("left"));
		WebElement rightField= webDriver.findElement(By.id("right"));
		WebElement typeDropDown= webDriver.findElement(By.id("type"));
		WebElement submitButton= webDriver.findElement(By.id("submit"));
		
				
		//WHEN
		// on imite l'action de l'utilisateur
		leftField.sendKeys("2");
		rightField.sendKeys("16");
		typeDropDown.sendKeys("x");
		submitButton.click();
		
		//THEN
		// On créé un objet qui attend le chargement de la page de retour
		final WebDriverWait waiter = new WebDriverWait(webDriver,5);
		WebElement solutionElement = waiter.until(ExpectedConditions.presenceOfElementLocated(By.id("solution")));
		String solution = solutionElement.getText();
		assertThat(solution).isEqualTo("32");
		
		
		
		

}
}
