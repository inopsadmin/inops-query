package com.inops.query.record;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


public record EmployeeLeaveBalance(
        String _id,
        String organizationCode,
        String tenantCode,
        String employeeID,
        List<LeaveBalance> balances
) {
    public record LeaveBalance(
            String leaveCode,
            String lastCreditDate,
            double openingBalance,
            double closingBalance,
            double availed,
            double encashed,
            double carryForward,
            double previousCarryForward,
            double balance
    ) {}
}
