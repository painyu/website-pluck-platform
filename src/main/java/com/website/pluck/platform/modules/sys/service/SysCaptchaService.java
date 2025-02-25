package com.website.pluck.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.website.pluck.platform.modules.sys.model.SysCaptcha;

import java.awt.image.BufferedImage;

/**
 * 验证码
 */
public interface SysCaptchaService extends IService<SysCaptcha> {

    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     *
     * @param uuid uuid
     * @param code 验证码
     * @return true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}
