package com.inops.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
