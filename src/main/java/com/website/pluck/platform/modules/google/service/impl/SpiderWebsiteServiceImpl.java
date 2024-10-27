package com.website.pluck.platform.modules.google.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.website.pluck.platform.modules.google.mapper.SpiderWebsiteMapper;
import com.website.pluck.platform.modules.google.model.SpiderWebsite;
import com.website.pluck.platform.modules.google.service.SpiderWebsiteService;
import org.springframework.stereotype.Service;

@Service("spiderWebsiteService")
public class SpiderWebsiteServiceImpl extends ServiceImpl<SpiderWebsiteMapper, SpiderWebsite> implements SpiderWebsiteService {

}
