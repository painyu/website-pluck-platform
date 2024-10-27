package com.website.pluck.platform.modules.sys.redis;


import com.website.pluck.platform.common.utils.RedisKeys;
import com.website.pluck.platform.common.utils.RedisUtils;
import com.website.pluck.platform.modules.sys.model.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统配置Redis
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class SysConfigRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysConfig config) {
        if (config == null) {
            return;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public SysConfig get(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfig.class);
    }
}
