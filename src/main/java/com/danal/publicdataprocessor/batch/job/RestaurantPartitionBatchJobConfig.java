package com.danal.publicdataprocessor.batch.job;

import com.danal.publicdataprocessor.batch.dto.RestaurantCsvRowData;
import com.danal.publicdataprocessor.batch.partitioner.CsvPartitioner;
import com.danal.publicdataprocessor.batch.processor.CsvToEntityProcessor;
import com.danal.publicdataprocessor.batch.reader.RangeAwareCsvRestaurantFileReader;
import com.danal.publicdataprocessor.batch.reader.RestaurantCsvFieldSetMapper;
import com.danal.publicdataprocessor.batch.writer.RestaurantInsertIgnoreWriter;
import com.danal.publicdataprocessor.domain.model.Restaurant;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

import static com.danal.publicdataprocessor.batch.constants.BatchJobConstants.PUBLIC_DATA_RESTAURANT_PARTITION_JOB_NAME;

/**
 * 파티셔닝 reader Job
 */
@Configuration
public class RestaurantPartitionBatchJobConfig {
    private static final int CHUNK_SIZE = 1000;
    private static final int PARTITION_COUNT = 5;
    private static final String WORKER_STEP_NAME = "csvToDatabaseStep";
    private static final String PARTITIONED_STEP_NAME = "partitionedCsvToDatabaseStep";

    @Bean(name = PUBLIC_DATA_RESTAURANT_PARTITION_JOB_NAME)
    public Job publicDataPartitionRestaurantJob(JobRepository jobRepository, Step partitionedStep) {
        return new JobBuilder(PUBLIC_DATA_RESTAURANT_PARTITION_JOB_NAME, jobRepository)
                .start(partitionedStep)
                .build();
    }

    /**
     * 파티션을 나누는 마스터 스텝
     */
    @Bean
    public Step partitionedStep(JobRepository jobRepository,
                                Partitioner csvPartitioner,
                                Step workerStep,
                                TaskExecutor taskExecutor) {
        return new StepBuilder(PARTITIONED_STEP_NAME, jobRepository)
                .partitioner(WORKER_STEP_NAME, csvPartitioner)
                .step(workerStep)
                .taskExecutor(taskExecutor)
                .build();
    }

    /**
     * 실제 파티션 단위로 실행되는 Worker Step
     */
    @Bean
    public Step workerStep(JobRepository jobRepository,
                           DataSourceTransactionManager transactionManager,
                           ItemReader<RestaurantCsvRowData> partitionedCsvReader,
                           ItemProcessor<RestaurantCsvRowData, Restaurant> partitionCsvToEntityProcessor,
                           ItemWriter<Restaurant> partitionRestaurantInsertIgnoreWriter,
                           ChunkListener chunkListener) {

        return new StepBuilder("workerStep", jobRepository)
                .<RestaurantCsvRowData, Restaurant>chunk(CHUNK_SIZE, transactionManager)
                .reader(partitionedCsvReader)
                .processor(partitionCsvToEntityProcessor)
                .writer(partitionRestaurantInsertIgnoreWriter)
                .listener(chunkListener)
                .build();
    }

    /**
     * 파티션별 라인 범위 (startLine, endLine)와 파일 경로를
     * stepExecutionContext에서 주입받는 ItemReader
     */
    @Bean
    @StepScope
    public ItemReader<RestaurantCsvRowData> partitionedCsvReader(
            @Value("#{stepExecutionContext['filePath']}") String filePath,
            @Value("#{stepExecutionContext['startLine']}") int startLine,
            @Value("#{stepExecutionContext['endLine']}") int endLine,
            RestaurantCsvFieldSetMapper fieldSetMapper
    ) {
        FlatFileItemReader<RestaurantCsvRowData> delegate = new RangeAwareCsvRestaurantFileReader(filePath, fieldSetMapper, startLine, endLine);
        delegate.setStrict(true);
        delegate.setSaveState(false);
        return new SynchronizedItemStreamReaderBuilder<RestaurantCsvRowData>()
                .delegate(delegate)
                .build();
    }

    @Bean(name = "partitionCsvToEntityProcessor")
    @StepScope
    public CsvToEntityProcessor csvToEntityProcessor() {
        return new CsvToEntityProcessor();
    }

    @Bean(name = "partitionRestaurantInsertIgnoreWriter")
    @StepScope
    public RestaurantInsertIgnoreWriter restaurantInsertIgnoreWriter(DataSource dataSource) {
        return new RestaurantInsertIgnoreWriter(dataSource);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(PARTITION_COUNT);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("partitioned-thread-");
        executor.initialize();
        return executor;
    }

    @JobScope
    @Bean
    public Partitioner csvPartitioner(@Value("#{jobParameters['inputFile']}") String filePath) {
        return new CsvPartitioner(filePath, PARTITION_COUNT);
    }
}