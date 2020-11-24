package com.gtw.crowd.handler;

import com.gtw.crowd.constant.Constant;
import com.gtw.crowd.service.api.MemberService;
import com.gtw.crowd.entity.po.Member;
import com.gtw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @create 2020-11-15-21:05
 */
@RestController
public class MemberProviderHandler {
    @Autowired
    MemberService memberService;

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<Member> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){
        try {
            Member member = memberService.getMemberPOByLoginAcct(loginacct);
            return new ResultEntity<Member>(1).add(member);
        }catch (Exception e){
            return ResultEntity.error(e.getMessage());
        }
    }

    @RequestMapping("/auth/member/do/reg/remote")
    ResultEntity saveMemberRemote(@RequestBody  Member member){
        try {
            memberService.saveMember(member);
            return ResultEntity.success();
        }catch (Exception e){
            if(e instanceof DuplicateKeyException)
                return ResultEntity.error(Constant.MESSAGE_LOGIN_ACCT_REPEAT);
            else
                return ResultEntity.error(e.getMessage());
        }
    }
}
