package com.example.leetdoce.compilator;

import java.util.Map;

public class ResponseCompilator {
    private String consoleText;
    private String passedTestCases;
    private Long runtime;
    private boolean passed=true;
    private Map<Integer,String> provided;
    private String expected;
    private String youResult;

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getConsoleText() {
        return consoleText;
    }

    public void setConsoleText(String consoleText) {
        this.consoleText = consoleText;
    }

    public String getPassedTestCases() {
        return passedTestCases;
    }

    public void setPassedTestCases(String passedTestCases) {
        this.passedTestCases = passedTestCases;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Map<Integer, String> getProvided() {
        return provided;
    }

    public void setProvided(Map<Integer, String> provided) {
        this.provided = provided;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getYouResult() {
        return youResult;
    }

    public void setYouResult(String youResult) {
        this.youResult = youResult;
    }

    @Override
    public String toString() {
        return "ResponseCompilator{" +
                "consoleText='" + consoleText + '\'' +
                ", passedTestCases='" + passedTestCases + '\'' +
                ", runtime=" + runtime +
                ", passed=" + passed +
                ", provided=" + provided +
                ", expected='" + expected + '\'' +
                ", youResult='" + youResult + '\'' +
                '}';
    }
}
