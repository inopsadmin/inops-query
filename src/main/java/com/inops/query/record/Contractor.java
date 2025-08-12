package com.inops.query.record;

import java.util.List;

public record Contractor(
	    String _id,
	    String contractorCode,
	    String contractorName,
	    String organizationCode,
	    String tenantCode,

	    String ownerName,
	    String ownerContactNo,
	    String ownerEmailId,
	    String fatherName,
	    String contactPersonName,
	    String contactPersonContactNo,
	    String contactPersonEmailId,
	    String birthDate,
	    String workLocation,
	    String aadharNumber,
	    String contractorImage,
	    String serviceSince,
	    String typeOfCompany,
	    Boolean penaltiesPolicies,
	    Boolean basicInformation,
	    Boolean companyDetails,
	    Boolean licensesPermits,
	    Boolean financialDetails,
	    Boolean documentsCompliance,
	    Boolean workOrdersCompleted,
	    Boolean addressInformation,
	    String panNumber,

	    List<ContractorWorkOrder> workOrders
	) {
    public record ContractorWorkOrder(
        String workOrderNumber,
        String workOrderDate,
        String proposalReferenceNumber,
        int NumberOfEmployee,
        String contractPeriodFrom,
        String contractPeriodTo,
        String workOrderDocumentFilePath,
        String annexureFilePath,
        double serviceChargeAmount,
        String workOrderType,
        String workOrderLineItems,
        String serviceLineItems,
        String serviceCode,
        double wcChargesPerEmployee,
        List<Double> assetChargesPerDay,
        EmployeeWages employeeWages
    ) {}

    public record EmployeeWages(
        String wageType,
        double wageAmount
    ) {}
}