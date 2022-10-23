package com.hk.activityfinder.controller;

import com.hk.activityfinder.dto.Member;
import com.hk.activityfinder.service.MemberHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private MemberHandler memberHandler;

    @GetMapping("/homepage")
    public String showHomePage(HttpSession session, HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equalsIgnoreCase("uuid")) {
                System.out.println(cookie.getValue());
                Member member = memberHandler.load(Long.parseLong(cookie.getValue()));
                session.setAttribute("member", member);
                System.out.println(member);
            }
        }
        return "/index";
    }


}
