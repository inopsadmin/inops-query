package com.inops.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;

@AllArgsConstructor
@Data
public class DocumentResponse {
    private String id;
    private Document data;
}
