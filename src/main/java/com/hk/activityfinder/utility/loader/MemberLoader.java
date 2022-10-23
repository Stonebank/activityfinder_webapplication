package com.hk.activityfinder.utility.loader;

import com.google.gson.Gson;
import com.hk.activityfinder.dto.Member;
import com.hk.activityfinder.service.MemberHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MemberLoader {

    private final Logger logger = LoggerFactory.getLogger(MemberLoader.class);

    public void init() {

        MemberHandler memberHandler = new MemberHandler();
        File[] files = new File("./data/").listFiles();

        if (files == null) {
            logger.error("CRITICAL ERROR! Folder with data does not exist. Shutting backend systems...");
            System.exit(0);
            return;
        }

        if (files.length == 0) {
            logger.error("No members were found in the database.");
            return;
        }

        for (File file : files) {
            if (file == null || !file.getName().endsWith(".json")) {
                logger.error("ERROR! Skip " + file + ": null or not json");
                continue;
            }
            try (Reader reader = Files.newBufferedReader(Paths.get("./data/" + file.getName()), StandardCharsets.UTF_8)) {
                Member member = new Gson().fromJson(reader, Member.class);
                Member.members.add(member);
            } catch (IOException e) {
                logger.error("ERROR! Data from " + file.getName() + " was not loaded.");
            }
        }

        logger.info("Successfully initialized " + Member.members.size() + " accounts from the database");

    }

}
