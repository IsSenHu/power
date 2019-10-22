package com.cdsen.power.server.oss;

import com.cdsen.power.core.CommonError;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.oss.OssClient;
import com.cdsen.power.core.oss.OssClientManager;
import com.cdsen.power.server.oss.model.cons.ImageType;
import com.cdsen.power.server.oss.model.cons.OssError;
import com.cdsen.power.server.oss.model.cons.RedisKey;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
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

    private final StringRedisTemplate redisTemplate;

    public OssController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/{endpoint}/{bucketName}/{timeout}/{objectName}")
    public String test(@PathVariable String endpoint, @PathVariable String bucketName, @PathVariable Long timeout, @PathVariable String objectName) {
        OssClient client = OssClientManager.getClient(endpoint, bucketName);
        String url = client.generatePreSignedUrl(timeout, TimeUnit.SECONDS, objectName);
        client.shutdown();
        return url;
    }

    /**
     * 上传图片到指定的endpoint bucketName
     *
     * @param type       图片类型 或者说 使用的地方
     * @param endpoint   endpoint
     * @param bucketName bucketName
     * @param image      图片
     * @return 上传结果
     */
    @PostMapping("/image/{type}/{endpoint}/{bucketName}")
    public JsonResult<String> image(@PathVariable ImageType type,  @PathVariable String endpoint, @PathVariable String bucketName, MultipartFile image) {
        try {
            if (image == null) {
                return JsonResult.of(OssError.IS_NULL);
            }
            String originalFilename = image.getOriginalFilename();
            if (StringUtils.isBlank(originalFilename)) {
                return JsonResult.of(OssError.BLANK_FILE_NAME);
            }
            String suffix = StringUtils.substringAfter(originalFilename, ".");
            if (!ALLOW_IMAGE_TYPE.contains(suffix)) {
                return JsonResult.of(OssError.ERROR_TYPE);
            }
            ValueOperations<String, String> value = redisTemplate.opsForValue();
            Long increment = value.increment(RedisKey.FILE_NAME_KEY.concat(originalFilename));
            if (increment == null) {
                return JsonResult.of(CommonError.REDIS_ERROR);
            }
            if (increment > 1) {
                originalFilename = originalFilename.concat("(").concat(String.valueOf(increment - 1)).concat(")");
            }
            OssClient client = OssClientManager.getClient("", "");
            client.upload(originalFilename, image.getInputStream());
            client.shutdown();
            return JsonResult.success();
        } catch (IOException e) {
            log.error("图片上传失败:", e);
            return JsonResult.of(CommonError.INNER_ERROR);
        }
    }
}
