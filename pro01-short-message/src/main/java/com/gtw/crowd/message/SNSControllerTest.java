package com.gtw.crowd.message;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import javax.sound.midi.ShortMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2020-11-16-2:49
 */


public class SNSControllerTest {
    private Map<String, MessageAttributeValue> smsAttributes;

    private String AWSAccessKeyId;

    private String AWSSecretKeyId;

    public SNSControllerTest(String AWSAccessKeyId, String AWSSecretKeyId) {
        this.AWSAccessKeyId = AWSAccessKeyId;
        this.AWSSecretKeyId = AWSSecretKeyId;
    }

    public Map<String, MessageAttributeValue> getDefaultSMSAttributes() {
        if (smsAttributes == null) {
            smsAttributes = new HashMap<String,MessageAttributeValue>();
            smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                    .withStringValue("1")
                    .withDataType("String"));
            smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                    .withStringValue("0.05")
                    .withDataType("Number"));
            smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                    .withStringValue("Transactional")
                    .withDataType("String"));
        }
        return smsAttributes;
    }

    public PublishResult sendSMSMessage(String phoneNumber, String message) {
        return sendSMSMessage(phoneNumber, message, getDefaultSMSAttributes());
    }

    public PublishResult sendSMSMessage(String phoneNumber, String message, Map<String, MessageAttributeValue> smsAttributes) {
//        final AWSCredentials awsCredentials = new AWSCredentials() {
//
//            public String getAWSAccessKeyId() {
//                return AWSAccessKeyId; // IAM ACCESS_KEY
//            }
//
//
//            public String getAWSSecretKey() {
//                return AWSSecretKeyId; // IAM SECRET_KEY
//            }
//        };
//        AWSCredentialsProvider provider = new AWSCredentialsProvider() {
//
//            public AWSCredentials getCredentials() {
//                return awsCredentials;
//            }
//
//
//            public void refresh() {
//            }
//        };
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKeyId);
        AWSStaticCredentialsProvider provider = new AWSStaticCredentialsProvider(awsCredentials);
        AmazonSNS amazonSNS = AmazonSNSClientBuilder.standard()
                            .withCredentials(provider)
                            .withRegion("ca-central-1")
                            .build();

        return amazonSNS.publish(
                new PublishRequest()
                        .withMessage(message)
                        .withPhoneNumber(phoneNumber)
                        .withMessageAttributes(smsAttributes)
        );
    }

//    public static void main(String[] args) {
////        //AmazonSNSClient client = new AmazonSNSClient(credentialsProvider);
////        //AWSCredentialsProviderChain chain = new AWSCredentialsProviderChain(credentialsProvider);
////        //chain.setReuseLastProvider(true);
////        //credentialsProvider.getCredentials();
//        SNSControllerTest shortMessage = new SNSControllerTest("AKIA334PV4R4DUR3LZ67","Kb0fnQrpHbI939fUy750/BRniRATan++crXpFiG0");
//        PublishResult publishResult = shortMessage.sendSMSMessage("your phone number", "test1");
//        System.out.println(publishResult);
//    }
//
}
