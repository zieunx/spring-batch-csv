package com.danal.publicdataprocessor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@EnableAutoConfiguration
@ActiveProfiles("test")
@ComponentScan(basePackages = "com.danal.publicdataprocessor")
public class BatchTestConfig {}
