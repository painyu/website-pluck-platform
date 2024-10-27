package com.website.pluck.platform.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.website.pluck.platform.common.utils.PageUtils;
import com.website.pluck.platform.common.utils.Query;
import com.website.pluck.platform.modules.job.mapper.ScheduleJobLogMapper;
import com.website.pluck.platform.modules.job.model.ScheduleJobLog;
import com.website.pluck.platform.modules.job.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLog> implements ScheduleJobLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String jobId = (String) params.get("jobId");
        IPage<ScheduleJobLog> page = this.page(new Query<ScheduleJobLog>().getPage(params), new QueryWrapper<ScheduleJobLog>().like(StringUtils.isNotBlank(jobId), "job_id", jobId));
        return new PageUtils(page);
    }
}
