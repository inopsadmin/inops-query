package com.inops.query.record;

import java.util.List;

import org.bson.types.ObjectId;

public record WorkflowManagement(
		String _id,
		String tenantCode,
		String userId,
		String operation,
		String workflow,
		String currentStatus,
		List<Log> log
) {
	public record Log(
			String workflowName,
			String stateName,
			String eventName,
			String timestamp,
			String performedBy,
			Boolean isSuccess
		) {}
}
