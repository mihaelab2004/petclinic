package com.endava.petclinic.extensions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestReporterExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger LOGGER = LogManager.getLogger(TestReporterExtension.class);

    static {
        String env = System.getProperty("env");
        LOGGER.info("Starting tests on env {}", env);
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) {
        LOGGER.info("Starting test {} / {}", extensionContext.getTestClass().get().getSimpleName(), extensionContext.getDisplayName());

    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) {
        LOGGER.info("Finishing test {} / {}", extensionContext.getTestClass().get().getSimpleName(), extensionContext.getDisplayName());

    }
}