package com.cdsen.power.core.oss;

import lombok.Data;

/**
 * @author HuSen
 * create on 2019/10/14 11:06
 */
@Data
public class OssProperties {
    private String useFor;
    private String endpoint;
    private String bucketName;
    private String accessKeyId;
    private String accessKeySecret;
}
