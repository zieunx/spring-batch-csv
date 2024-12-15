package com.danal.publicdataprocessor.batch.reader;

import com.danal.publicdataprocessor.batch.dto.RestaurantCsvRowData;
import com.danal.publicdataprocessor.util.NumberUtils;
import com.danal.publicdataprocessor.util.YnType;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class RestaurantCsvFieldSetMapper implements FieldSetMapper<RestaurantCsvRowData> {

    @Override
    public RestaurantCsvRowData mapFieldSet(FieldSet fieldSet) {
        return RestaurantCsvRowData.builder()
                .id(fieldSet.readString("번호"))
                .serviceName(fieldSet.readString("개방서비스명"))
                .serviceId(fieldSet.readString("개방서비스아이디"))
                .localGovernmentCode(fieldSet.readString("개방자치단체코드"))
                .managementNumber(fieldSet.readString("관리번호"))
                .approvalDate(fieldSet.readString("인허가일자"))
                .approvalCancelDate(fieldSet.readString("인허가취소일자"))
                .businessStatusCode(fieldSet.readString("영업상태구분코드"))
                .businessStatusName(fieldSet.readString("영업상태명"))
                .detailedBusinessStatusCode(fieldSet.readString("상세영업상태코드"))
                .detailedBusinessStatusName(fieldSet.readString("상세영업상태명"))
                .closureDate(fieldSet.readString("폐업일자"))
                .suspensionStartDate(fieldSet.readString("휴업시작일자"))
                .suspensionEndDate(fieldSet.readString("휴업종료일자"))
                .reopeningDate(fieldSet.readString("재개업일자"))
                .locationPhoneNumber(fieldSet.readString("소재지전화"))
                .locationArea(fieldSet.readString("소재지면적"))
                .locationPostalCode(fieldSet.readString("소재지우편번호"))
                .locationFullAddress(fieldSet.readString("소재지전체주소"))
                .roadFullAddress(fieldSet.readString("도로명전체주소"))
                .roadPostalCode(fieldSet.readString("도로명우편번호"))
                .businessName(fieldSet.readString("사업장명"))
                .lastModifiedTime(fieldSet.readString("최종수정시점"))
                .dataUpdateType(fieldSet.readString("데이터갱신구분"))
                .dataUpdateDate(fieldSet.readString("데이터갱신일자"))
                .industryTypeName(fieldSet.readString("업태구분명"))
                .coordinateX(fieldSet.readString("좌표정보(x)"))
                .coordinateY(fieldSet.readString("좌표정보(y)"))
                .hygieneIndustryTypeName(fieldSet.readString("위생업태명"))
                .maleEmployees(NumberUtils.parseInt(fieldSet.readString("남성종사자수"), 0))
                .femaleEmployees(NumberUtils.parseInt(fieldSet.readString("여성종사자수"), 0))
                .surroundingAreaType(fieldSet.readString("영업장주변구분명"))
                .gradeType(fieldSet.readString("등급구분명"))
                .waterSupplyType(fieldSet.readString("급수시설구분명"))
                .totalEmployees(NumberUtils.parseInt(fieldSet.readString("총직원수"), 0))
                .headOfficeEmployees(NumberUtils.parseInt(fieldSet.readString("본사직원수"), 0))
                .officeEmployees(NumberUtils.parseInt(fieldSet.readString("공장사무직직원수"), 0))
                .salesEmployees(NumberUtils.parseInt(fieldSet.readString("공장판매직직원수"), 0))
                .productionEmployees(NumberUtils.parseInt(fieldSet.readString("공장생산직직원수"), 0))
                .buildingOwnershipType(fieldSet.readString("건물소유구분명"))
                .depositAmount(fieldSet.readString("보증액"))
                .monthlyRent(fieldSet.readString("월세액"))
                .isMultiUseBusiness(YnType.valueOfOrNull(fieldSet.readString("다중이용업소여부")))
                .facilityTotalSize(fieldSet.readString("시설총규모"))
                .traditionalBusinessDesignationNumber(fieldSet.readString("전통업소지정번호"))
                .mainFoodOfTraditionalBusiness(fieldSet.readString("전통업소주된음식"))
                .website(fieldSet.readString("홈페이지"))
                .build();
    }
}
