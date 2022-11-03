package com.gap.loy.automation.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
        plugin = {"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"},
        features = {
           //    "src/test/java/com/gap/loy/automation/features/LoginPage.feature",
        "src/test/java/com/gap/loy/automation/features/ValueCenterPage.feature"
        },
//"src/test/java/com/gap/loy/automation/features/LoginPage.feature",
        glue = "com/gap/loy/automation/StepDef")
public class RunnerTest extends AbstractTestNGCucumberTests {

}
