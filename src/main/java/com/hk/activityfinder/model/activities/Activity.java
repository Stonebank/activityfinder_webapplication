package com.hk.activityfinder.model.activities;

import com.hk.activityfinder.model.location.coordinate.Coordinate;
import com.hk.activityfinder.model.location.weather.WeatherType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

@Getter @Setter
public class Activity {

    public static final ArrayList<Activity> activities = new ArrayList<>();

    private String name;
    private String city;

    private int points;

    private double distance;

    private Coordinate coordinate;

    private WeatherType[] weatherTypes;

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", address='" + city + '\'' +
                ", points=" + points +
                ", distance=" + distance +
                ", coordinate=" + coordinate +
                ", weatherTypes=" + Arrays.toString(weatherTypes) +
                '}';
    }

}
