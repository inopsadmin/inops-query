package com.inops.query.record;

import java.util.List;
import org.bson.types.ObjectId;

public record Organization(
    ObjectId _id,
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
    List<OrgSection> sections
) {

    public record OrgSubsidiary(
        String subsidiaryName,
        String subsidiaryCode,
        String subsidiaryDescription,
        List<OrgSubLocation> location
    ) {}

    public record OrgSubLocation(
        String locationName,
        String locationCode
    ) {}

    public record OrgLocation(
        String locationName,
        String locationCode,
        String regionCode,
        String countryCode,
        String stateCode,
        String city,
        String pinCode
    ) {}

    public record OrgDesignation(
        String divisionCode,
        String designationCode,
        String designationName,
        String designationDescription
    ) {}

    public record OrgDivision(
        String subsidiaryCode,
        String divisionCode,
        String divisionName,
        String divisionDescription
    ) {}

    public record OrgDepartment(
        String departmentName,
        String departmentCode,
        String divisionCode,
        String departmentDescription
    ) {}

    public record OrgGrade(
        String gradeCode,
        String gradeName,
        String gradeDescription,
        String designationCode
    ) {}

    public record OrgSubDepartment(
        String subDepartmentName,
        String subDepartmentCode,
        String departmentCode,
        String subDepartmentDescription
    ) {}

    public record OrgSection(
        String sectionName,
        String sectionCode,
        String subDepartmentCode,
        String sectionDescription
    ) {}

}
