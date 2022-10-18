package com.hk.activityfinder.service;

import com.hk.activityfinder.interfaces.ActivityService;
import com.hk.activityfinder.model.activities.Activity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Activities implements ActivityService {

    @Override
    public ArrayList<Activity> getAllActivities() {
        return Activity.activities;
    }

}
