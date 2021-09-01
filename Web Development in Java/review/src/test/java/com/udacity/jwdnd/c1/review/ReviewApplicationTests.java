package com.udacity.jwdnd.c1.review;

import com.udacity.jwdnd.c1.review.pages.ChatPage;
import com.udacity.jwdnd.c1.review.pages.LoginPage;
import com.udacity.jwdnd.c1.review.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewApplicationTests {
	@LocalServerPort
	private Integer port;

	private static WebDriver driver;
	private LoginPage loginPage;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
	}

	//This test two users can sign up and that both messages are shown
	@Test public void testTwoSignUpToMessages() {
		testFromLoginToSignUpToSubmitMessage("Rodrigo", "Mesta", "rimesta", "1234",
				"rimesta: HELLO!", "hello!", "Shout",0);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(webDriver -> webDriver.findElement(By.id("signup-link")));
		testFromLoginToSignUpToSubmitMessage("Isaias", "Garcia", "isa21", "12345",
				"isa21: hey", "HeY", "Whisper",1);
	}

	@Test
	public void testFromLoginToSignUpToSubmitMessage(String firstName, String lastName, String userName, String password,
													 String expectedMessage, String message, String messageMode, int index) {
		loginPage.goToSignUp();
		testSignUpUser(driver, firstName, lastName, userName, password);
		loginPage.fillOutLogin(userName, password);
		testEnterChatMessage(driver, expectedMessage,message, messageMode, index);
	}

	@Test
	public void testSignUpUser(WebDriver driver, String firstName, String lastName, String userName, String password) {
		SignupPage signupPage = new SignupPage(driver);
		signupPage.fillOutSignup(firstName, lastName,  userName, password);
		signupPage.goToLogin(driver);
	}

	@Test
	public void testEnterChatMessage(WebDriver driver, String expectedMessage, String message, String messageMode, int index) {
		ChatPage chatPage = new ChatPage(driver);
		chatPage.messageAndMessageType(message, messageMode);
		assertEquals(expectedMessage,chatPage.getXMessage(index));
		chatPage.goToLogin();
	}


}
