package com.hk.activityfinder.interfaces;

import com.hk.activityfinder.dto.Member;

public interface MemberService {

    void saveUser(Member member);
    void createVerificationEmail(Member member, String token);

    Member load(String email);
    Member load(long id);

}
