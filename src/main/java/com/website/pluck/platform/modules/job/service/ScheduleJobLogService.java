package com.website.pluck.platform.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.website.pluck.platform.common.utils.PageUtils;
import com.website.pluck.platform.modules.job.model.ScheduleJobLog;

import java.util.Map;

public interface ScheduleJobLogService extends IService<ScheduleJobLog> {
    PageUtils queryPage(Map<String, Object> params);
}
