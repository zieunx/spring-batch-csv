package com.danal.publicdataprocessor.batch.reader;

import com.danal.publicdataprocessor.BatchTestConfig;
import com.danal.publicdataprocessor.batch.job.RestaurantBatchJobConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBatchTest
@SpringBootTest(classes = { BatchTestConfig.class, RestaurantBatchJobConfig.class })
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class RestaurantCsvReaderConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job publicDataRestaurantJob;

    @BeforeEach
    public void before() {
        jobLauncherTestUtils.setJob(publicDataRestaurantJob);
    }

    @Test
    void test1() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("inputFile", new ClassPathResource("data/csv/공공데이터_일반음식점_241210.csv").getFile().getAbsolutePath())
                .toJobParameters();

        // when
        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}
