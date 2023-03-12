package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

	@CucumberOptions(features = "src/main/java/Features", glue = "StepDefinitions", monochrome = true, plugin = {
	"html:target/cucumber.html" })
	public class Runner extends AbstractTestNGCucumberTests {

}

