package org.structuredb.query.handler;

import org.structuredb.query.data.QueryData;
import org.structuredb.structure.Structure;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class QueryHandlerMap {

    private Map<QueryType, Callable<Structure>> map;

    private QueryData queryData;

    public QueryHandlerMap(QueryData queryData) {
        this.map = new HashMap<>();
        this.queryData = queryData;
        build();
    }

    private void build() {
        map.put(QueryType.CREATE_APP, () -> CreateAppHandler.getInstance().run(queryData));
    }

    public Map<QueryType, Callable<Structure>> getMap() {
        return map;
    }

    public Callable<Structure> getHandler(QueryType queryType) {
        return this.map.get(queryType);
    }
}