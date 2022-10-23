package com.hk.activityfinder.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hk.activityfinder.dto.Member;
import com.hk.activityfinder.interfaces.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class MemberHandler implements MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberHandler.class);

    @Autowired
    private Email email;

    @Override
    public Member load(long id) {
        try (Reader reader = Files.newBufferedReader(Paths.get("./data/" + id + ".json"), StandardCharsets.UTF_8)) {
            Member member = new Gson().fromJson(reader, Member.class);
            logger.info("Successfully loaded member: " + member.getId() + ".");
            return member;
        } catch (IOException e) {
            logger.error("ERROR! Data from " + id + " was not loaded.");
            return null;
        }
    }

    @Override
    public Member load(String email) {
        for (Member member : Member.members) {
            if (member == null || !member.getEmail().equalsIgnoreCase(email))
                continue;
            return member;
        }
        return null;
    }

    @Override
    public void saveUser(Member member) {
        try (Writer writer = Files.newBufferedWriter(Paths.get("./data/" + member.getId() + ".json"), StandardCharsets.UTF_8)) {
            new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().disableHtmlEscaping().create().toJson(member, writer);
            if (!Member.members.contains(member))
                Member.members.add(member);
            logger.info("Successfully saved user " + member);
        } catch (IOException e) {
            logger.error("ERROR! Could not save user with id " + member.getId() + ".");
        }

    }

    @Override
    public void createVerificationEmail(Member member, String token) {
        String link = "http://localhost:8080/register/confirmation?token=" + token;
        email.sendMail("Confirm your registration", "Hello " + member.getName() + "!<br><br>Thank you for registering. Click the link below to complete your registration<br>" + link);
    }

}
