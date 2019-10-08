package com.cdsen.power.server;

import com.cdsen.power.core.oss.OssClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author HuSen
 * create on 2019/10/8 11:42
 */
@RestController
@RequestMapping("/api/oss")
public class OssController {

    private final OssClient ossClient;

    public OssController(OssClient ossClient) {
        this.ossClient = ossClient;
    }

    @GetMapping("/{objectName}")
    public String test(@PathVariable String objectName) {
        return ossClient.generatePresignedUrl(60, TimeUnit.SECONDS, objectName);
    }
}
