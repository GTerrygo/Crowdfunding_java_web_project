package com.gtw.crowd.util.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2020-10-26-0:33
 */
public class ResultEntity {
    public static final String SUCCESS="success";
    public static final String ERROR="error";

    private Integer code;
    private String message;
    private Map<String,Object> map=new HashMap<>();


    public static ResultEntity success(){
        ResultEntity entity = new ResultEntity();
        entity.setMessage(SUCCESS);
        entity.setCode(100);
        return entity;
    }

    public static ResultEntity error(){
        ResultEntity entity = new ResultEntity();
        entity.setMessage(ERROR);
        entity.setCode(200);
        return entity;
    }

    public ResultEntity add(String key,Object value){
        this.map.put(key,value);
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

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
