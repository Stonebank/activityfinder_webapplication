package com.hk.activityfinder.controller;

import com.hk.activityfinder.model.contact.User;
import com.hk.activityfinder.service.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private Email email;

    @GetMapping("/contact")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "contact";
    }

    @PostMapping("/contact")
    public String submitForm(@ModelAttribute("user") User user) {
        email.sendMail("hassan_99@live.dk", user.getName() + " has sent you an email", "Hi Hassan<br><br>" + user.getName() + " (" + user.getEmail() + ") has sent you a message:<br><br>Topic: " + user.getTopic() + "<br>Message: " + user.getMessage());
        return "contact";
    }

}
