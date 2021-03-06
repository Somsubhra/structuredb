package org.structuredb.query.handler;

import org.structuredb.query.data.QueryData;
import org.structuredb.query.handler.app.CreateAppHandler;
import org.structuredb.query.handler.app.DeleteAppHandler;
import org.structuredb.query.handler.app.ListAppsHandler;
import org.structuredb.query.handler.app.RenameAppHandler;
import org.structuredb.query.type.QueryType;
import org.structuredb.structure.Structure;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class QueryHandlerMap {

    private Map<QueryType, Callable<Structure>> map;

    private QueryData queryData;

    private String dataPath;

    public QueryHandlerMap(QueryData queryData, String dataPath) {
        this.map = new HashMap<>();
        this.queryData = queryData;
        this.dataPath = dataPath;
        build();
    }

    private void build() {
        map.put(QueryType.APP_CREATE, () -> CreateAppHandler.getInstance().run(queryData, dataPath));
        map.put(QueryType.APP_LIST, () -> ListAppsHandler.getInstance().run(queryData, dataPath));
        map.put(QueryType.APP_RENAME, () -> RenameAppHandler.getInstance().run(queryData, dataPath));
        map.put(QueryType.APP_DELETE, () -> DeleteAppHandler.getInstance().run(queryData, dataPath));
    }

    public Map<QueryType, Callable<Structure>> getMap() {
        return map;
    }

    public Callable<Structure> getHandler(QueryType queryType) {
        return this.map.get(queryType);
    }
}
