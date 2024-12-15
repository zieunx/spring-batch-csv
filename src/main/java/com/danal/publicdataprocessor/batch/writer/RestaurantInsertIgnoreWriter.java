package com.danal.publicdataprocessor.batch.writer;

import com.danal.publicdataprocessor.domain.model.Restaurant;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;

/**
 * RestaurantInsertIgnoreWriter
 * <p>
 * Restaurant 데이터를 데이터베이스에 삽입하고, 중복된 경우 무시하는 Writer.
 * MySQL의 INSERT IGNORE 문법을 활용하며, 배치 처리를 지원합니다.
 * <p>
 * 관리번호(ManagementNumber) 컬럼이 UNIQUE 키로 설정되어야 합니다.
 */
public class RestaurantInsertIgnoreWriter extends JdbcBatchItemWriter<Restaurant> {

    public RestaurantInsertIgnoreWriter(DataSource dataSource) {
        // 데이터 소스 설정
        setDataSource(dataSource);

        // SQL 설정
        setSql("INSERT IGNORE INTO tb_restaurant (management_number, service_name, service_id, local_government_code, approval_date, " +
                "approval_cancel_date, business_status_code, business_status_name, detailed_business_status_code, " +
                "detailed_business_status_name, closure_date, suspension_start_date, suspension_end_date, reopening_date, " +
                "location_phone_number, location_area, location_postal_code, location_full_address, road_full_address, " +
                "road_postal_code, business_name, last_modified_time, data_update_type, data_update_date, industry_type_name, " +
                "traditional_business_designation_number) " +
                "VALUES (:managementNumber, :serviceName, :serviceId, :localGovernmentCode, :approvalDate, :approvalCancelDate, " +
                ":businessStatusCode, :businessStatusName, :detailedBusinessStatusCode, :detailedBusinessStatusName, " +
                ":closureDate, :suspensionStartDate, :suspensionEndDate, :reopeningDate, :locationPhoneNumber, :locationArea, " +
                ":locationPostalCode, :locationFullAddress, :roadFullAddress, :roadPostalCode, :businessName, :lastModifiedTime, " +
                ":dataUpdateType, :dataUpdateDate, :industryTypeName, :traditionalBusinessDesignationNumber)");

        // Bean 프로퍼티 매핑
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
    }
}
