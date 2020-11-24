package com.gtw.crowd.util;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author
 * @create 2020-11-19-0:25
 */
public class AWSS3Client {

    private  AmazonS3 s3Client;
    private  String bucketName;
    private String location="ca-central-1";

    public AWSS3Client(String AWSAccessKeyId,String AWSSecretKeyId){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKeyId);
        s3Client = AmazonS3Client.builder()
                .withRegion(location)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public ResultEntity<String> upload(File tempFile, String remoteFileName,String bucketName) {

        try {
            //Create stored path in S3 for example: /bucketName/20201119
            String folderName=new SimpleDateFormat("yyyyMMdd").format(new Date());
            String bucketPath = bucketName + "/" +folderName;
            //send file to s3
            s3Client.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
            URL url = s3Client.generatePresignedUrl(urlRequest);
            return new ResultEntity<String>(1).add(url.toString());
        } catch (SdkClientException e) {
            return ResultEntity.error(e.getMessage());
        }
    }

    public ResultEntity<String> upload(InputStream tempFile, String contentType, long contentLength,String originalFileName, String bucketName) {

        try {
            //Create stored path in S3 for example: /bucketName/20201119
            String folderName=new SimpleDateFormat("yyyyMMdd").format(new Date());
            // Generate the file name of the uploaded file when it is saved on the OSS server
            // Use UUID to generate file subject name
            String fileMainName = UUID.randomUUID().toString().replace("-", "");
            // Get the file extension from the original file name
            String extensionName = folderName+"/"+fileMainName+originalFileName.substring(originalFileName.lastIndexOf("."));

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(contentLength);

            //send file to s3
            s3Client.putObject(new PutObjectRequest(bucketName, extensionName, tempFile,metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            //design url
            String url="http://"+bucketName+".s3."+location+".amazonaws.com/"+extensionName;
            return new ResultEntity<String>(1).add(url);
        } catch (SdkClientException e) {
            return ResultEntity.error(e.getMessage());
        }
    }

}
