package com.gtw.crowd.service.api;

import com.gtw.crowd.entity.po.Member;

/**
 * @author
 * @create 2020-11-15-21:09
 */
public interface MemberService {

    Member getMemberPOByLoginAcct(String loginacct);

    void saveMember(Member member);
}
