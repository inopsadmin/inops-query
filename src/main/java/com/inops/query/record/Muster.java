package com.inops.query.record;

import java.util.List;

public record Muster(
		String id,
		String organizationCode,
		String tenantCode,
		int month,
		int year,
		String employeeID,
		List<AttendanceDetail> attendanceDetails
) {

	public record AttendanceDetail(
			String date,
			String shiftCode,
			String attendanceID,
			String leaveCode,
			int hoursWorked,
			int lateIn,
			int earlyOut,
			int extraHours,
			int personalOut,
			int officialOut,
			int extraHoursPreShift,
			int extraHoursPostShift,
			PunchDetails punchDetails
	) {
	}

	public record PunchDetails(
			List<Punch> inPunches,
			List<Punch> outPunches,
			List<Punch> defaultPunches
	) {
	}

	public record Punch(
			String id,
			String punchedTime,
			String transactionTime,
			String inOut,
			String typeOfMovement,
			String readerSerialNumber,
			String uploadTime,
			String attendanceDate,
			String employeeID,
			String organizationCode,
			String tenantCode,
			boolean processed,
			String state,
			boolean isDeleted,
			String remarks,
			String date
	) {
	}
}