package com.hk.activityfinder.controller;

import com.hk.activityfinder.dto.Member;
import com.hk.activityfinder.service.MemberHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

    @Autowired
    private MemberHandler memberHandler;

    @PostMapping("/login")
    public String handleLoginRequest(@ModelAttribute("member") Member member, HttpSession session, BindingResult result, Model model) {
        var email = (String) result.getFieldValue("email");
        if (email != null) {
            member = memberHandler.load(email);
            if (member == null) {
                result.rejectValue("email", "500", "E-mail address not found");
                return "/login";
            }
            var password = (String) result.getFieldValue("password");
            if (password != null) {
                if (!member.getPassword().equalsIgnoreCase(password)) {
                    result.rejectValue("password", "500", "Password is not correct");
                    return "/login";
                }
            }
        }
        model.addAttribute("member", member);
        session.setAttribute("member", member);
        return "/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("member");
        return "/index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

}
