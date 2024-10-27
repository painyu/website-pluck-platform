package com.website.pluck.platform.modules.sys.service;

import com.website.pluck.platform.modules.sys.model.SysUser;
import com.website.pluck.platform.modules.sys.model.SysUserToken;

import java.util.Set;

/**
 * shiro相关接口
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     */
    SysUser queryUser(Long userId);
}
