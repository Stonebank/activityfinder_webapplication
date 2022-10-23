package com.hk.activityfinder.model.email;

import com.hk.activityfinder.dto.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter @Setter
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    private int id;

    private String token;

    private Member member;

    private Date createdDate;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiration_in_minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdDate);
        calendar.add(Calendar.MINUTE, expiration_in_minutes);
        return new Date(calendar.getTime().getTime());
    }

}
