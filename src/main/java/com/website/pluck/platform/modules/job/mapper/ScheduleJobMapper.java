package com.website.pluck.platform.modules.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.website.pluck.platform.modules.job.model.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
    int updateBatch(Map<String, Object> map);
}
