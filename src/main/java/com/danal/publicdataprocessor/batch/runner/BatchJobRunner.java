package com.danal.publicdataprocessor.batch.runner;

import com.danal.publicdataprocessor.batch.constants.BatchJobConstants;
import com.danal.publicdataprocessor.util.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchJobRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public BatchJobRunner(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @Override
    public void run(String... args) {
        try {
            Job job = jobRegistry.getJob(BatchJobConstants.PUBLIC_DATA_RESTAURANT_JOB_NAME);
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("inputFile", FileUtils.getAbsolutePath("data/csv/공공데이터_일반음식점_241210.csv"))
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}