package com.hk.activityfinder.utility;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hk.activityfinder.model.activities.Activity;
import com.hk.activityfinder.model.location.coordinate.Coordinate;
import com.hk.activityfinder.model.location.weather.WeatherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class JSONConverter {

    public static void main(String[] args) throws IOException {
        JSONConverter converter = new JSONConverter(new File("./activity.txt"), "./activity.json");
        converter.convert();
    }

    private final Logger logger = LoggerFactory.getLogger(JSONConverter.class);

    private final File file;
    private final String destination;

    public JSONConverter(File file, String destination) throws FileNotFoundException {
        this.file = file;
        this.destination = destination;
        if (!file.exists())
            throw new FileNotFoundException(file.getPath() + " was not found");
        if (new File(destination).delete())
            System.out.println("Deleted " + destination);
    }

    public void convert() throws IOException {

        logger.info("Converting " + file.getName() + "...");

        ArrayList<Activity> activities = new ArrayList<>();

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(destination));

        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {

            String[] splitter = scanner.nextLine().replaceAll(" ", "").split("-");

            String name = splitter[0];
            String city = splitter[1];

            double latitude = Double.parseDouble(splitter[2]);
            double longitude = Double.parseDouble(splitter[3]);

            WeatherType bestWeather = WeatherType.valueOf(splitter[4]);
            WeatherType worstWeather = WeatherType.valueOf(splitter[5]);

            Activity activity = new Activity();

            activity.setName(name);
            activity.setCity(city);
            activity.setImage_path("/image/activity/" + name.replaceAll("\\s", "").toLowerCase(Locale.ROOT) + ".png");
            activity.setCoordinate(new Coordinate(latitude, longitude));
            activity.setWeatherTypes(new WeatherType[] { bestWeather, worstWeather });

            activities.add(activity);

        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(activities, bufferedWriter);

        bufferedWriter.flush();
        bufferedWriter.close();

        logger.info("[COMPLETE!] Converted " + file + " to " + destination + ".");

    }

}
