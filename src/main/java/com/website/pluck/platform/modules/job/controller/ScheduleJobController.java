package com.website.pluck.platform.modules.job.controller;

import com.website.pluck.platform.common.annotation.SysLog;
import com.website.pluck.platform.common.utils.PageUtils;
import com.website.pluck.platform.common.utils.R;
import com.website.pluck.platform.common.validator.ValidatorUtils;
import com.website.pluck.platform.modules.job.model.ScheduleJob;
import com.website.pluck.platform.modules.job.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/sys/schedule"})
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    @RequestMapping({"/list"})
    @RequiresPermissions({"sys:schedule:list"})
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = this.scheduleJobService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping({"/info/{jobId}"})
    @RequiresPermissions({"sys:schedule:info"})
    public R info(@PathVariable("jobId") Long jobId) {
        ScheduleJob schedule = (ScheduleJob) this.scheduleJobService.getById(jobId);
        return R.ok().put("schedule", schedule);
    }

    @SysLog("保存定时任务")
    @RequestMapping({"/save"})
    @RequiresPermissions({"sys:schedule:save"})
    public R save(@RequestBody ScheduleJob scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob, new Class[0]);
        this.scheduleJobService.saveJob(scheduleJob);
        return R.ok();
    }

    @SysLog("修改定时任务")
    @RequestMapping({"/update"})
    @RequiresPermissions({"sys:schedule:update"})
    public R update(@RequestBody ScheduleJob scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob, new Class[0]);
        this.scheduleJobService.update(scheduleJob);
        return R.ok();
    }

    @SysLog("删除定时任务")
    @RequestMapping({"/delete"})
    @RequiresPermissions({"sys:schedule:delete"})
    public R delete(@RequestBody Long[] jobIds) {
        this.scheduleJobService.deleteBatch(jobIds);
        return R.ok();
    }

    @SysLog("立即执行任务")
    @RequestMapping({"/run"})
    @RequiresPermissions({"sys:schedule:run"})
    public R run(@RequestBody Long[] jobIds) {
        this.scheduleJobService.run(jobIds);
        return R.ok();
    }

    @SysLog("暂停定时任务")
    @RequestMapping({"/pause"})
    @RequiresPermissions({"sys:schedule:pause"})
    public R pause(@RequestBody Long[] jobIds) {
        this.scheduleJobService.pause(jobIds);
        return R.ok();
    }

    @SysLog("恢复定时任务")
    @RequestMapping({"/resume"})
    @RequiresPermissions({"sys:schedule:resume"})
    public R resume(@RequestBody Long[] jobIds) {
        this.scheduleJobService.resume(jobIds);
        return R.ok();
    }
}
