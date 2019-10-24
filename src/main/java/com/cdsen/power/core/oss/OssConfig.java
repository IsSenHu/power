package com.cdsen.power.core.oss;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/10/24 11:45
 */
@Slf4j
@Data
public class OssConfig implements InitializingBean {

    @ApolloJsonValue("${ossEndpoints:[]}")
    private List<OssProperties> ossEndpoints;

    @Override
    public void afterPropertiesSet() {
        Assert.notEmpty(this.ossEndpoints, "");
        log.info("ヾ(Ő∀Ő๑)ﾉ太好惹 加载到OSS配置:{}", this.ossEndpoints);
        OssClientManager.init(this.ossEndpoints);
    }

    @ApolloConfigChangeListener(interestedKeys = "ossEndpoints")
    private void someOnChange(ConfigChangeEvent changeEvent) {
        Assert.notEmpty(this.ossEndpoints, "");
        log.info("ヾ(o◕∀◕)ﾉヾ Oss配置发生了变化:{}", this.ossEndpoints);
        OssClientManager.refresh(this.ossEndpoints);
    }
}
