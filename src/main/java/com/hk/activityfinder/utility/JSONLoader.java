package com.hk.activityfinder.utility;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.nio.file.Paths;

public abstract class JSONLoader {

    private final Logger logger = LoggerFactory.getLogger(JSONLoader.class);

    public abstract String path();

    public abstract void load(JsonObject reader, Gson gson);

    public void load() {
        try (FileReader reader = new FileReader(Paths.get(path()).toFile())) {
            JsonArray array = (JsonArray) JsonParser.parseReader(reader);
            Gson builder = new GsonBuilder().create();

            for (int i = 0; i < array.size(); i++) {
                JsonObject jsonObject = (JsonObject) array.get(i);
                load(jsonObject, builder);
            }
        } catch (Exception e) {
            logger.error("ERROR! JSON File at " + path() + " was not parsed correctly.", e);
        }
    }

}
