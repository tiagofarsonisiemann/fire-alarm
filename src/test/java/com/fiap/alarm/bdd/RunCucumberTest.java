package com.fiap.alarm.bdd;
import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@SuiteDisplayName("API BDD test suite")
public class RunCucumberTest {}