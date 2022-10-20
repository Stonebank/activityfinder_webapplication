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

@Controller
public class AuthenticationController {

    @Autowired
    private MemberHandler memberHandler;

    @GetMapping("/register")
    public String showRegisterPage(@ModelAttribute("member") Member member, Model model) {
        model.addAttribute("member", member);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("member") Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("member", member);
            return "/register";
        }
        memberHandler.saveUser(member);
        return "redirect:/register?success";
    }

}
