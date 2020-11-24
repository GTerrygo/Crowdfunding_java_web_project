package com.gtw.crowd.constant;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author
 * @create 2020-11-18-0:54
 */
public class AccessResource {
    private final static Set<String> accessPassResources=new HashSet<>();
    static {
        accessPassResources.add("/auth/member/to/reg/page");
        accessPassResources.add("/auth/member/to/login/page");
        accessPassResources.add("/auth/member/send/SMS.json");
        accessPassResources.add("/auth/member/do/reg");
        accessPassResources.add("/auth/member/do/login");
        accessPassResources.add("/auth/member/do/logout");
        accessPassResources.add("/");
        accessPassResources.add("/favicon.ico");
    }

    public static boolean judgeAccess(String path){

        if(path==null || path.length()==0)
            throw new RuntimeException(Constant.MESSAGE_STRING_INVALIDATE);

        if(accessPassResources.contains(path))
            return true;

        String[] split=path.split("/");
        String first=split[1];
        if(first.equals("static"))
            return true;

        return false;
    }
}
