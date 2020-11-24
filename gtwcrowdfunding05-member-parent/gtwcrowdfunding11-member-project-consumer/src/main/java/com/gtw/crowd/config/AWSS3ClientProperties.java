package com.gtw.crowd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020-11-16-21:20
 */
@Component
@ConfigurationProperties(prefix = "aws.s3")
public class AWSS3ClientProperties {

    String AWSAccessKeyId;
    String AWSSecretKeyId;
    String bucketName;

    public String getAWSAccessKeyId() {
        return AWSAccessKeyId;
    }

    public void setAWSAccessKeyId(String AWSAccessKeyId) {
        this.AWSAccessKeyId = AWSAccessKeyId;
    }

    public String getAWSSecretKeyId() {
        return AWSSecretKeyId;
    }

    public void setAWSSecretKeyId(String AWSSecretKeyId) {
        this.AWSSecretKeyId = AWSSecretKeyId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
