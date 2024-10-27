package com.website.pluck.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.website.pluck.platform.common.utils.PageUtils;
import com.website.pluck.platform.common.utils.Query;
import com.website.pluck.platform.modules.sys.mapper.SysLogMapper;
import com.website.pluck.platform.modules.sys.model.SysLog;
import com.website.pluck.platform.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<SysLog> page = this.page(new Query<SysLog>().getPage(params), new QueryWrapper<SysLog>().like(StringUtils.isNotBlank(key), "username", key));
        return new PageUtils(page);
    }
}
