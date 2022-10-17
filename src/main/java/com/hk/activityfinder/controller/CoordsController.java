package com.hk.activityfinder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CoordsController {

    private final Logger logger = LoggerFactory.getLogger(CoordsController.class);

    @RequestMapping("/homepage/coords")
    public String getCoords(@RequestBody String coords) {
        logger.info("Coordinates from client [" + coords + "]");
        return "index";
    }

    @GetMapping("/homepage/coords")
    public String showHomePage() {
        return "index";
    }

}
