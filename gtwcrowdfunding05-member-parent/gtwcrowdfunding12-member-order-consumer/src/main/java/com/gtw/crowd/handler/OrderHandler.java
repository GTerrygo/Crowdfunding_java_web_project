package com.gtw.crowd.handler;

import com.gtw.crowd.api.MySQLRemoteService;
import com.gtw.crowd.config.PaypalProperties;
import com.gtw.crowd.constant.Constant;
import com.gtw.crowd.entity.vo.AddressVO;
import com.gtw.crowd.entity.vo.MemberLoginVO;
import com.gtw.crowd.entity.vo.OrderProjectVO;
import com.gtw.crowd.entity.vo.OrderVO;
import com.gtw.crowd.util.PayPalClient;
import com.gtw.crowd.util.ResultEntity;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * @create 2020-11-22-19:59
 */
@Controller
public class OrderHandler {

    @Autowired
    MySQLRemoteService mySQLRemoteService;

    @Autowired
    PaypalProperties properties;

    @RequestMapping("/pay/step/one/page/{returnId}")
    public String showOrderStepOnePage(@PathVariable("returnId") Integer returnId, HttpSession session){

        ResultEntity<OrderProjectVO> orderProjectVOResult=mySQLRemoteService.showOrderStepOneRemote(returnId);
        if(orderProjectVOResult.getCode().equals(100))
           session.setAttribute("orderProjectVO",orderProjectVOResult.getData());

        return "order-step1";
    }

    @RequestMapping("/pay/step/two/page/{returnCount}")
    public String showOrderStepTwoPage(@PathVariable("returnCount") Integer returnCount,HttpSession session){
        //. update orderProjectVO in redis session
        OrderProjectVO orderProjectVO = (OrderProjectVO)session.getAttribute("orderProjectVO");
        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO",orderProjectVO);
        //. get member ID in redis session
        MemberLoginVO memberLoginVO =(MemberLoginVO) session.getAttribute(Constant.ATTR_NAME_MEMBER);
        Integer memberId = memberLoginVO.getId();
//        MemberLoginVO memberLoginVO = new MemberLoginVO(2, "lkm","lkm@com");
//        session.setAttribute(Constant.ATTR_NAME_MEMBER,memberLoginVO);
//        Integer memberId=memberLoginVO.getId();
        //. get address information from mysql database
        ResultEntity<List<AddressVO>> addressVOResult=mySQLRemoteService.getAddressInfoRemote(memberId);

        if(addressVOResult.getCode().equals(200))
            return "order-step1";//you can mention user by adding some message into session or request here
        List<AddressVO> addressVOList = addressVOResult.getData();
        session.setAttribute("addressVOList",addressVOList);

        return "order-step2";
    }

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO,HttpSession session){

        ResultEntity<String> addressVOListResult= mySQLRemoteService.saveAddressRemote(addressVO);
        if(addressVOListResult.getCode().equals(200))
            return "order-step2";//you can mention user by adding some message into session or request here

        OrderProjectVO orderProjectVO = (OrderProjectVO)session.getAttribute("orderProjectVO");
        Integer returnCount = orderProjectVO.getReturnCount();
        return "redirect:/pay/step/two/page/"+returnCount;
    }

    @RequestMapping("/pay/step/three/page")
    public String doPayPal(OrderVO orderVO, HttpSession session, HttpServletRequest request){
        //1. get orderProjectVO from session
        OrderProjectVO orderProjectVO=(OrderProjectVO)session.getAttribute("orderProjectVO");
        orderVO.setOrderProjectVO(orderProjectVO);

        //2.generate order Num
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String user = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String orderNum = time + user;
        orderVO.setOrderNum(orderNum);

        //3.calculate order amount
        Double orderAmount = (double) (orderProjectVO.getSupportPrice() *
                orderProjectVO.getReturnCount() + orderProjectVO.getFreight());
        orderVO.setOrderAmount(orderAmount);

        session.setAttribute("orderVO",orderVO);
        //4.call paypal interface
        String  successUrl= "http://localhost/order/paypal/success";
        String  cancelUrl= "http://localhost/order/paypal/cancel";
        String paypalUrl = PayPalClient.pay(
                orderVO.getOrderAmount(),
                "USD",
                orderVO.getOrderNum(),
                "paypal",
                "sale",
                "decription!!!!!!!!!",
                cancelUrl,
                successUrl,
                properties.getClientId(),
                properties.getClientSecret(),
                properties.getMode());

        return paypalUrl;
    }

    @RequestMapping(method = RequestMethod.GET, value = "paypal/cancel")
    public String cancelPay(){
        return "cancel";
    }

    @RequestMapping(method = RequestMethod.GET, value ="paypal/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,HttpSession session){
        try {
            Payment payment = PayPalClient.executePayment(paymentId,
                                                            payerId,
                                                            properties.getClientId(),
                                                            properties.getClientSecret(),
                                                            properties.getMode());
            if(payment.getState().equals("approved")){
                String transationId = payment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId();
                OrderVO orderVO=(OrderVO) session.getAttribute("orderVO");
                orderVO.setPayOrderNum(transationId);
                ResultEntity<String> resultEntity=mySQLRemoteService.saveOrderVORemote(orderVO);
                if(resultEntity.getCode().equals(200))
                    return "redirect:/"; //set by yourself
                session.removeAttribute("orderVO");
                session.removeAttribute("orderProjectVO");
                return "order-step4";
            }
        } catch (PayPalRESTException e) {
        }
        return "redirect:/" ;//set by yourself
    }
}
