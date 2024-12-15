package com.danal.publicdataprocessor.batch.reader;

import com.danal.publicdataprocessor.batch.dto.RestaurantCsvRowData;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CsvRestaurantFileReader
 * <p>
 * CSV 데이터를 읽고 중복된 row를 필터링하는 Reader
 */
public class CsvRestaurantFileReader extends FlatFileItemReader<RestaurantCsvRowData> {

    private final Set<String> processedManagementNumbers = ConcurrentHashMap.newKeySet();

    public CsvRestaurantFileReader(String filePath, FieldSetMapper<RestaurantCsvRowData> fieldSetMapper) {
        setResource(new FileSystemResource(filePath));
        setEncoding("EUC-KR");
        setLinesToSkip(1); // CSV 헤더 스킵
        setLineMapper(createLineMapper(fieldSetMapper));
    }

    @Override
    public RestaurantCsvRowData read() throws Exception {
        RestaurantCsvRowData item;
        while ((item = super.read()) != null) {
            if (!processedManagementNumbers.add(item.managementNumber())) {
                continue; // 중복된 경우 다음 데이터로 이동
            }
            return item;
        }
        return null;
    }

    private LineMapper<RestaurantCsvRowData> createLineMapper(FieldSetMapper<RestaurantCsvRowData> fieldSetMapper) {
        var lineMapper = new DefaultLineMapper<RestaurantCsvRowData>();
        var tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(
                "번호", "개방서비스명", "개방서비스아이디", "개방자치단체코드", "관리번호", "인허가일자",
                "인허가취소일자", "영업상태구분코드", "영업상태명", "상세영업상태코드", "상세영업상태명",
                "폐업일자", "휴업시작일자", "휴업종료일자", "재개업일자", "소재지전화", "소재지면적",
                "소재지우편번호", "소재지전체주소", "도로명전체주소", "도로명우편번호", "사업장명", "최종수정시점",
                "데이터갱신구분", "데이터갱신일자", "업태구분명", "좌표정보(x)", "좌표정보(y)", "위생업태명",
                "남성종사자수", "여성종사자수", "영업장주변구분명", "등급구분명", "급수시설구분명",
                "총직원수", "본사직원수", "공장사무직직원수", "공장판매직직원수", "공장생산직직원수",
                "건물소유구분명", "보증액", "월세액", "다중이용업소여부", "시설총규모", "전통업소지정번호",
                "전통업소주된음식", "홈페이지", ""
        );
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}