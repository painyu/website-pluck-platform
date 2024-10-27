package com.website.pluck.platform.modules.job.controller;

import com.website.pluck.platform.common.utils.PageUtils;
import com.website.pluck.platform.common.utils.R;
import com.website.pluck.platform.modules.job.model.ScheduleJobLog;
import com.website.pluck.platform.modules.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping({"/sys/scheduleLog"})
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    public ScheduleJobLogController() {
    }

    @RequestMapping({"/list"})
    @RequiresPermissions({"sys:schedule:log"})
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = this.scheduleJobLogService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping({"/info/{logId}"})
    public R info(@PathVariable("logId") Long logId) {
        ScheduleJobLog log = this.scheduleJobLogService.getById(logId);
        return R.ok().put("log", log);
    }
}

