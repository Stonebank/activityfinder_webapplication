package com.hk.activityfinder.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hk.activityfinder.model.activities.Activity;
import com.hk.activityfinder.model.location.coordinate.Coordinate;
import com.hk.activityfinder.model.location.weather.WeatherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JSONConverter {

    private final Logger logger = LoggerFactory.getLogger(JSONConverter.class);

    private final File file;
    private final String destination;

    public JSONConverter(File file, String destination) {
        this.file = file;
        this.destination = destination;
        if (!file.exists()) {
            logger.error("CRITICAL ERROR! " + file.getPath() + " do not exist. Shutting backend systems");
            System.exit(0);
        }
        if (new File(destination).delete())
            logger.info("Deleted " + destination + "...");
    }

    public void convert() {

        logger.info("Converting " + file.getName() + "...");

        try {
            ArrayList<Activity> activities = new ArrayList<>();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destination));

            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {

                String[] splitter = scanner.nextLine().split("-");

                String name = splitter[0];
                String city = splitter[1];

                double latitude = Double.parseDouble(splitter[2]);
                double longitude = Double.parseDouble(splitter[3]);

                WeatherType bestWeather = WeatherType.valueOf(splitter[4]);
                WeatherType worstWeather = WeatherType.valueOf(splitter[5]);

                Activity activity = new Activity();

                activity.setName(name);
                activity.setCity(city);
                activity.setImage_path("/image/activity/" + name.replaceAll("\\s", "") + ".png");
                activity.setCoordinate(new Coordinate(latitude, longitude));
                activity.setWeatherTypes(new WeatherType[] { bestWeather, worstWeather });

                activities.add(activity);

            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(activities, bufferedWriter);

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            logger.error("CRITICAL ERROR! Conversion was not complete. Shutting down backend systems...");
            System.exit(0);
        }

        logger.info("[COMPLETE!] Converted " + file + " to " + destination + ".");

    }

}
