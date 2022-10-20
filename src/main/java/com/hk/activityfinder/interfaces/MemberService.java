package com.hk.activityfinder.interfaces;

import com.hk.activityfinder.dto.Member;

import java.util.List;

public interface MemberService {

    void saveUser(Member member);

    Member load(Member member);

}
