package com.cdsen.power.core;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cdsen.interfaces.config.service.BusinessSettingApiService;
import com.cdsen.interfaces.config.vo.BusinessSetting;
import com.cdsen.power.core.util.JsonUtils;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author HuSen
 * create on 2019/10/24 12:26
 */
@Data
@Slf4j
public class BusinessConfig implements InitializingBean {

    @ApolloJsonValue("${consumptionTypes:[]}")
    private List<BusinessSetting> consumptionTypes;

    @ApolloJsonValue("${incomeChannels:[]}")
    private List<BusinessSetting> incomeChannels;

    private final BusinessSettingApiService businessSettingApiService;

    public BusinessConfig(BusinessSettingApiService businessSettingApiService) {
        this.businessSettingApiService = businessSettingApiService;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notEmpty(this.consumptionTypes, "");
        Assert.notEmpty(this.incomeChannels, "");
        log.info("ヾ(Ő∀Ő๑)ﾉ太好惹 加载到消费类型配置:{}", this.consumptionTypes);
        log.info("ヾ(Ő∀Ő๑)ﾉ太好惹 加载到收入渠道配置:{}", this.incomeChannels);
    }

    @ApolloConfigChangeListener(interestedKeys = {"consumptionTypes", "incomeChannels"})
    private void someOnChange(ConfigChangeEvent changeEvent) {
        Set<String> changedKeys = changeEvent.changedKeys();
        Map<String, List<BusinessSetting>> changedMap = new HashMap<>(changedKeys.size());

        for (String changedKey : changedKeys) {
            switch (changedKey) {
                case "consumptionTypes": {
                    ConfigChange change = changeEvent.getChange(changedKey);
                    String newValue = change.getNewValue();
                    log.info("ヾ(o◕∀◕)ﾉヾ 消费类型配置发生了变化:{}", newValue);
                    changedMap.put(changedKey, JsonUtils.parseArr(newValue, BusinessSetting.class));
                    break;
                }
                case "incomeChannels": {
                    ConfigChange change = changeEvent.getChange(changedKey);
                    String newValue = change.getNewValue();
                    log.info("ヾ(o◕∀◕)ﾉヾ 收入渠道配置发生了变化:{}", newValue);
                    changedMap.put(changedKey, JsonUtils.parseArr(newValue, BusinessSetting.class));
                    break;
                }
                default:
            }
        }
        if (!CollectionUtils.isEmpty(changedMap)) {
            log.info("ヾ(o◕∀◕)ﾉヾ 配置发生了改变，进行推送");
            businessSettingApiService.push(changedMap);
        }
    }

    public Map<String, List<BusinessSetting>> getAll() {
        Map<String, List<BusinessSetting>> all = new HashMap<>(2);
        all.put("consumptionTypes", this.consumptionTypes);
        all.put("incomeChannels", this.incomeChannels);
        return all;
    }
}
