package com.hk.activityfinder.utility.loader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hk.activityfinder.Launch;
import com.hk.activityfinder.model.activities.Activity;
import com.hk.activityfinder.model.location.coordinate.Coordinate;
import com.hk.activityfinder.model.location.weather.WeatherType;
import com.hk.activityfinder.utility.JSONLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityLoader {

    private final Logger logger = LoggerFactory.getLogger(ActivityLoader.class);

    public void init() {

        JSONLoader jsonLoader = new JSONLoader() {
            @Override
            public String path() {
                return "./activity.json";
            }

            @Override
            public void load(JsonObject reader, Gson gson) {
                var name = reader.get("name").getAsString();
                var city = reader.get("city").getAsString();

                var latitude = reader.get("coordinate").getAsJsonObject().get("latitude").getAsDouble();
                var longitude = reader.get("coordinate").getAsJsonObject().get("longitude").getAsDouble();

                var bestWeather = WeatherType.valueOf(reader.get("weatherTypes").getAsJsonArray().get(0).getAsString());
                var worstWeather = WeatherType.valueOf(reader.get("weatherTypes").getAsJsonArray().get(1).getAsString());

                Activity activity = new Activity();

                activity.setName(name);
                activity.setCity(city);
                activity.setDistance(0);
                activity.setCoordinate(new Coordinate(latitude, longitude));
                activity.setWeatherTypes(new WeatherType[] { bestWeather, worstWeather });

                Activity.activities.add(activity);

            }

        };
        jsonLoader.load();

        logger.info(Activity.activities.size() + " activities parsed");

    }

}
