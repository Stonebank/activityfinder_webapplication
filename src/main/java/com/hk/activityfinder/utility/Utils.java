package com.hk.activityfinder.utility;

import java.util.UUID;

public class Utils {

    public static int generateUniqueID() {
        return Integer.parseInt(String.valueOf(UUID.randomUUID().hashCode()).replaceAll("-", ""));
    }

}
