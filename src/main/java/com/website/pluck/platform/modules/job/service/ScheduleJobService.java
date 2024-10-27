package com.website.pluck.platform.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.website.pluck.platform.common.utils.PageUtils;
import com.website.pluck.platform.modules.job.model.ScheduleJob;

import java.util.Map;

public interface ScheduleJobService extends IService<ScheduleJob> {
    PageUtils queryPage(Map<String, Object> params);

    void saveJob(ScheduleJob scheduleJob);

    void update(ScheduleJob scheduleJob);

    void deleteBatch(Long[] jobIds);

    int updateBatch(Long[] jobIds, int status);

    void run(Long[] jobIds);

    void pause(Long[] jobIds);

    void resume(Long[] jobIds);
}