package com.cdsen.power.core.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.PutObjectResult;
import com.cdsen.power.core.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author HuSen
 * create on 2019/10/8 10:38
 */
@Slf4j
@Component
public class OssClient {

    private OSS oss;

    private final AppProperties appProperties;

    public OssClient(AppProperties appProperties) {
        this.appProperties = appProperties;
        Assert.notNull(appProperties.getOss(), "");
        AppProperties.Oss oss = appProperties.getOss();
        Assert.hasText(oss.getAccessKeyId(), "");
        Assert.hasText(oss.getAccessKeySecret(), "");
        Assert.hasText(oss.getBucketName(), "");
    }

    @PostConstruct
    public void init() {
        oss = new OSSClientBuilder().build(appProperties.getOss().getEndpoint(), appProperties.getOss().getAccessKeyId(), appProperties.getOss().getAccessKeySecret());
    }

    @PreDestroy
    public void destroy() {
        oss.shutdown();
    }

    public void upload(String objectName, byte[] bytes) {
        PutObjectResult putObjectResult = oss.putObject(appProperties.getOss().getBucketName(), objectName, new ByteArrayInputStream(bytes));
    }

    public void upload(String objectName, InputStream fileInputStream) {
        PutObjectResult putObjectResult = oss.putObject(appProperties.getOss().getBucketName(), objectName, fileInputStream);
        System.out.println(1);
    }

    public String generatePreSignedUrl(long timeout, TimeUnit timeUnit, String objectName) {
        URL url = oss.generatePresignedUrl(appProperties.getOss().getBucketName(), objectName, new Date(System.currentTimeMillis() + timeUnit.toMillis(timeout)));
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
