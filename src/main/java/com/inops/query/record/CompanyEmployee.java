package com.inops.query.record;

public record CompanyEmployee(
		String _id,
	    String employeeID,
	    String firstName,
	    String middleName,
	    String lastName,
	    String gender,
	    String birthDate,
	    String photo,
	    String nationality,
	    String joiningDate,
	    String emailID,
	    String aadharNumber,
	    String manager,
	    String managerName,
	    String status,
	    Boolean settingsRemarks,
	    String organizationCode,
	    String tenantCode
	) {}
