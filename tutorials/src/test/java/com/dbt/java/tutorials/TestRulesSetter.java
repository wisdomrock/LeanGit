package com.dbt.java.tutorials;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestRulesSetter implements TestRule {

    private OutputStream out = null;
    private final TestCasePrinter printer = new TestCasePrinter();

    private String beforeContent = null;
    private String afterContent = null;
    private long timeStart;
    private long timeEnd;

    public TestRulesSetter(OutputStream os) {
        out = os;
    }

    private class TestCasePrinter extends ExternalResource {
        @Override
        protected void before() throws Throwable {
            timeStart = System.currentTimeMillis();
            out.write(beforeContent.getBytes());
        };


        @Override
        protected void after() {
            try {
                timeEnd = System.currentTimeMillis();
                long miliSeconds = (timeEnd-timeStart);
                out.write(String.format("%s: Time elapsed %s miliSeconds\n", afterContent, miliSeconds).getBytes());
            } catch (IOException ioe) { /* ignore */
            }
        };
    }

    public final Statement apply(Statement statement, Description description) {
        beforeContent = String.format("\n[TEST STARTED %s.%s()]", description.getClassName(), description.getMethodName()); // description.getClassName() to get class name
        afterContent =  String.format("[TEST ENDED %s.%s()]", description.getClassName(), description.getMethodName());
        return printer.apply(statement, description);
    }    
}
