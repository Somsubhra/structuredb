package org.structuredb.query.handler.app;

import com.google.gson.JsonObject;
import org.structuredb.exception.app.AppNameRequiredException;
import org.structuredb.fileops.app.AppFiles;
import org.structuredb.query.data.QueryData;
import org.structuredb.query.handler.QueryHandler;
import org.structuredb.structure.Error;
import org.structuredb.structure.Structure;
import org.structuredb.structure.app.AppCreation;
import org.structuredb.utils.Console;

public class CreateAppHandler extends QueryHandler {

    public static CreateAppHandler getInstance() {
        return new CreateAppHandler();
    }

    @Override
    public Structure run(QueryData queryData, String dataPath) {

        JsonObject parsedData = queryData.toParsedData().getAsJsonObject();

        if (!parsedData.has("app")) {
            return new Error(new AppNameRequiredException());
        }

        String appName = parsedData.get("app").getAsString();

        Console.info("Creating app '" + appName + "'");

        try {
            AppFiles.createApp(dataPath, appName);
        } catch (Exception e) {
            return new Error(e);
        }

        return new AppCreation(appName);
    }
}
