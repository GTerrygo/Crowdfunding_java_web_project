package com.gtw.crowd.service.impl;

import com.gtw.crowd.service.api.MemberService;
import com.gtw.crowd.entity.po.Member;
import com.gtw.crowd.entity.po.MemberExample;
import com.gtw.crowd.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author
 * @create 2020-11-15-21:09
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member getMemberPOByLoginAcct(String loginacct) {
        MemberExample example=new MemberExample();
        MemberExample.Criteria criteria=example.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);

        List<Member> members = memberMapper.selectByExample(example);
        if(members==null)
            return null;
        return members.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveMember(Member member) {
        memberMapper.insertSelective(member);
    }
}
