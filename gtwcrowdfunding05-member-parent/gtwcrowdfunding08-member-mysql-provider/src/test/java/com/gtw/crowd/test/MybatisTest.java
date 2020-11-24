package com.gtw.crowd.test;

import com.gtw.crowd.entity.po.Member;
import com.gtw.crowd.entity.po.ProjectPO;
import com.gtw.crowd.entity.vo.PortalProjectVO;
import com.gtw.crowd.entity.vo.PortalTypeVO;
import com.gtw.crowd.mapper.MemberMapper;
import com.gtw.crowd.mapper.ProjectPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author
 * @create 2020-11-15-19:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private ProjectPOMapper projectPOMapper;
    @Test
    public void test(){
        Member member = memberMapper.selectByPrimaryKey(1);
        System.out.println(member);
    }

    @Test
    public void test2(){
        List<PortalTypeVO> portalTypeVOList = projectPOMapper.selectPortalTypeVOList();
        for(PortalTypeVO portalTypeVO:portalTypeVOList){
            System.out.println(portalTypeVO.getId()+" "+portalTypeVO.getName()+" "+portalTypeVO.getRemark());
            List<PortalProjectVO> portalProjectVOList = portalTypeVO.getPortalProjectVOList();
            for(PortalProjectVO portalProjectVO:portalProjectVOList){
                System.out.println(portalProjectVO);
            }
        }

    }
}
