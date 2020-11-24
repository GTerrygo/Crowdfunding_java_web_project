package com.gtw.crowd.handler;

import com.gtw.crowd.api.MySQLRemoteService;
import com.gtw.crowd.api.RedisRemoteService;
import com.gtw.crowd.config.AWSSMSClientProperties;
import com.gtw.crowd.constant.Constant;
import com.gtw.crowd.entity.po.Member;
import com.gtw.crowd.entity.vo.MemberLoginVO;
import com.gtw.crowd.entity.vo.MemberVO;
import com.gtw.crowd.util.ResultEntity;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @create 2020-11-19-0:50
 */
@Controller
public class MemberHandler {

    @Autowired
    private AWSSMSClientProperties awssmsClientProperties;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @ResponseBody
    @RequestMapping("auth/member/send/SMS.json")
    public ResultEntity sendSMS(@RequestParam("phoneNumber") String phoneNumber){

        String AWSAccessKeyId=awssmsClientProperties.getAWSAccessKeyId();
        String AWSSecretKeyId=awssmsClientProperties.getAWSSecretKeyId();
        String sign=awssmsClientProperties.getSign();
        //creat AWS client and send SMS message to target phone
//        AWSSNSClient awsSnsClient = new AWSSNSClient(AWSAccessKeyId, AWSSecretKeyId);
//        ResultEntity<String> stringResultEntity = awsSnsClient.sendSMSMessage(phoneNumber, sign);
        //simulate SMS result
        ResultEntity sendResult=ResultEntity.success().add("data","2234");
        if(sendResult.getCode()==100){
            String code=(String) sendResult.getMap().get("data");
            String key= Constant.REDIS_CODE_PREFIX+phoneNumber;
            ResultEntity saveResult = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 3, TimeUnit.MINUTES);

            if(saveResult.getCode()==100)
                return ResultEntity.success();
            else
                return saveResult;
        }else return  sendResult;
    }

    @RequestMapping("/auth/member/do/reg")
    public String saveMember(MemberVO memberVO, Map map){
        //1.read code
        String phoneNumber=memberVO.getPhoneNumber();
        String RedisKey=Constant.REDIS_CODE_PREFIX+phoneNumber;
        ResultEntity codeResult = redisRemoteService.getRedisValueByKeyRemote(RedisKey);
        //2.check code
        if(codeResult.getCode()==200){
            map.put("message",codeResult.getMessage());
            return "member-reg";
        }

        String code=(String)codeResult.getMap().get("value");

        if(code==null){
            map.put(Constant.ATTR_NAME_MESSAGE,Constant.MESSAGE_CODE_NONEXOST);
            return "member-reg";
        }

        if(!code.equals(memberVO.getCode())){
            map.put(Constant.ATTR_NAME_MESSAGE,Constant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }

        //3.delete check code in redis
        redisRemoteService.remoteRedisValueByKeyRemote(RedisKey);

        //3.password encode
        String passwdBefore=memberVO.getUserpswd();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passedEncode=encoder.encode(passwdBefore);
        memberVO.setUserpswd(passedEncode);

        //4.membervo - memberpo
        Member member = new Member();
        BeanUtils.copyProperties(memberVO,member);
        ResultEntity resultEntity = mySQLRemoteService.saveMemberRemote(member);

        //5.check save is valid
        if(resultEntity.getCode().equals(200)){
            map.put("message",resultEntity.getMessage());
            return "member-reg";
        }
        return "redirect:http://localhost/auth/member/to/login/page";
    }

    @RequestMapping("/auth/member/do/login")
    public String loginAcct(@RequestParam("loginacct") String loginacct,
                            @RequestParam("userpswd") String userpswd,
                            HttpSession session,
                            Map map){
        //1. check ACCT
        ResultEntity<Member> memberResult = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);

        if(memberResult.getCode().equals(200)) {
            map.put(Constant.ATTR_NAME_MESSAGE,memberResult.getMessage());
            return "member-login";
        }

        Member member=memberResult.getData();
        if(member==null){
            map.put(Constant.ATTR_NAME_MESSAGE,Constant.LOGINACCT_STRING_NONEXIST);
            return "member-login";
        }

        //2. check password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String userpswdCorrect=member.getUserpswd();
        boolean matches = encoder.matches(userpswd,userpswdCorrect);
        if(matches==false){
            map.put(Constant.ATTR_NAME_MESSAGE,Constant.PASSWORD_STRING_ERROR);
            return "member-login";
        }

        //3. login successfully and redirect
        MemberLoginVO memberLoginVO = new MemberLoginVO(member.getId(),member.getUsername(),member.getEmail());
        session.setAttribute(Constant.ATTR_NAME_MEMBER,memberLoginVO);
        return "redirect:http://localhost/auth/member/to/center/page";
    }

    @RequestMapping("/auth/member/do/logout")
   public String memberLogout(HttpSession session){
        session.invalidate();
        return "redirect:http://localhost/";
    }

    @RequestMapping("/auth/member/do/mycrowdfunding")
    public String myCrowdFunding(){

        return "member-mycrowdfunding";
    }


}
