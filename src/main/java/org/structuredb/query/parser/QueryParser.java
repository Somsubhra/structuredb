package org.structuredb.query.parser;

import org.structuredb.exception.query.BlankQueryException;
import org.structuredb.exception.query.UnknownQueryType;
import org.structuredb.query.data.QueryData;
import org.structuredb.query.data.RawQuery;
import org.structuredb.query.type.QueryType;
import org.structuredb.query.type.QueryTypeMap;

import java.util.Arrays;
import java.util.Map;

public class QueryParser {

    private Map<String, QueryType> queryTypeMap;

    public QueryParser() {
        queryTypeMap = QueryTypeMap.get();
    }

    public ParsedQuery parse(RawQuery rawQuery) {

        // TODO: Can be optimized by delimiting using the first space

        String[] queryComponents = rawQuery.getQuery().split("\\s+");

        if (queryComponents.length == 0) {
            throw new BlankQueryException();
        }

        QueryData queryData;

        if (queryComponents.length > 1) {
            String[] queryDataComponents = Arrays.copyOfRange(queryComponents, 1, queryComponents.length);
            String queryDataString = String.join(" ", queryDataComponents);
            queryData = new QueryData(queryDataString);
        } else {
            queryData = new QueryData("{}");
        }

        String rawQueryType = queryComponents[0];

        if (!queryTypeMap.containsKey(rawQueryType)) {
            throw new UnknownQueryType(rawQueryType);
        }

        return new ParsedQuery(queryTypeMap.get(rawQueryType), queryData);
    }
}
