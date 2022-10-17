package com.hk.activityfinder.utility;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BackgroundThread {

    public static final ThreadPoolExecutor EMAIL_EXECUTOR = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

}
