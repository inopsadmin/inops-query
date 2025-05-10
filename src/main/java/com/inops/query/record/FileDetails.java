package com.inops.query.record;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
public record FileDetails(
		ObjectId _id,
		String fileName,
		String filePath,
		String fileSize,
		String workflowName,
		String status,
		String remarks,
		String uploadedBy,
		String createdOn
		
) {}
