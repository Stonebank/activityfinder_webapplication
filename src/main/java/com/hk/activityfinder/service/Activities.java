package com.hk.activityfinder.service;

import com.hk.activityfinder.interfaces.ActivityService;
import com.hk.activityfinder.model.activities.Activity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Activities implements ActivityService {

    @Override
    public List<Activity> getAllActivities() {
        Activity activity = new Activity();
        activity.setName("Frederiksborg slot");
        activity.setAddress("Slangerupgade 8 3400 Hillerød");
        activity.setDistance(225);
        return List.of(activity);
    }

}
