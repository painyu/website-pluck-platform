package com.website.pluck.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.website.pluck.platform.common.utils.R;
import com.website.pluck.platform.modules.sys.model.SysUserToken;

/**
 * 用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysUserTokenService extends IService<SysUserToken> {

    /**
     * 生成token
     *
     * @param userId 用户ID
     */
    R createToken(long userId);

    /**
     * 退出，修改token值
     *
     * @param userId 用户ID
     */
    void logout(long userId);

}
