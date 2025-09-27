package com.inops.query.record;


public record WageSalaryHeads(
		String _id,
		String organizationCode,
		String tenantCode,
		String name,
		String code,
		String description,
		String salaryType,
		PrimarySalaryHead primarySalaryHead,
		CalculationType calculationType,
		boolean printable,
		String salaryCalculationBasis
) {

	public record PrimarySalaryHead(
			String name,
			String code
	) {
	}

	public record CalculationType(
			String type
	) {
	}
}