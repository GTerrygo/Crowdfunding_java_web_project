package com.gtw.crowd.util;

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
 * @create 2020-11-19-0:39
 */
public class AWSSNSClient {
    private Map<String, MessageAttributeValue> smsAttributes;
    private static AmazonSNS snsClient;

    public AWSSNSClient(String AWSAccessKeyId,String AWSSecretKeyId){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKeyId);
        snsClient = AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("ca-central-1")
                .build();
    }

    protected Map<String, MessageAttributeValue> getDefaultSMSAttributes() {
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

    public ResultEntity<String> sendSMSMessage(String phoneNumber, String sign) {
        try {
            //create random 4 digit number
            StringBuilder code=new StringBuilder();
            for(int i=0;i<4;i++){
                int random =(int) (Math.random()*10);
                code.append(random);
            }
            String message=sign+code;

            PublishResult publish = snsClient.publish(
                    new PublishRequest()
                            .withMessage(message)
                            .withPhoneNumber(phoneNumber)
                            .withMessageAttributes(getDefaultSMSAttributes()));
            return new ResultEntity<String>(1).add(code.toString());
        } catch (Exception exception) {
            return ResultEntity.error(exception.getMessage());
        }
    }
}
