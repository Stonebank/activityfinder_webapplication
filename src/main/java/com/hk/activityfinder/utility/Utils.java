package com.hk.activityfinder.utility;

import java.util.UUID;

public class Utils {

    public static boolean checkPasswordRequirements(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }

    public static int generateUniqueID() {
        return Integer.parseInt(String.valueOf(UUID.randomUUID().hashCode()).replaceAll("-", ""));
    }

}
