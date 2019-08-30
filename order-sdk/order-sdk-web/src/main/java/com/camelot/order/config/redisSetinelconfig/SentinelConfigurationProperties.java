package com.camelot.order.config.redisSetinelconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConfigurationProperties(prefix = "spring.redis.sentinel")
public class SentinelConfigurationProperties {
    //跟配置文件中nodes对应
    Set<String> nodes;

    //跟配置文件master中
    String master;

    public Set<String> getNodes() {
        return nodes;
    }

    public void setNodes(Set<String> nodes) {
        this.nodes = nodes;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
