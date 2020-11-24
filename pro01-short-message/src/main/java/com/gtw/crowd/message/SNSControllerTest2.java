package com.gtw.crowd.message;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2020-11-18-23:35
 */
public class SNSControllerTest2 {
    private static String access_key_id = "AKIA334PV4R4DUR3LZ67";
    private static String secret_key_id = "Kb0fnQrpHbI939fUy750/BRniRATan++crXpFiG0";

    private Map<String, MessageAttributeValue> smsAttributes;
    private static AmazonSNS snsClient;

    static {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(access_key_id, secret_key_id);
        snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("ca-central-1")
                .build();
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
        return snsClient.publish(
                new PublishRequest()
                        .withMessage(message)
                        .withPhoneNumber(phoneNumber)
                        .withMessageAttributes(getDefaultSMSAttributes()));
    }

    public static void main(String[] args) {
        SNSControllerTest2 snsController = new SNSControllerTest2();
        snsController.sendSMSMessage("15146490626","123");
    }
}
