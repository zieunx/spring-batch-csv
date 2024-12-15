package com.danal.publicdataprocessor.batch.job;

import com.danal.publicdataprocessor.batch.dto.RestaurantCsvRowData;
import com.danal.publicdataprocessor.batch.processor.CsvToEntityProcessor;
import com.danal.publicdataprocessor.batch.reader.CsvRestaurantFileReader;
import com.danal.publicdataprocessor.batch.reader.RestaurantCsvFieldSetMapper;
import com.danal.publicdataprocessor.batch.writer.RestaurantUpsertWriter;
import com.danal.publicdataprocessor.domain.model.Restaurant;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

import static com.danal.publicdataprocessor.batch.constants.BatchJobConstants.PUBLIC_DATA_RESTAURANT_JOB_NAME;

@Configuration
public class RestaurantBatchJobConfig {
    private static final int CHUNK_SIZE = 1000;
    private static final String CSV_TO_DATABASE_STEP = "csvToDatabaseStep";

    @Bean(name = PUBLIC_DATA_RESTAURANT_JOB_NAME)
    public Job publicDataRestaurantJob(JobRepository jobRepository,
                                       Step csvToDatabaseStep) {
        return new JobBuilder(PUBLIC_DATA_RESTAURANT_JOB_NAME, jobRepository)
                .start(csvToDatabaseStep)
                .build();
    }

    @Bean(CSV_TO_DATABASE_STEP)
    @JobScope
    public Step csvToDatabaseStep(JobRepository jobRepository,
                                  DataSourceTransactionManager transactionManager,
                                  CsvRestaurantFileReader csvRestaurantFileReader,
                                  ItemProcessor<RestaurantCsvRowData, Restaurant> csvToEntityProcessor,
                                  ItemWriter<Restaurant> restaurantUpsertWriter,
                                  ChunkListener chunkListener) {
        return new StepBuilder(CSV_TO_DATABASE_STEP, jobRepository)
                .<RestaurantCsvRowData, Restaurant>chunk(CHUNK_SIZE, transactionManager)
                .reader(csvRestaurantFileReader)
                .processor(csvToEntityProcessor)
                .writer(restaurantUpsertWriter)
                .listener(chunkListener)
                .build();
    }

    @StepScope
    @Bean
    public CsvRestaurantFileReader csvRestaurantFileReader(@Value("#{jobParameters['inputFile']}") String filePath,
                                                           RestaurantCsvFieldSetMapper restaurantCsvFieldSetMapper) {
        return new CsvRestaurantFileReader(filePath, restaurantCsvFieldSetMapper);
    }

    @StepScope
    @Bean
    public CsvToEntityProcessor csvToEntityProcessor() {
        return new CsvToEntityProcessor();
    }

    @StepScope
    @Bean
    public RestaurantUpsertWriter restaurantUpsertWriter(DataSource dataSource) {
        return new RestaurantUpsertWriter(dataSource);
    }
}
