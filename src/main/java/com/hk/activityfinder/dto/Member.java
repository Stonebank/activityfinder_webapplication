package com.hk.activityfinder.dto;

import com.hk.activityfinder.utility.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

    private int id = Utils.generateUniqueID();

    private String name;
    private String email;
    private String repeat_email;
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
