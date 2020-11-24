package com.gtw.crowd.service.impl;

import com.gtw.crowd.constant.Constant;
import com.gtw.crowd.entity.po.MemberConfirmInfoPO;
import com.gtw.crowd.entity.po.MemberLaunchInfoPO;
import com.gtw.crowd.entity.po.ProjectPO;
import com.gtw.crowd.entity.po.ReturnPO;
import com.gtw.crowd.entity.vo.*;
import com.gtw.crowd.mapper.*;
import com.gtw.crowd.service.api.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2020-11-20-3:22
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectPOMapper projectPOMapper;
    @Autowired
    MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
    @Autowired
    MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
    @Autowired
    ReturnPOMapper returnPOMapper;

    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {

        //1. save project
        ProjectPO projectPO=new ProjectPO();
        BeanUtils.copyProperties(projectVO,projectPO);
        projectPO.setMemberid(memberId);
        projectPO.setCreatedate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        projectPO.setStatus(0);
        projectPOMapper.insertSelective(projectPO);
        Integer projectId=projectPO.getId();

        //2. save project, classification relation information
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList,projectId);

        //3  save project, tag relation information
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList,projectId);

        //4. save project detail picture path
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectPOMapper.insertDetailPicturePath(detailPicturePathList,projectId);

        //5. save project launcher information
        MemberLaunchInfoVO memberLaunchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO=new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLaunchInfoVO,memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);

        //6. save project return information
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<ReturnPO> returnPOList=new ArrayList<>();
        for(ReturnVO returnVO:returnVOList){
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO,returnPO);
            returnPOList.add(returnPO);
        }
        returnPOMapper.insertBatch(returnPOList,projectId);
        //7. save project confirm information
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO,memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);

    }

    @Override
    public List<PortalTypeVO> getPortalTypeVOList() {
        List<PortalTypeVO> portalTypeVOList = projectPOMapper.selectPortalTypeVOList();
        return portalTypeVOList;
    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer projectId) {
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(projectId);
        // transfer integer status to character
        Integer status=detailProjectVO.getStatus();
        switch (status) {
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("已关闭");
                break;
            default:
                break;
        }

        //calculate time
        // 2020-10-15
        String deployDate = detailProjectVO.getDeployDate();
        // get current date
        Date currentDay = new Date();
        // Parse the crowdfunding date into Date type
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date deployDay = format.parse(deployDate);
            // 获取当前当前日期的时间戳
            long currentTimeStamp = currentDay.getTime();
            // Get the timestamp of the current current date
            long deployTimeStamp = deployDay.getTime();
            // Subtract two timestamps to calculate the current elapsed time
            long pastDays = (currentTimeStamp - deployTimeStamp) / 1000 / 60 / 60 / 24;

            //get the total day
            Integer totalDays = detailProjectVO.getDay();
            // Use the total number of crowdfunding days minus the number of days that have passed to get the remaining days
            Integer lastDay = (int) (totalDays - pastDays);
            detailProjectVO.setLastDay(lastDay);
            return detailProjectVO;
        }catch(Exception e){
            return null;
        }

    }
}
