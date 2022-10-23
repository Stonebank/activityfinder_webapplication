package com.hk.activityfinder.interfaces;

import com.hk.activityfinder.dto.Member;

public interface MemberService {

    void saveUser(Member member);

    Member load(String email);
    Member load(Member member);

}
