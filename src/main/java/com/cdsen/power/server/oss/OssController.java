package com.cdsen.power.server.oss;

import com.cdsen.power.core.CommonError;
import com.cdsen.power.core.Error;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.oss.OssClient;
import com.cdsen.power.core.oss.OssClientManager;
import com.cdsen.power.core.oss.OssProperties;
import com.cdsen.power.server.oss.model.cons.OssError;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author HuSen
 * create on 2019/10/8 11:42
 */
@Slf4j
@RestController
@RequestMapping("/api/oss")
public class OssController {

    private static final Set<String> ALLOW_IMAGE_TYPE = Sets.newHashSet("jpg", "png", "bmp", "gif", "webp", "tiff");

    public OssController() {
    }

    /**
     * 获取有超时的文件url
     *
     * @param endpoint   endpoint
     * @param bucketName bucketName
     * @param timeout    超时时间 单位秒
     * @param objectName 文件对象的名称
     * @return url
     */
    @GetMapping("/{endpoint}/{bucketName}/{timeout}/{objectName}")
    public String getTimeoutUrl(@PathVariable String endpoint, @PathVariable String bucketName, @PathVariable Long timeout, @PathVariable String objectName) {
        OssClient client = OssClientManager.getClient(endpoint, bucketName);
        String url = client.generatePreSignedUrl(timeout, TimeUnit.SECONDS, objectName);
        client.shutdown();
        return url;
    }

    /**
     * 上传图片到指定的endpoint bucketName
     *
     * @param endpoint   endpoint
     * @param bucketName bucketName
     * @param image      图片
     * @return 上传结果
     */
    @PostMapping("/image/{endpoint}/{bucketName}")
    public JsonResult image(@PathVariable String endpoint, @PathVariable String bucketName, MultipartFile image) {
        Pair<Error, String> result = upload(OssClientManager.getProperties(endpoint, bucketName), image);
        return result.getFirst().equals(OssError.SUCCESS) ? JsonResult.of(result.getSecond()) : JsonResult.of(result.getFirst());
    }

    /**
     * 为文章模块上传文件
     *
     * @param file   文件
     * @return 文件链接
     */
    @PostMapping("/uploadForArticle")
    public JsonResult<String> uploadForArticle(MultipartFile file) {
        Pair<Error, String> result = upload(OssClientManager.getProperties("ARTICLE"), file);
        return result.getFirst().equals(OssError.SUCCESS) ? JsonResult.of(result.getSecond()) : JsonResult.of(result.getFirst());
    }

    private Pair<Error, String> upload(OssProperties properties, MultipartFile file) {
        try {
            if (file == null) {
                return Pair.of(OssError.IS_NULL, "");
            }
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isBlank(originalFilename)) {
                return Pair.of(OssError.BLANK_FILE_NAME, "");
            }
            String suffix = StringUtils.substringAfter(originalFilename, ".");
            if (!ALLOW_IMAGE_TYPE.contains(suffix)) {
                return Pair.of(OssError.ERROR_TYPE, "");
            }
            originalFilename = UUID.randomUUID().toString().replace("-", "").concat(".").concat(suffix);
            OssClient client = OssClientManager.getClient(properties.getUseFor());
            client.upload(originalFilename, file.getInputStream());
            client.shutdown();
            // https://bucketName.endPoint/originalFilename
            return Pair.of(OssError.SUCCESS, "https://".concat(properties.getBucketName()).concat(".").concat(properties.getEndpoint()).concat("/").concat(originalFilename));
        } catch (Exception e) {
            log.error("上传文件到:{}, 失败:", properties, e);
            return Pair.of(CommonError.INNER_ERROR, "");
        }
    }
}
