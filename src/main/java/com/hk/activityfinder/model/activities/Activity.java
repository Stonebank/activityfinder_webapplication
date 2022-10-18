package com.hk.activityfinder.model.activities;

import com.hk.activityfinder.model.location.UserLocation;
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
    private String image_path;
    private int points;
    private String distance;
    private Coordinate coordinate;
    private WeatherType[] weatherTypes;

    private int[] ratings;

    public String getDistance() {
        return String.format("%s km", Math.round(Double.parseDouble(this.distance)));
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", image_path='" + image_path + '\'' +
                ", points=" + points +
                ", distance=" + distance +
                ", coordinate=" + coordinate +
                ", weatherTypes=" + Arrays.toString(weatherTypes) +
                '}';
    }

}
