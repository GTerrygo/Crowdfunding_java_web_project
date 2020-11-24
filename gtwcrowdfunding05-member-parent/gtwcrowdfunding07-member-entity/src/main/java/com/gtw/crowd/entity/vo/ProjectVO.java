package com.gtw.crowd.entity.vo;

import java.util.List;

/**
 * @author
 * @create 2020-11-19-20:49
 */
public class ProjectVO {

    private static final long serialVersionUID = 1L;
    private List<Integer> typeIdList;
    // 标签id 集合
    private List<Integer> tagIdList;
    // 项目名称
    private String projectName;
    // 项目描述
    private String projectDescription;
    // 计划筹集的金额
    private Integer money;
    // 筹集资金的天数
    private Integer day;
    // 创建项目的日期
    private String createdate;
    // 头图的路径
    private String headerPicturePath;
    // 详情图片的路径
    private List<String> detailPicturePathList;
    // 发起人信息
    private MemberLaunchInfoVO memberLauchInfoVO;
    // 回报信息集合
    private List<ReturnVO> returnVOList;
    // 发起人确认信息
    private MemberConfirmInfoVO memberConfirmInfoVO;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Integer> getTypeIdList() {
        return typeIdList;
    }

    public void setTypeIdList(List<Integer> typeIdList) {
        this.typeIdList = typeIdList;
    }

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getHeaderPicturePath() {
        return headerPicturePath;
    }

    public void setHeaderPicturePath(String headerPicturePath) {
        this.headerPicturePath = headerPicturePath;
    }

    public List<String> getDetailPicturePathList() {
        return detailPicturePathList;
    }

    public void setDetailPicturePathList(List<String> detailPicturePathList) {
        this.detailPicturePathList = detailPicturePathList;
    }

    public MemberLaunchInfoVO getMemberLauchInfoVO() {
        return memberLauchInfoVO;
    }

    public void setMemberLauchInfoVO(MemberLaunchInfoVO memberLauchInfoVO) {
        this.memberLauchInfoVO = memberLauchInfoVO;
    }

    public List<ReturnVO> getReturnVOList() {
        return returnVOList;
    }

    public void setReturnVOList(List<ReturnVO> returnVOList) {
        this.returnVOList = returnVOList;
    }

    public MemberConfirmInfoVO getMemberConfirmInfoVO() {
        return memberConfirmInfoVO;
    }

    public void setMemberConfirmInfoVO(MemberConfirmInfoVO memberConfirmInfoVO) {
        this.memberConfirmInfoVO = memberConfirmInfoVO;
    }
}

