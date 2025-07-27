package com.inops.query.record;


import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;


public record LeavePolicyRecord(
        String _id,
        String organizationCode,
        String tenantCode,
        Subsidiary subsidiary,
        Location location,
        Designation designation,
        List<String> employeeCategory,
        LeavePolicy leavePolicy
) {
    public record Subsidiary(
            String subsidiaryCode,
            String subsidiaryName
    ) {}

    public record Location(
            String locationCode,
            String locationName
    ) {}

    public record Designation(
            String designationCode,
            String designationName
    ) {}

    public record LeavePolicy(
            String leaveCode,
            String leaveTitle,
            String effectiveFrom,
            String genderAllowed,
            List<String> maritalStatus,
            int minimumServicePeriodRequired,
            List<MaximumLeaveAllowed> maximumLeaveAllowed,
            List<MaximumApplicationAllowed> maximumApplicationAllowed,
            double minimumDaysPerApplication,
            int maximumDaysPerApplication,
            boolean halfDayAllowed,
            SandwichLeave sandwichHolidayAsLeave,
            SandwichLeave sandwichWeekOffAsLeave,
            boolean canStartOrEndOnHoliday,
            boolean canStartOrEndOnWeekOff,
            PreApplication preApplication,
            int maximumBackDaysApplicationAllowed,
            int maximumFutureDaysApplicationAllowed,
            int requireDocsIfLeaveDaysExceeds,
            boolean allowedInNoticePeriod,
            boolean alertManagerAfterApproval,
            int alertManagerDaysBeforeLeaveStart,
            boolean delegateApplicable,
            int reminderFrequencyToApprover,
            AutoApproval autoApproval,
            CannotCombineWith cannotCombineWith,
            Balance balance,
            LeaveAccrual leaveAccrual,
            Encashment encashment,
            String leaveType,
            String leaveCategory
    ) {
        public record MaximumLeaveAllowed(
                String type,
                int daysAllowed
        ) {}

        public record MaximumApplicationAllowed(
                String type,
                int count
        ) {}

        public record SandwichLeave(
                boolean countAsLeave,
                int minimumLeaveDays
        ) {}

        public record PreApplication(
                int leaveDaysMoreThan,
                int applyBeforeDays
        ) {}

        public record AutoApproval(
                boolean autoApprovalAllowed,
                boolean autoApproveIfDateCrossed,
                int daysForAutoApproval
        ) {}

        public record CannotCombineWith(
                List<String> prefix,
                List<String> postfix
        ) {}

        public record Balance(
                boolean balanceValidation,
                int allowedNegativeBalance,
                int minServicePeriodRequired,
                String lapseLeaveBalanceAtYearEnd,
                int maximumBalanceAllowed
        ) {}

        public record LeaveAccrual(
                String accrualType,
                int dayId,
                AccrualPolicy accrualPolicy,
                boolean accrualInAdvance,
                int maximumBalanceCarriedForward,
                List<String> excludedDaysForAccrual
        ) {
            public record AccrualPolicy(
                    double accrualDays,
                    int workingDays
            ) {}
        }

        public record Encashment(
                boolean encashmentAllowed,
                int minimumBalanceRequired,
                int maximumAllowedEncashment,
                boolean autoEncashment,
                boolean applicationRequired,
                int maximumApplicationAllowedYearly,
                int maximumEncashmentPerApplication
        ) {}
    }
}
