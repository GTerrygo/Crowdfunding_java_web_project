package com.gtw.crowd.util;

import com.amazonaws.services.sqs.model.Message;
import com.sun.net.httpserver.Authenticator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2020-10-26-0:33
 */
public class  ResultEntity <T>{
    public static final String SUCCESS="success";
    public static final String ERROR="error";

    private Integer code;
    private String message;
    private Map<String,T> map=new HashMap<>();
    private T data;


    public  ResultEntity(){}

    public ResultEntity(Integer successOrFail){
        if(successOrFail==1){
            code=100;
            message=SUCCESS;
        }else {
            code=200;
            message=ERROR;
        }
    }

    public ResultEntity(Integer successOrFail,String message){
        if(successOrFail==1){
            code=100;
            this.message=message;
        }else {
            code=200;
            this.message=message;
        }
    }

    public static ResultEntity success(){
        ResultEntity entity = new ResultEntity();
        entity.setMessage(SUCCESS);
        entity.setCode(100);
        return entity;
    }
    public static ResultEntity success(String message){
        ResultEntity entity = new ResultEntity();
        entity.setMessage(message);
        entity.setCode(100);
        return entity;
    }


    public static ResultEntity error(){
        ResultEntity entity = new ResultEntity();
        entity.setMessage(ERROR);
        entity.setCode(200);
        return entity;
    }
    public static ResultEntity error(String message){
        ResultEntity entity = new ResultEntity();
        entity.setMessage(message);
        entity.setCode(200);
        return entity;
    }

    public ResultEntity add(String key,T value){
        this.map.put(key,value);
        return this;
    }

    public ResultEntity add(T data){
        this.data=data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, T> getMap() {
        return map;
    }

    public void setMap(Map<String, T> map) {
        this.map = map;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data=data;
    }
}
