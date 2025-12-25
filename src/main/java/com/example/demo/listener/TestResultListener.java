// File: src/test/java/com/example/demo/listener/TestResultListener.java
package com.example.demo.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestResultListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("[TEST START] " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("[TEST PASS] " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("[TEST FAIL] " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("[TEST SKIP] " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("[TEST SUITE START] " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("[TEST SUITE FINISH] " + context.getName());
    }
}