package com.gtw.AWS.S3;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kms.model.GenerateDataKeyRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 * @create 2020-11-18-22:33
 */

public class S3ControllerTest {

    private static String access_key_id = "AKIA334PV4R4ORBPQAKT";
    private static String secret_key_id = "g+fvA9VD92H2BTHjX1aCEYGVFzgWkLOVxOm/esEZ";
    private static String bucketName = "gterry-common";
    private static AmazonS3 s3Client;
    static {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(access_key_id, secret_key_id);
        s3Client = AmazonS3Client.builder()
                .withRegion("ca-central-1")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public static String upload(File tempFile, String remoteFileName) {

        String bucketPath = bucketName + "/upload" ;
        s3Client.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
        URL url = s3Client.generatePresignedUrl(urlRequest);
        return url.toString();

    }
    public static String upload(InputStream tempFile, String contentType, long contentLength, String remoteFileName, String bucketName) {

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(contentLength);

            //Create stored path in S3 for example: /bucketName/20201119
            String folderName=new SimpleDateFormat("yyyyMMdd").format(new Date());
            String newFileName = folderName + "/" +remoteFileName;
            //send file to s3
            s3Client.putObject(new PutObjectRequest(bucketName, newFileName, tempFile,metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
            URL url = s3Client.generatePresignedUrl(urlRequest);

            return url.toString();
        } catch (SdkClientException e) {
            return "error";
        }
    }
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("E:\\HELLO.txt");
        String uploadKey = "hello";
//        upload(uploadFile,uploadKey);

        InputStream is = new FileInputStream(file);
        upload(is,"text/plain",file.length(),"hellolleh22","gterry-common");

    }

}
