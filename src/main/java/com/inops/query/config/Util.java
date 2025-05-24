package com.inops.query.config;

import com.inops.query.model.CriteriaRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

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

    public static Query getQuery(List<CriteriaRequest> criteriaRequests) {
        Query query = new Query();

        for (CriteriaRequest criteria : criteriaRequests) {
            String field = criteria.getField();
            String operator = criteria.getOperator().toLowerCase();
            Object value = Util.parseValue(criteria.getValue());


            switch (operator) {
                case "eq":
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
                    query.addCriteria(Criteria.where(field).regex(value.toString(), "i")); // Case-insensitive
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        }
        return query;
    }

}
