package com.website.pluck.platform.modules.job.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("schedule_job_log")
public class ScheduleJobLog implements Serializable {
    @TableId
    private Long logId;
    private Long jobId;
    private String beanName;
    private String params;
    private Integer status;
    private String error;
    private Integer times;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

}
