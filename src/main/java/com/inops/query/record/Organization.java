package com.inops.query.record;

import java.util.List;
import org.bson.types.ObjectId;

public record Organization(
        String _id,
        String organizationName,
        String organizationCode,
        String addressLine1,
        String addressLine2,
        String city,
        String pinCode,
        String logoFileName,
        String description,
        String emailId,
        String contactPersonContactNumber,
        String registrationNo,
        String tenantCode,
        Integer isActive,
        Integer firstMonthOfFinancialYear,
        List<OrgSubsidiary> subsidiaries,
        List<OrgDesignation> designations,
        List<OrgGrade> grades,
        List<OrgDivision> divisions,
        List<OrgDepartment> departments,
        List<OrgSubDepartment> subDepartments,
        List<OrgSection> sections,
        List<OrgEmployeeCategory> employeeCategories,
        List<OrgLocation> location,
        List<OrgReasonCode> reasonCodes
) {

    public record OrgSubsidiary(
            String subsidiaryName,
            String subsidiaryCode,
            String subsidiaryDescription,
            String organizationCode,
            String locationCode
    ) {}

    public record OrgLocation(
            String locationName,
            String locationCode,
            String regionCode,
            String countryCode,
            String stateCode,
            String city,
            String pinCode,
            String organizationCode
    ) {}

    public record OrgDesignation(
            String divisionCode,
            String subsidiaryCode,
            String organizationCode,
            String locationCode,
            String designationCode,
            String designationName,
            String designationDescription
    ) {}

    public record OrgDivision(
            String subsidiaryCode,
            String organizationCode,
            String locationCode,
            String divisionCode,
            String divisionName,
            String divisionDescription
    ) {}

    public record OrgDepartment(
            String departmentName,
            String departmentCode,
            String divisionCode,
            String subsidiaryCode,
            String organizationCode,
            String locationCode,
            String departmentDescription
    ) {}

    public record OrgGrade(
            String gradeCode,
            String gradeName,
            String gradeDescription,
            String divisionCode,
            String subsidiaryCode,
            String organizationCode,
            String locationCode,
            String designationCode
    ) {}

    public record OrgSubDepartment(
            String subDepartmentName,
            String subDepartmentCode,
            String departmentCode,
            String divisionCode,
            String subsidiaryCode,
            String organizationCode,
            String locationCode,
            String subDepartmentDescription
    ) {}

    public record OrgSection(
            String sectionName,
            String sectionCode,
            String subDepartmentCode,
            String departmentCode,
            String divisionCode,
            String subsidiaryCode,
            String organizationCode,
            String locationCode,
            String sectionDescription
    ) {}
    public record OrgEmployeeCategory(
            String employeeCategoryCode,
            String employeeCategoryName,
            String employeeCategoryDescription
    ) {}
    public record OrgReasonCode(
            String reasonCode,
            String reasonName
    ) {}
}