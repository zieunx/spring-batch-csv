package com.danal.publicdataprocessor.batch.processor;

import com.danal.publicdataprocessor.batch.dto.RestaurantCsvRowData;
import com.danal.publicdataprocessor.domain.model.Restaurant;
import com.danal.publicdataprocessor.domain.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class RestaurantCsvToEntityProcessor implements ItemProcessor<RestaurantCsvRowData, Restaurant> {

    private final RestaurantRepository restaurantRepository;

    public RestaurantCsvToEntityProcessor(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant process(RestaurantCsvRowData csvRowData) {
        // managementNumber 로 데이터베이스에서 조회
        Optional<Restaurant> existing = restaurantRepository.findByManagementNumber(csvRowData.managementNumber());

        Restaurant createdRestaurant = Restaurant.builder()
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

        if (existing.isPresent()) {
            // Restaurant 가 존재하면 업데이트
            Restaurant existRestaurant = existing.get();
            existRestaurant.update(createdRestaurant);
            return existRestaurant;
        } else {
            // 없으면 새로 생성
            return createdRestaurant;
        }
    }
}