package com.inops.query.record;

import java.util.List;

public record ShiftConfig(
	String _id,
    String organizationCode,
    String tenantCode,
    String shiftGroupCode,
    String shiftGroupName,
    Subsidiary subsidiary,
    Location location,
    List<String> employeeCategory,
    List<Shift> shift
) {
    public record Subsidiary(
        String subsidiaryCode,
        String subsidiaryName
    ) {}

    public record Location(
        String locationCode,
        String locationName
    ) {}

    public record Shift(
        String shiftCode,
        String shiftName,
        String shiftStart,
        String shiftEnd,
        String firstHalfStart,
        String firstHalfEnd,
        String secondHalfStart,
        String secondHalfEnd,
        String lunchStart,
        String lunchEnd,
        int duration,
        boolean crossDay,
        boolean flexible,
        int flexiFullDayDuration,
        int flexiHalfDayDuration,
        int inAheadMargin,
        int inAboveMargin,
        int outAheadMargin,
        int outAboveMargin,
        int lateInAllowedTime,
        int earlyOutAllowedTime,
        int graceIn,
        int graceOut,
        int earlyOutTime,
        int minimumDurationForFullDay,
        int minimumDurationForHalfDay,
        int minimumExtraMinutesForExtraHours
    ) {}
}
