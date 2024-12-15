package com.danal.publicdataprocessor.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "TB_RESTAURANT",
        indexes = @Index(name = "idx_management_number", columnList = "management_number"))
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유 식별자 (자동 생성)")
    private int id;

    @Column(name = "service_name")
    @Comment("개방 서비스명")
    private String serviceName;

    @Column(name = "service_id")
    @Comment("개방 서비스 아이디")
    private String serviceId;

    @Column(name = "local_government_code")
    @Comment("개방 자치 단체 코드")
    private String localGovernmentCode;

    @Column(name = "management_number", unique = true)
    @Comment("관리 번호")
    private String managementNumber;

    @Column(name = "approval_date")
    @Comment("인허가일자")
    private String approvalDate;

    @Column(name = "approval_cancel_date")
    @Comment("인허가취소일자")
    private String approvalCancelDate;

    @Column(name = "business_status_code")
    @Comment("영업 상태 구분 코드")
    private String businessStatusCode;

    @Column(name = "business_status_name")
    @Comment("영업 상태명")
    private String businessStatusName;

    @Column(name = "detailed_business_status_code")
    @Comment("상세 영업 상태 코드")
    private String detailedBusinessStatusCode;

    @Column(name = "detailed_business_status_name")
    @Comment("상세 영업 상태명")
    private String detailedBusinessStatusName;

    @Column(name = "closure_date")
    @Comment("폐업일자")
    private String closureDate;

    @Column(name = "suspension_start_date")
    @Comment("휴업 시작일자")
    private String suspensionStartDate;

    @Column(name = "suspension_end_date")
    @Comment("휴업 종료일자")
    private String suspensionEndDate;

    @Column(name = "reopening_date")
    @Comment("재개업일자")
    private String reopeningDate;

    @Column(name = "location_phone_number")
    @Comment("소재지 전화")
    private String locationPhoneNumber;

    @Column(name = "location_area")
    @Comment("소재지 면적")
    private String locationArea;

    @Column(name = "location_postal_code")
    @Comment("소재지 우편번호")
    private String locationPostalCode;

    @Column(name = "location_full_address")
    @Comment("소재지 전체주소")
    private String locationFullAddress;

    @Column(name = "road_full_address")
    @Comment("도로명 전체주소")
    private String roadFullAddress;

    @Column(name = "road_postal_code")
    @Comment("도로명 우편번호")
    private String roadPostalCode;

    @Column(name = "business_name")
    @Comment("사업장명")
    private String businessName;

    @Column(name = "last_modified_time")
    @Comment("최종 수정 시점")
    private String lastModifiedTime;

    @Column(name = "data_update_type")
    @Comment("데이터 갱신 구분")
    private String dataUpdateType;

    @Column(name = "data_update_date")
    @Comment("데이터 갱신일자")
    private String dataUpdateDate;

    @Column(name = "industry_type_name")
    @Comment("업태 구분명")
    private String industryTypeName;

    @Column(name = "traditional_business_designation_number")
    @Comment("전통업소 지정 번호")
    private String traditionalBusinessDesignationNumber;

    public void update(Restaurant updated) {
        if (updated.getServiceName() != null) {
            this.serviceName = updated.getServiceName();
        }
        if (updated.getServiceId() != null) {
            this.serviceId = updated.getServiceId();
        }
        if (updated.getLocalGovernmentCode() != null) {
            this.localGovernmentCode = updated.getLocalGovernmentCode();
        }
        if (updated.getManagementNumber() != null) {
            this.managementNumber = updated.getManagementNumber();
        }
        if (updated.getApprovalDate() != null) {
            this.approvalDate = updated.getApprovalDate();
        }
        if (updated.getApprovalCancelDate() != null) {
            this.approvalCancelDate = updated.getApprovalCancelDate();
        }
        if (updated.getBusinessStatusCode() != null) {
            this.businessStatusCode = updated.getBusinessStatusCode();
        }
        if (updated.getBusinessStatusName() != null) {
            this.businessStatusName = updated.getBusinessStatusName();
        }
        if (updated.getDetailedBusinessStatusCode() != null) {
            this.detailedBusinessStatusCode = updated.getDetailedBusinessStatusCode();
        }
        if (updated.getDetailedBusinessStatusName() != null) {
            this.detailedBusinessStatusName = updated.getDetailedBusinessStatusName();
        }
        if (updated.getClosureDate() != null) {
            this.closureDate = updated.getClosureDate();
        }
        if (updated.getSuspensionStartDate() != null) {
            this.suspensionStartDate = updated.getSuspensionStartDate();
        }
        if (updated.getSuspensionEndDate() != null) {
            this.suspensionEndDate = updated.getSuspensionEndDate();
        }
        if (updated.getReopeningDate() != null) {
            this.reopeningDate = updated.getReopeningDate();
        }
        if (updated.getLocationPhoneNumber() != null) {
            this.locationPhoneNumber = updated.getLocationPhoneNumber();
        }
        if (updated.getLocationArea() != null) {
            this.locationArea = updated.getLocationArea();
        }
        if (updated.getLocationPostalCode() != null) {
            this.locationPostalCode = updated.getLocationPostalCode();
        }
        if (updated.getLocationFullAddress() != null) {
            this.locationFullAddress = updated.getLocationFullAddress();
        }
        if (updated.getRoadFullAddress() != null) {
            this.roadFullAddress = updated.getRoadFullAddress();
        }
        if (updated.getRoadPostalCode() != null) {
            this.roadPostalCode = updated.getRoadPostalCode();
        }
        if (updated.getBusinessName() != null) {
            this.businessName = updated.getBusinessName();
        }
        if (updated.getLastModifiedTime() != null) {
            this.lastModifiedTime = updated.getLastModifiedTime();
        }
        if (updated.getDataUpdateType() != null) {
            this.dataUpdateType = updated.getDataUpdateType();
        }
        if (updated.getDataUpdateDate() != null) {
            this.dataUpdateDate = updated.getDataUpdateDate();
        }
        if (updated.getIndustryTypeName() != null) {
            this.industryTypeName = updated.getIndustryTypeName();
        }
        if (updated.getTraditionalBusinessDesignationNumber() != null) {
            this.traditionalBusinessDesignationNumber = updated.getTraditionalBusinessDesignationNumber();
        }
    }

}