package com.inops.query.config;

import com.inops.query.model.CriteriaRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

    private static Object parseValue(Object value) {
        if (value instanceof String str) {
            try {
                if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
                    return Boolean.parseBoolean(str);
                }
                if (str.contains(".")) return Double.parseDouble(str);
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return str; // fallback to string
            }
        }
        return value;
    }

//    public static Query getQuery(List<CriteriaRequest> criteriaRequests) {
//        Query query = new Query();
//
//        for (CriteriaRequest criteria : criteriaRequests) {
//            String field = criteria.getField();
//            String operator = criteria.getOperator().toLowerCase();
//            Object value = Util.parseValue(criteria.getValue());
//
//
//            switch (operator) {
//                case "eq":
//                    query.addCriteria(Criteria.where(field).is(value));
//                    break;
//                case "ne":
//                    query.addCriteria(Criteria.where(field).ne(value));
//                    break;
//                case "gt":
//                    query.addCriteria(Criteria.where(field).gt(value));
//                    break;
//                case "gte":
//                    query.addCriteria(Criteria.where(field).gte(value));
//                    break;
//                case "lt":
//                    query.addCriteria(Criteria.where(field).lt(value));
//                    break;
//                case "lte":
//                    query.addCriteria(Criteria.where(field).lte(value));
//                    break;
//                case "in":
//                    if (value instanceof List) {
//                        query.addCriteria(Criteria.where(field).in((List<?>) value));
//                    }
//                    break;
//                case "nin":
//                    if (value instanceof List) {
//                        query.addCriteria(Criteria.where(field).nin((List<?>) value));
//                    }
//                    break;
//                case "regex":
//                    query.addCriteria(Criteria.where(field).regex(value.toString(), "i")); // Case-insensitive
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unsupported operator: " + operator);
//            }
//        }
//        return query;
//    }

    public static Query getQuery(List<CriteriaRequest> criteriaRequests) {
        Query query = new Query();

        Map<String, List<CriteriaRequest>> groupedCriteria = new HashMap<>();

        // Group criteria based on field or group (e.g., all "month" conditions)
        for (CriteriaRequest criteria : criteriaRequests) {
            groupedCriteria
                    .computeIfAbsent(criteria.getField(), k -> new ArrayList<>())
                    .add(criteria);
        }

        for (Map.Entry<String, List<CriteriaRequest>> entry : groupedCriteria.entrySet()) {
            String field = entry.getKey();
            List<CriteriaRequest> fieldCriteria = entry.getValue();

            // Special handling for multiple conditions on same field
            if (fieldCriteria.size() > 1) {
                List<Criteria> andCriterias = new ArrayList<>();
                for (CriteriaRequest cr : fieldCriteria) {
                    Object value = Util.parseValue(cr.getValue());
                    String op = cr.getOperator().toLowerCase();

                    switch (op) {
                        case "eq": andCriterias.add(Criteria.where(field).is(value)); break;
                        case "is": andCriterias.add(Criteria.where(field).is(value)); break;
                        case "ne": andCriterias.add(Criteria.where(field).ne(value)); break;
                        case "gt": andCriterias.add(Criteria.where(field).gt(value)); break;
                        case "gte": andCriterias.add(Criteria.where(field).gte(value)); break;
                        case "lt": andCriterias.add(Criteria.where(field).lt(value)); break;
                        case "lte": andCriterias.add(Criteria.where(field).lte(value)); break;
                        default: throw new IllegalArgumentException("Unsupported operator: " + op);
                    }
                }
                // Combine with andOperator
                query.addCriteria(new Criteria().andOperator(andCriterias.toArray(new Criteria[0])));
            } else {
                // Single condition
                CriteriaRequest criteria = fieldCriteria.get(0);
                String operator = criteria.getOperator().toLowerCase();
                Object value = Util.parseValue(criteria.getValue());

                switch (operator) {
                    case "eq":
                        query.addCriteria(Criteria.where(field).is(value));
                        break;
                    case "is":
                        query.addCriteria(Criteria.where(field).is(value));
                        break;
                    case "ne":
                        query.addCriteria(Criteria.where(field).ne(value));
                        break;
                    case "gt":
                        query.addCriteria(Criteria.where(field).gt(value));
                        break;
                    case "gte":
                        query.addCriteria(Criteria.where(field).gte(value));
                        break;
                    case "lt":
                        query.addCriteria(Criteria.where(field).lt(value));
                        break;
                    case "lte":
                        query.addCriteria(Criteria.where(field).lte(value));
                        break;
                    case "in":
                        if (value instanceof List) {
                            query.addCriteria(Criteria.where(field).in((List<?>) value));
                        }
                        break;
                    case "nin":
                        if (value instanceof List) {
                            query.addCriteria(Criteria.where(field).nin((List<?>) value));
                        }
                        break;
                    case "regex":
                        query.addCriteria(Criteria.where(field).regex(value.toString(), "i"));
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported operator: " + operator);
                }
            }
        }

        return query;
    }

}
