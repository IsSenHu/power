package com.cdsen.power.core.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author HuSen
 * create on 2019/10/8 10:38
 */
@Slf4j
public class OssClient {

    private final OSS oss;

    private final OssProperties properties;

    public OssClient(OssProperties properties) {
        this.properties = properties;
        oss = new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKeyId(), properties.getAccessKeySecret());
    }

    public void shutdown() {
        oss.shutdown();
    }

    public void upload(String objectName, byte[] bytes) {
        PutObjectResult putObjectResult = oss.putObject(properties.getBucketName(), objectName, new ByteArrayInputStream(bytes));
    }

    public void upload(String objectName, InputStream fileInputStream) {
        PutObjectResult putObjectResult = oss.putObject(properties.getBucketName(), objectName, fileInputStream);
    }

    public String generatePreSignedUrl(long timeout, TimeUnit timeUnit, String objectName) {
        URL url = oss.generatePresignedUrl(properties.getBucketName(), objectName, new Date(System.currentTimeMillis() + timeUnit.toMillis(timeout)));
        return url.toString();
    }

    public Bucket createBucket(String bucketName) {
        try {
            return oss.createBucket(bucketName);
        } catch (Exception e) {
            log.error("创建存储空间[{}]失败:", bucketName, e);
            return null;
        }
    }
}
