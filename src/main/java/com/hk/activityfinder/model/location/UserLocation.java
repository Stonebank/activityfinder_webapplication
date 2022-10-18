package com.hk.activityfinder.model.location;

import com.hk.activityfinder.model.location.coordinate.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLocation {

    private Coordinate coordinate;

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

    @Override
    public String toString() {
        return "UserLocation{" +
                "coordinate=" + coordinate.getLatitude() + " " + coordinate.getLongitude() +
                '}';
    }
}
