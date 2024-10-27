package com.website.pluck.platform.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.website.pluck.platform.modules.sys.model.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
    /**
     * 根据key，查询value
     */
    SysConfig queryByKey(String paramKey);

    /**
     * 根据key，更新value
     */
    int updateValueByKey(@Param("paramKey") String paramKey, @Param("paramValue") String paramValue);
}
