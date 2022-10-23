package com.hk.activityfinder.dto;

import com.google.gson.annotations.Expose;
import com.hk.activityfinder.utility.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class Member {

    public static ArrayList<Member> members = new ArrayList<>();

    @Expose
    private int id = Utils.generateUniqueID();

    @Expose
    private String name;

    @Expose
    private String email;
    private String repeat_email;

    @Expose
    private String password;
    private String repeat_password;

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
