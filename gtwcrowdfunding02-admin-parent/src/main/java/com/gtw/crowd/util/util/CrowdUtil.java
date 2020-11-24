package com.gtw.crowd.util.util;

import com.gtw.crowd.util.constant.Constant;
;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author
 * @create 2020-10-26-1:24
 */
public class CrowdUtil {

    public static boolean judgeRequstType(HttpServletRequest request){
        String acceptHeader=request.getHeader("Accept");
        String xRequestHeader=request.getHeader("X-Requested-With");

        return  acceptHeader !=null && acceptHeader.contains("Application/json")
        || xRequestHeader!=null &&xRequestHeader.equals("XMLHttpRequest");

    }

    /**
     *This function is for md5 encoding
     * @param source :the original password
     * @return the encoded password
     */
    public static String md5Code(String source){
        if(source==null || source.length()==0){
            throw new RuntimeException(Constant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            String algorithm="md5";
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] input=source.getBytes();
            byte[] output=digest.digest(input);
            int signum=1;
            BigInteger bigInteger = new BigInteger(signum, output);
            int radix=16;
            String encoded = bigInteger.toString(16).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
