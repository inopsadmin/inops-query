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
		String aadharNumber,
		List<ContractorWorkOrder> workOrders,
		Boolean isDeleted,
		String createdOn,
		String createdBy
) {
	public record ContractorWorkOrder(
			String workOrderNumber,
			String workOrderDate,
			String proposalReferenceNumber,
			int NumberOfEmployee,
			String contractPeriodFrom,
			String contractPeriodTo
	) {}
}