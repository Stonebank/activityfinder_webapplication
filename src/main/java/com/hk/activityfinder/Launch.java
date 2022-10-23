package com.hk.activityfinder;

import com.hk.activityfinder.utility.loader.ActivityLoader;
import com.hk.activityfinder.utility.loader.MemberLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launch {

    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
        new ActivityLoader().init();
        new MemberLoader().init();
    }

}
