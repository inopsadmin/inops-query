package com.inops.query.model;

import lombok.Data;

@Data
public class KafkaEvent {
    private String tenant;
    private String action;
    private String collectionName;
    private String id;
    private Object data;
}
