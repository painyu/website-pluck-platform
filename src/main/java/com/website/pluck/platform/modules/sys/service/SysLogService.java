package com.website.pluck.platform.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.website.pluck.platform.common.utils.PageUtils;
import com.website.pluck.platform.modules.sys.model.SysLog;

import java.util.Map;


/**
 * 系统日志
 */
public interface SysLogService extends IService<SysLog> {

    PageUtils queryPage(Map<String, Object> params);

}
