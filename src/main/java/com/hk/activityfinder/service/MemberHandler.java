package com.hk.activityfinder.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hk.activityfinder.dto.Member;
import com.hk.activityfinder.interfaces.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MemberHandler implements MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberHandler.class);

    @Override
    public Member load(Member member) {
        try (Reader reader = Files.newBufferedReader(Paths.get("./data/" + member.getId() + ".json"), StandardCharsets.UTF_8)) {
            member = new Gson().fromJson(reader, Member.class);
            logger.info("Successfully loaded member: " + member.getId() + ".");
            return member;
        } catch (IOException e) {
            logger.error("ERROR! Data from " + member.getId() + " was not loaded.");
            return null;
        }
    }

    @Override
    public void saveUser(Member member) {
        try (Writer writer = Files.newBufferedWriter(Paths.get("./data/" + member.getId() + ".json"), StandardCharsets.UTF_8)) {
            new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(member, writer);
            logger.info("Successfully saved user " + member);
        } catch (IOException e) {
            logger.error("ERROR! Could not save user with id " + member.getId() + ".");
        }

    }

}
