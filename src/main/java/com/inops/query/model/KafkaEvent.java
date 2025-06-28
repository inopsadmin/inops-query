package com.inops.query.model;

import lombok.Data;

import java.util.List;

@Data
public class KafkaEvent {
    private String tenant;
    private String action;
    private String collectionName;
    private String id;
    private String key;
    private String event;
    private Object data;
    private List<Object> datas;

}
