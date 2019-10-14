package com.cdsen.power.server.oss;

import com.cdsen.power.core.CommonError;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.oss.OssClient;
import com.cdsen.power.core.oss.OssClientManager;
import com.cdsen.power.server.oss.model.cons.OssError;
import com.cdsen.power.server.oss.model.cons.RedisKey;
import com.cdsen.power.server.oss.service.OssService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private final OssService ossService;

    public OssController(StringRedisTemplate redisTemplate, OssService ossService) {
        this.redisTemplate = redisTemplate;
        this.ossService = ossService;
    }

    @GetMapping("/{objectName}")
    public String test(@PathVariable String objectName) throws FileNotFoundException {
        OssClient client = OssClientManager.getClient("", "");
        client.upload("timg.jpg", new FileInputStream(new File("D:\\TSBrowserDownloads\\20130311231714.jpg")));
        String url = client.generatePreSignedUrl(60, TimeUnit.SECONDS, objectName);
        client.shutdown();
        return url;
    }

    @PostMapping("/image")
    public JsonResult image(MultipartFile image) {
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
