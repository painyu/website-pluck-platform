package com.website.pluck.platform.modules.job.utils;

import com.website.pluck.platform.common.utils.SpringContextUtils;
import com.website.pluck.platform.modules.job.model.ScheduleJob;
import com.website.pluck.platform.modules.job.model.ScheduleJobLog;
import com.website.pluck.platform.modules.job.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.lang.reflect.Method;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleJobUtils extends QuartzJobBean {

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("JOB_PARAM_KEY");
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");
        ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
        scheduleJobLog.setJobId(scheduleJob.getJobId());
        scheduleJobLog.setBeanName(scheduleJob.getBeanName());
        scheduleJobLog.setParams(scheduleJob.getParams());
        scheduleJobLog.setCreateTime(new Date());
        long startTime = System.currentTimeMillis();
        try {
            log.debug("任务准备执行，任务ID：" + scheduleJob.getJobId());
            Object target = SpringContextUtils.getBean(scheduleJob.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, scheduleJob.getParams());
            long times = System.currentTimeMillis() - startTime;
            scheduleJobLog.setTimes((int) times);
            scheduleJobLog.setStatus(0);
            log.debug("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception var14) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), var14);
            long times = System.currentTimeMillis() - startTime;
            scheduleJobLog.setTimes((int) times);
            scheduleJobLog.setStatus(1);
            scheduleJobLog.setError(StringUtils.substring(var14.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.save(scheduleJobLog);
        }

    }
}