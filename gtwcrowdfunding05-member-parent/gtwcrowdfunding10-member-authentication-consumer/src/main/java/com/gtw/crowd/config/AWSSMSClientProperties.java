package com.gtw.crowd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020-11-16-21:20
 */
@Component
@ConfigurationProperties(prefix = "aws.sns.sms")
public class AWSSMSClientProperties {

    String AWSAccessKeyId;
    String AWSSecretKeyId;
    String sign;

    public String getAWSAccessKeyId() {
        return AWSAccessKeyId;
    }

    public String getAWSSecretKeyId() {
        return AWSSecretKeyId;
    }

    public String getSign() {
        return sign;
    }

    public void setAWSAccessKeyId(String AWSAccessKeyId) {
        this.AWSAccessKeyId = AWSAccessKeyId;
    }

    public void setAWSSecretKeyId(String AWSSecretKeyId) {
        this.AWSSecretKeyId = AWSSecretKeyId;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
