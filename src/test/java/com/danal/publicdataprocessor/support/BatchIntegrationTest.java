package com.danal.publicdataprocessor.support;

import com.danal.publicdataprocessor.BatchTestConfig;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBatchTest
@SpringBootTest(classes = { BatchTestConfig.class })
@ActiveProfiles("test")
public @interface BatchIntegrationTest {
}
