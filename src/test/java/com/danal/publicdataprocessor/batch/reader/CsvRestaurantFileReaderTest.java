package com.danal.publicdataprocessor.batch.reader;

import com.danal.publicdataprocessor.batch.dto.RestaurantCsvRowData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CsvRestaurantFileReaderTest {

    private CsvRestaurantFileReader reader;

    @BeforeEach
    void setUp() throws Exception {
        // FieldSetMapper 구현
        FieldSetMapper<RestaurantCsvRowData> fieldSetMapper = new RestaurantCsvFieldSetMapper();

        // Reader 초기화
        reader = new CsvRestaurantFileReader(
                new ClassPathResource("src/test/resources/data/csv/simple.csv").getPath(),
                fieldSetMapper
        );

        // ExecutionContext로 Reader open
        ExecutionContext executionContext = new ExecutionContext();
        reader.open(executionContext);
    }

    @Test
    void testRead_validData_shouldReturnUniqueItems() throws Exception {
        RestaurantCsvRowData item1 = reader.read();
        RestaurantCsvRowData item2 = reader.read();
        RestaurantCsvRowData item3 = reader.read();
        RestaurantCsvRowData item4 = reader.read();

        // 데이터 검증
        assertThat(item1).isNotNull();
        assertThat(item1.managementNumber()).isEqualTo("MG001");

        assertThat(item2).isNotNull();
        assertThat(item2.managementNumber()).isEqualTo("MG002");

        assertThat(item3).isNotNull();
        assertThat(item3.managementNumber()).isEqualTo("MG003");

        assertThat(item4).isNull(); // 중복된 관리번호
    }
}
