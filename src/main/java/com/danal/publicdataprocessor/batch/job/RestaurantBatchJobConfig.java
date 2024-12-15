package com.danal.publicdataprocessor.batch.job;

import com.danal.publicdataprocessor.batch.dto.RestaurantCsvRowData;
import com.danal.publicdataprocessor.batch.processor.RestaurantCsvToEntityProcessor;
import com.danal.publicdataprocessor.domain.model.Restaurant;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.orm.jpa.JpaTransactionManager;

import static com.danal.publicdataprocessor.batch.constants.BatchJobConstants.PUBLIC_DATA_RESTAURANT_JOB_NAME;

@Configuration
public class RestaurantBatchJobConfig {
    private static final int CHUNK_SIZE = 100;
    private static final String CSV_TO_DATABASE_STEP = "csvToDatabaseStep";
    private static final String READER_NAME = "restaurantCsvReader";

    @Bean(name = PUBLIC_DATA_RESTAURANT_JOB_NAME)
    public Job job(JobRepository jobRepository, Step csvToDatabaseStep) {
        return new JobBuilder(PUBLIC_DATA_RESTAURANT_JOB_NAME, jobRepository)
                .start(csvToDatabaseStep)
                .build();
    }

    @JobScope
    @Bean(name = PUBLIC_DATA_RESTAURANT_JOB_NAME + "_" + CSV_TO_DATABASE_STEP)
    public Step csvToDatabaseStep(JobRepository jobRepository,
                                  JpaTransactionManager transactionManager,
                                  FlatFileItemReader<RestaurantCsvRowData> restaurantCsvReader,
                                  RestaurantCsvToEntityProcessor restaurantCsvToEntityProcessor,
                                  ItemWriter<Restaurant> restaurantItemWriter) {
        return new StepBuilder(CSV_TO_DATABASE_STEP, jobRepository)
                .<RestaurantCsvRowData, Restaurant>chunk(CHUNK_SIZE, transactionManager)
                .reader(restaurantCsvReader)
                .processor(restaurantCsvToEntityProcessor)
                .writer(restaurantItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<RestaurantCsvRowData> restaurantCsvReader(
            @Value("#{jobParameters['inputFile']}") String filePath,
            RestaurantCsvFieldSetMapper fieldSetMapper
    ) {
        return new FlatFileItemReaderBuilder<RestaurantCsvRowData>()
                .name(READER_NAME)
                .resource(new FileSystemResource(filePath))
                .encoding("EUC-KR")
                .linesToSkip(1)
                .delimited()
                .names("번호", "개방서비스명", "개방서비스아이디", "개방자치단체코드", "관리번호", "인허가일자",
                        "인허가취소일자", "영업상태구분코드", "영업상태명", "상세영업상태코드", "상세영업상태명",
                        "폐업일자", "휴업시작일자", "휴업종료일자", "재개업일자", "소재지전화", "소재지면적",
                        "소재지우편번호", "소재지전체주소", "도로명전체주소", "도로명우편번호", "사업장명", "최종수정시점",
                        "데이터갱신구분", "데이터갱신일자", "업태구분명", "좌표정보(x)", "좌표정보(y)", "위생업태명",
                        "남성종사자수", "여성종사자수", "영업장주변구분명", "등급구분명", "급수시설구분명",
                        "총직원수", "본사직원수", "공장사무직직원수", "공장판매직직원수", "공장생산직직원수",
                        "건물소유구분명", "보증액", "월세액", "다중이용업소여부", "시설총규모", "전통업소지정번호",
                        "전통업소주된음식", "홈페이지", "")
                .fieldSetMapper(fieldSetMapper)
                .build();
    }
}
