package com.gtw.paypal.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2020-11-22-2:25
 */

public class PaypalUtil {

        public static APIContext creatAPIContext(String clientId, String clientSecret, String mode) throws PayPalRESTException {
            //1. set mode
            Map<String, String> sdkConfig = new HashMap<>();
            sdkConfig.put("mode", mode);
            //2.set Credential
            OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(clientId, clientSecret, sdkConfig);
            //3.get apiContext
            APIContext apiContext = new APIContext(oAuthTokenCredential.getAccessToken());
            apiContext.setConfigurationMap(sdkConfig);
            return apiContext;
        }


    }

