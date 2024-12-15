package com.danal.publicdataprocessor.batch.writer;

import com.danal.publicdataprocessor.domain.model.Restaurant;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;

/**
 * RestaurantUpsertWriter
 * <p>
 * Restaurant 데이터를 데이터베이스에 삽입하거나, 중복된 경우 업데이트하는 Writer.
 * MySQL의 ON DUPLICATE KEY UPDATE 문법을 활용하며, 배치 처리를 지원합니다.
 * <p>
 * 관리번호(ManagementNumber) 컬럼이 UNIQUE 키로 설정되어야 합니다.
 */
public class RestaurantUpsertWriter extends JdbcBatchItemWriter<Restaurant> {

    public RestaurantUpsertWriter(DataSource dataSource) {
        // 데이터 소스 설정
        setDataSource(dataSource);

        // SQL 설정
        setSql("INSERT INTO tb_restaurant (management_number, service_name, service_id, local_government_code, approval_date, " +
                "approval_cancel_date, business_status_code, business_status_name, detailed_business_status_code, " +
                "detailed_business_status_name, closure_date, suspension_start_date, suspension_end_date, reopening_date, " +
                "location_phone_number, location_area, location_postal_code, location_full_address, road_full_address, " +
                "road_postal_code, business_name, last_modified_time, data_update_type, data_update_date, industry_type_name, " +
                "traditional_business_designation_number) " +
                "VALUES (:managementNumber, :serviceName, :serviceId, :localGovernmentCode, :approvalDate, :approvalCancelDate, " +
                ":businessStatusCode, :businessStatusName, :detailedBusinessStatusCode, :detailedBusinessStatusName, " +
                ":closureDate, :suspensionStartDate, :suspensionEndDate, :reopeningDate, :locationPhoneNumber, :locationArea, " +
                ":locationPostalCode, :locationFullAddress, :roadFullAddress, :roadPostalCode, :businessName, :lastModifiedTime, " +
                ":dataUpdateType, :dataUpdateDate, :industryTypeName, :traditionalBusinessDesignationNumber) " +
                "ON DUPLICATE KEY UPDATE " +
                "service_name = VALUES(service_name), " +
                "service_id = VALUES(service_id), " +
                "local_government_code = VALUES(local_government_code), " +
                "approval_date = VALUES(approval_date), " +
                "approval_cancel_date = VALUES(approval_cancel_date), " +
                "business_status_code = VALUES(business_status_code), " +
                "business_status_name = VALUES(business_status_name), " +
                "detailed_business_status_code = VALUES(detailed_business_status_code), " +
                "detailed_business_status_name = VALUES(detailed_business_status_name), " +
                "closure_date = VALUES(closure_date), " +
                "suspension_start_date = VALUES(suspension_start_date), " +
                "suspension_end_date = VALUES(suspension_end_date), " +
                "reopening_date = VALUES(reopening_date), " +
                "location_phone_number = VALUES(location_phone_number), " +
                "location_area = VALUES(location_area), " +
                "location_postal_code = VALUES(location_postal_code), " +
                "location_full_address = VALUES(location_full_address), " +
                "road_full_address = VALUES(road_full_address), " +
                "road_postal_code = VALUES(road_postal_code), " +
                "business_name = VALUES(business_name), " +
                "last_modified_time = VALUES(last_modified_time), " +
                "data_update_type = VALUES(data_update_type), " +
                "data_update_date = VALUES(data_update_date), " +
                "industry_type_name = VALUES(industry_type_name), " +
                "traditional_business_designation_number = VALUES(traditional_business_designation_number)");

        // Bean 프로퍼티 매핑
        setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
    }
}