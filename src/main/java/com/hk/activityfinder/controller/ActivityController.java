package com.hk.activityfinder.controller;

import com.hk.activityfinder.interfaces.ActivityService;
import com.hk.activityfinder.model.location.UserLocation;
import com.hk.activityfinder.model.location.coordinate.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ActivityController {

    private final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private double lat;
    private double lon;

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/get-started")
    public String getCoords(@RequestBody String coords) {
        lat = Double.parseDouble(coords.split(" ")[0]);
        lon = Double.parseDouble(coords.split(" ")[1]);
        logger.info("COORDINATES RECEIVED FROM JAVASCRIPT CLIENT: " + lat + " " + lon);
        return "getstarted";
    }

    @GetMapping("/get-started")
    public String showGetStarted(Model model, @ModelAttribute("user") UserLocation userlocation) {
        userlocation.setCoordinate(new Coordinate(lat, lon));
        model.addAttribute(userlocation);
        model.addAttribute("allActivities", activityService.getAllActivities());
        return "getstarted";
    }

}
