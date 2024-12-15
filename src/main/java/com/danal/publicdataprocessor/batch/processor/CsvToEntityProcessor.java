package com.danal.publicdataprocessor.batch.processor;

import com.danal.publicdataprocessor.batch.dto.RestaurantCsvRowData;
import com.danal.publicdataprocessor.domain.model.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

/**
 * CsvToEntityProcessor
 * <p>
 * CSV 행 데이터를 Restaurant 엔티티로 변환하는 클래스입니다.
 */
@Slf4j
public class CsvToEntityProcessor implements ItemProcessor<RestaurantCsvRowData, Restaurant> {

    @Override
    public Restaurant process(RestaurantCsvRowData csvRowData) {
        return Restaurant.builder()
                .serviceName(csvRowData.serviceName())
                .serviceId(csvRowData.serviceId())
                .localGovernmentCode(csvRowData.localGovernmentCode())
                .managementNumber(csvRowData.managementNumber())
                .approvalDate(csvRowData.approvalDate())
                .approvalCancelDate(csvRowData.approvalCancelDate())
                .businessStatusCode(csvRowData.businessStatusCode())
                .businessStatusName(csvRowData.businessStatusName())
                .detailedBusinessStatusCode(csvRowData.detailedBusinessStatusCode())
                .detailedBusinessStatusName(csvRowData.detailedBusinessStatusName())
                .closureDate(csvRowData.closureDate())
                .suspensionStartDate(csvRowData.suspensionStartDate())
                .suspensionEndDate(csvRowData.suspensionEndDate())
                .reopeningDate(csvRowData.reopeningDate())
                .locationPhoneNumber(csvRowData.locationPhoneNumber())
                .locationArea(csvRowData.locationArea())
                .locationPostalCode(csvRowData.locationPostalCode())
                .locationFullAddress(csvRowData.locationFullAddress())
                .roadFullAddress(csvRowData.roadFullAddress())
                .roadPostalCode(csvRowData.roadPostalCode())
                .businessName(csvRowData.businessName())
                .lastModifiedTime(csvRowData.lastModifiedTime())
                .dataUpdateType(csvRowData.dataUpdateType())
                .dataUpdateDate(csvRowData.dataUpdateDate())
                .industryTypeName(csvRowData.industryTypeName())
                .traditionalBusinessDesignationNumber(csvRowData.traditionalBusinessDesignationNumber())
                .build();
    }
}