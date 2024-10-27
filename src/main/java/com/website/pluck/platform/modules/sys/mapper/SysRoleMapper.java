package com.website.pluck.platform.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.website.pluck.platform.modules.sys.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);
}
