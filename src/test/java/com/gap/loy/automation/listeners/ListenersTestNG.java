package com.gap.loy.automation.listeners;

import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.gap.loy.automation.utils.TestBase;
import com.gap.loy.automation.utils.TestBase;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ListenersTestNG implements ITestListener {

    public void saveScreenshot(String name, WebDriver driver) {
        Allure.addAttachment(name, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

     public void saveText(String name, String content) {
        Allure.addAttachment(name, content);
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test: " + result.getName() + " " + "Started");
//        try {
//            File downloadsDir = new File("./build/downloads");
//            FileUtils.forceMkdir(downloadsDir);
//            FileUtils.cleanDirectory(downloadsDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test is Successful: " + result.getName());
        saveText("Status: Success", "");
        TestBase.destroy();
    }

    @SneakyThrows
    @Override
    public void onTestFailure(ITestResult result) {
        String name = result.getMethod().getConstructorOrMethod().getName();
        System.out.println("Test is Failed: " + name);
        WebDriver webDriver = TestBase.getWebDriver();
        saveScreenshot("Failed at: " + webDriver.getCurrentUrl(), webDriver);
        saveText("StackTrace:", Arrays.toString(result.getThrowable().getStackTrace()));
        TestBase.destroy();
    }

    @SneakyThrows
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test is Skipped: " + result.getName());
        saveScreenshot(System.currentTimeMillis() + "", TestBase.getWebDriver());
        TestBase.destroy();
    }

    @SneakyThrows
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Test Result is: " + result.getName());
        saveScreenshot(System.currentTimeMillis() + "", TestBase.getWebDriver());
        TestBase.destroy();
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("In onStart.!");
        System.out.println("Context Name = " + context.getName());
        File reports = new File("target");
        try {
            FileUtils.deleteDirectory(reports);
            System.out.println("target directory deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("In onFinish.!");

        /* This piece is to delete the report folder before generating a new report.
         * This has to be removed in the final version */

        File reports = new File("target/allure-report");
        try {
            FileUtils.deleteDirectory(reports);
            System.out.println("allure-report directory deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* The above piece has to be removed in the final version */

        Process process = null;
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            boolean isWindows = osName.contains("win");
            if (isWindows) {
                ProcessBuilder builder = new ProcessBuilder(
                        "cmd.exe", "/c", "allure generate target/allure-results -o target/allure-report --clean");
                builder.redirectErrorStream(true);
                process = builder.start();
            } else {
                process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "allure generate allure-results -o allure-report --clean"});
            }
            boolean isProcessDone = process.waitFor(60, TimeUnit.SECONDS);
            if (!isProcessDone) {
                System.out.println("Reports are not generated within 60 seconds");
                process.destroy();
            } else if (process.exitValue() == 1) {
                BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                System.out.println("Process exited with status code: 1 and error: ");
                while (true) {
                    line = r.readLine();
                    if (line == null) { break; }
                    System.out.println(line);
                }
            } else {
                System.out.println("Report: http://localhost:63342/LoyaltyAutomation/target/allure-report/index.html");
            }
        } catch (Exception ex) {
            System.out.println("onFinish call failed due to " + ex);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
