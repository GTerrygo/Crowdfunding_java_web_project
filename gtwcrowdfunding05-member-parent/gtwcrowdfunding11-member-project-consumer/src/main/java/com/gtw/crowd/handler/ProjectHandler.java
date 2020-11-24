package com.gtw.crowd.handler;

import com.amazonaws.partitions.PartitionRegionImpl;
import com.gtw.crowd.api.MySQLRemoteService;
import com.gtw.crowd.config.AWSS3ClientProperties;
import com.gtw.crowd.constant.Constant;
import com.gtw.crowd.entity.po.ReturnPO;
import com.gtw.crowd.entity.vo.*;
import com.gtw.crowd.util.AWSS3Client;
import com.gtw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2020-11-19-3:59
 */
@Controller
public class ProjectHandler {

    @Autowired
    private AWSS3ClientProperties awss3ClientProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/create/project/information")
    public String creatInfo(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPictureList, HttpSession session, Map map) throws IOException {

        //1. creat AWS S3 Client
        AWSS3Client client = new AWSS3Client(awss3ClientProperties.getAWSAccessKeyId(), awss3ClientProperties.getAWSSecretKeyId());
        //2. check headerPicture if null
        if(headerPicture.isEmpty()) {
            map.put(Constant.ATTR_NAME_MESSAGE,Constant.MESSAGE_UPLOAD_ERROR);
            return "project-launch";
        }
        //3. check headerPicture if load failed
        ResultEntity<String> uploadHeaderPictureResult = client.upload(headerPicture.getInputStream(), headerPicture.getContentType(), headerPicture.getSize(), headerPicture.getOriginalFilename(), awss3ClientProperties.getBucketName());
        if(uploadHeaderPictureResult.getCode().equals(200)) {
            map.put(Constant.ATTR_NAME_MESSAGE, Constant.MESSAGE_UPLOAD_ERROR);
            return "project-launch";
        }
        String headerPictureUri = uploadHeaderPictureResult.getData();
        projectVO.setHeaderPicturePath(headerPictureUri);

        //4. load detailPictureList
        List<String> detailPictureArrayList=new ArrayList<>();
        for(MultipartFile detailPicture:detailPictureList){
            if(!detailPicture.isEmpty()){
                ResultEntity<String> uploadDetailPictureResult = client.upload(detailPicture.getInputStream(), detailPicture.getContentType(), detailPicture.getSize(), detailPicture.getOriginalFilename(), awss3ClientProperties.getBucketName());
                if(uploadDetailPictureResult.getCode().equals(100)){
                    String detailPictureUri=uploadDetailPictureResult.getData();
                    detailPictureArrayList.add(detailPictureUri);
                }
            }
        }
        projectVO.setDetailPicturePathList(detailPictureArrayList);
        //save project in session
        session.setAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT,projectVO);

        return "redirect:http://localhost/project/return/info/page";
    }

    @RequestMapping("/create/upload/return/picture.json")
    @ResponseBody
    public ResultEntity returnPicture(MultipartFile returnPicture) throws IOException {
        //1. check if it is null
        if (returnPicture.isEmpty())
            return ResultEntity.error(Constant.MESSAGE_UPLOAD_ERROR);
        //2.load
        AWSS3Client client = new AWSS3Client(awss3ClientProperties.getAWSAccessKeyId(), awss3ClientProperties.getAWSSecretKeyId());
        ResultEntity<String> returnPictureResult = client.upload(returnPicture.getInputStream(), returnPicture.getContentType(), returnPicture.getSize(), returnPicture.getOriginalFilename(), awss3ClientProperties.getBucketName());

        //3.check if load is success
        if(returnPictureResult.getCode().equals(200))
            return ResultEntity.error(Constant.MESSAGE_UPLOAD_ERROR);

        System.out.println(returnPictureResult.getData());
        //if success return uri
        return  returnPictureResult;
    }

    @RequestMapping("/create/save/return.json")
    @ResponseBody
    public ResultEntity saveReturnInfo(ReturnVO returnVO,HttpSession session){
        try {
            // check temple projectVO
            ProjectVO projectVO = (ProjectVO)session.getAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT);
            if (projectVO == null)
                return ResultEntity.error(Constant.MESSAGE_UPLOAD_ERROR);

            //get returnInfo from projectVO
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            if(returnVOList==null || returnVOList.size()==0){
                returnVOList=new ArrayList<ReturnVO>();
                projectVO.setReturnVOList(returnVOList);
            }

            //save this returnInfo in the list
            returnVOList.add(returnVO);

            //save new temple projectVO in redis session
            session.setAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT,projectVO);

            return ResultEntity.success();
        } catch (Exception exception) {
            return ResultEntity.error(exception.getMessage());
        }

    }

    @RequestMapping("/create/confirm")
    public String saveConfirm(Map map, HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO){
        //1. check temple projectVO
        ProjectVO projectVO = (ProjectVO)session.getAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT);
        if (projectVO == null)
            throw new RuntimeException(Constant.MESSAGE_UPLOAD_ERROR);
        //2. save confirmInfo in projectVO
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        //3. get current user id from redis session
        MemberLoginVO memberLoginVO = (MemberLoginVO)session.getAttribute(Constant.ATTR_NAME_MEMBER);
        Integer memberId = memberLoginVO.getId();
//        Integer memberId=3;

        //4. call remote function to save projectvo in mysql
        ResultEntity resultEntity = mySQLRemoteService.saveProjectRemote(projectVO, memberId);

        //5. check if 4 is success
        if(resultEntity.getCode().equals(200)) {
            map.put(Constant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "project-confirm";
        }

        //6.remove temple projectVO from redis session
        session.removeAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT);

        return "redirect:http://localhost/project/create/project/success";
    }

    @RequestMapping("/portal/show/project/detail/{projectId}")
    public String getDetailProjectInfo(@PathVariable("projectId") Integer projectId,Map map){

        ResultEntity<DetailProjectVO> resultEntity=mySQLRemoteService.getDetailProjectVORemote(projectId);

        if(resultEntity.getCode().equals(200)) {
            map.put(Constant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "project-detail";
        }

        DetailProjectVO detailProjectVO = resultEntity.getData();

        if(detailProjectVO==null){
            map.put(Constant.ATTR_NAME_MESSAGE,Constant.MESSAGE_UPLOAD_ERROR);
            return "project-detail";
        }

        map.put(Constant.ATTR_NAME_DETAILPROJECTVO,detailProjectVO);
        return "project-detail";

        }

}
