package com.hk.activityfinder;

import com.hk.activityfinder.utility.AES256;
import com.hk.activityfinder.utility.JSONConverter;
import com.hk.activityfinder.utility.loader.ActivityLoader;
import com.hk.activityfinder.utility.loader.MemberLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Launch {

    public static void main(String[] args) {
        SpringApplication.run(Launch.class, args);
        AES256.writeSecretKey(new File("./aes256key.txt"));
        new JSONConverter(new File("./activity.txt"), "./activity.json").convert();
        new ActivityLoader().init();
        new MemberLoader().init();
    }

}
