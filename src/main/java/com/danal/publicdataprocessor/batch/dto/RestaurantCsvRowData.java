package com.danal.publicdataprocessor.batch.dto;

import com.danal.publicdataprocessor.util.YnType;
import lombok.Builder;

@Builder
public record RestaurantCsvRowData(
        String id, // 번호
        String serviceName, // 개방서비스명
        String serviceId, // 개방서비스아이디
        String localGovernmentCode, // 개방자치단체코드
        String managementNumber, // 관리번호
        String approvalDate, // 인허가일자
        String approvalCancelDate, // 인허가취소일자
        String businessStatusCode, // 영업상태구분코드
        String businessStatusName, // 영업상태명
        String detailedBusinessStatusCode, // 상세영업상태코드
        String detailedBusinessStatusName, // 상세영업상태명
        String closureDate, // 폐업일자
        String suspensionStartDate, // 휴업시작일자
        String suspensionEndDate, // 휴업종료일자
        String reopeningDate, // 재개업일자
        String locationPhoneNumber, // 소재지전화
        String locationArea, // 소재지면적
        String locationPostalCode, // 소재지우편번호
        String locationFullAddress, // 소재지전체주소
        String roadFullAddress, // 도로명전체주소
        String roadPostalCode, // 도로명우편번호
        String businessName, // 사업장명
        String lastModifiedTime, // 최종수정시점
        String dataUpdateType, // 데이터갱신구분
        String dataUpdateDate, // 데이터갱신일자
        String industryTypeName, // 업태구분명
        String coordinateX, // 좌표정보(x)
        String coordinateY, // 좌표정보(y)
        String hygieneIndustryTypeName, // 위생업태명
        int maleEmployees, // 남성종사자수
        int femaleEmployees, // 여성종사자수
        String surroundingAreaType, // 영업장주변구분명
        String gradeType, // 등급구분명
        String waterSupplyType, // 급수시설구분명
        int totalEmployees, // 총직원수
        int headOfficeEmployees, // 본사직원수
        int officeEmployees, // 공장사무직직원수
        int salesEmployees, // 공장판매직직원수
        int productionEmployees, // 공장생산직직원수
        String buildingOwnershipType, // 건물소유구분명
        String depositAmount, // 보증액
        String monthlyRent, // 월세액
        YnType isMultiUseBusiness, // 다중이용업소여부
        String facilityTotalSize, // 시설총규모
        String traditionalBusinessDesignationNumber, // 전통업소지정번호
        String mainFoodOfTraditionalBusiness, // 전통업소주된음식
        String website // 홈페이지
) {}
