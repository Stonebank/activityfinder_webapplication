package com.hk.activityfinder.model.location;

import com.hk.activityfinder.model.location.coordinate.Coordinate;
import com.hk.activityfinder.model.location.weather.WeatherType;
import com.hk.settings.Constant;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Locale;

@Getter @Setter
public class UserLocation {

    private final Logger logger = LoggerFactory.getLogger(UserLocation.class);

    private Coordinate coordinate;

    private BigDecimal current_temperature;
    private BigDecimal maximum_temperature;
    private BigDecimal minimum_temperature;
    private BigDecimal feels_like;

    private WeatherType weatherType;

    private JSONObject jsonObject;

    public double getDistance(Coordinate coordinate) {
        int earth_radius = 6371;

        double lat1 = Math.min(this.coordinate.getLatitude(), coordinate.getLatitude());
        double lat2 = Math.max(this.coordinate.getLatitude(), coordinate.getLatitude());

        double lon1 = Math.min(this.coordinate.getLongitude(), coordinate.getLongitude());
        double lon2 = Math.max(this.coordinate.getLongitude(), coordinate.getLongitude());

        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earth_radius * c;
    }

    public void fetchWeatherData() {
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + coordinate.getLatitude() + "&lon=" + coordinate.getLongitude() + "&appid=" + Constant.WEATHER_API_KEY + "&units=" + Constant.WEATHER_UNIT_OUTPUT);
            var jsonToken = new JSONTokener(url.openStream());
            jsonObject = new JSONObject(jsonToken);
            logger.info("Weather data ready for " + coordinate);
        } catch (IOException e) {
            logger.error("ERROR! Weather data not fetched", e);
        }
    }

    public void parseWeatherData() {
        current_temperature = (BigDecimal) jsonObject.getJSONObject("main").get("temp");
        maximum_temperature = (BigDecimal) jsonObject.getJSONObject("main").get("temp_max");
        minimum_temperature = (BigDecimal) jsonObject.getJSONObject("main").get("temp_min");
        feels_like = (BigDecimal) jsonObject.getJSONObject("main").get("feels_like");

        String weather = (String) jsonObject.getJSONArray("weather").getJSONObject(0).get("description");
        switch (weather.toLowerCase(Locale.ROOT)) {
            case "clear sky", "sunny", "sun" -> weatherType = WeatherType.SUNNY;
            case "overcast clouds", "few clouds", "scattered clouds", "broken clouds" -> weatherType = WeatherType.CLOUD;
            case "shower rain", "rain", "mist", "drizzle" -> weatherType = WeatherType.RAIN;
            case "thunderstorm", "tornado" -> weatherType = WeatherType.STORM;
            case "snow" -> weatherType = WeatherType.SNOW;
            case "wind", "windy" -> weatherType = WeatherType.WIND;
            case "none" -> weatherType = WeatherType.NONE;
            default -> System.err.println("ERROR! WeatherType not detected");
        }
        logger.info("Weather data parsed for " + coordinate);
    }

    @Override
    public String toString() {
        int randomizer = (int) (Math.random() * 2);
        String message;
        if (coordinate.getLongitude() == 0 && coordinate.getLongitude() == 0)
            randomizer = -1;
        switch (randomizer) {
            case 0 -> message = String.format("Weather currently feels like %s°C", Math.round(feels_like.doubleValue()));
            case 1 -> message = String.format("The weather will reach a highest temperature of %s°C and lowest of %s°C ", Math.round(maximum_temperature.doubleValue()), Math.round(minimum_temperature.doubleValue()));
            default -> message = "Error: Weather data and location is inaccurate. Please reload page";
        }
        return message;
    }

}
