package com.hk.activityfinder.controller;

import com.hk.activityfinder.dto.Member;
import com.hk.activityfinder.service.MemberHandler;
import com.hk.activityfinder.utility.AES256;
import org.apache.tomcat.util.net.openssl.ciphers.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

    @Autowired
    private MemberHandler memberHandler;

    @GetMapping("/register/confirmation")
    public String confirmRegistration(@RequestParam("token") String token) {
        Member member = memberHandler.load(Long.parseLong(token));
        if (member == null)
            return "redirect:/error-500";
        if (member.getId() != Long.parseLong(token))
            return "redirect:/error-500";
        member.setConfirmed_account(true);
        return "confirmation";
    }

    @PostMapping("/register/create-account")
    public String register(@ModelAttribute("member") Member member, BindingResult result) {
        if (memberHandler.load((String) result.getFieldValue("email")) != null) {
            result.rejectValue("email", "500", "The email address you entered is already in use.");
            return "/register";
        }
        if (!member.getEmail().equalsIgnoreCase(member.getRepeat_email())) {
            result.rejectValue("repeat_email", "500", "The two email addresses that you entered do not match.");
            return "/register";
        }
        if (!member.getPassword().equalsIgnoreCase(member.getRepeat_password())) {
            result.rejectValue("password", "500", "The entered passwords do not match.");
            return "/register";
        }
        member.setPassword(AES256.encrypt(member.getPassword()));
        memberHandler.saveUser(member);
        memberHandler.createVerificationEmail(member, String.valueOf(member.getId()));
        return "confirmation_sent";
    }

    @GetMapping("/register")
    public String showRegisterPage(@ModelAttribute("member") Member member, HttpSession session, Model model) {
        model.addAttribute("member", member);
        return session.getAttribute("member") != null ? "error-404" : "register";
    }

}
