package com.hk.activityfinder.utility;

import java.util.UUID;

public class Utils {

    public static int generateUniqueID() {
        var uuid = UUID.randomUUID();
        var uid = uuid.hashCode();
        return Integer.parseInt(String.valueOf(uid).replaceAll("-", ""));
    }

}
