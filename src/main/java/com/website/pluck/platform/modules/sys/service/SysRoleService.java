package com.website.pluck.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.website.pluck.platform.common.utils.PageUtils;
import com.website.pluck.platform.modules.sys.model.SysRole;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysRoleService extends IService<SysRole> {

    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRole role);

    void update(SysRole role);

    void deleteBatch(Long[] roleIds);


    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);
}
