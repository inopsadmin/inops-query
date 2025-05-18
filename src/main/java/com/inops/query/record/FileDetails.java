package com.inops.query.record;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
public record FileDetails(
		String _id,
		String fileName,
		String filePath,
		String fileSize,
		String workflowName,
		String status,
		String description,
		String uploadedBy,
		String createdOn,
		String file
) {}
