package com.website.pluck.platform.modules.job.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.website.pluck.platform.common.utils.HttpClientUtil;
import com.website.pluck.platform.modules.google.mapper.SpiderWebsiteMapper;
import com.website.pluck.platform.modules.google.model.SpiderWebsite;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component("updatePhoneJobHandler")
public class UpdatePhoneJobHandlerImpl implements ITask {
    @Resource
    private SpiderWebsiteMapper spiderWebsiteMapper;

    public void run(String params) {
        log.info("--------打印日志---- updatePhoneJobHandler :{}", params);
        AtomicBoolean flag = new AtomicBoolean(true);
        int pageNum = 0;
        for (int pageSize = 50; flag.get(); pageNum = pageNum + 1) {
            Page<SpiderWebsite> spiderWebsiteList = spiderWebsiteMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<SpiderWebsite>lambdaQuery().eq(SpiderWebsite::getContactPerson, "").ne(SpiderWebsite::getCompanyUrl, "").orderByAsc(SpiderWebsite::getCreateTime));
            if (CollectionUtils.isEmpty(spiderWebsiteList.getRecords())) {
                flag.set(false);
            }
            spiderWebsiteList.getRecords().forEach((spiderWebsite) -> {
                String response = HttpClientUtil.get("https://www.europages.cn/ep-api/v2/epages/" + spiderWebsite.getCompanyId() + "/phones", null, MapUtils.EMPTY_MAP, null, false);
                JSONObject jsonObject = JSON.parseObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("phones");
                JSONObject resultx;
                if (Objects.isNull(jsonArray)) {
                    resultx = new JSONObject();
                    resultx.put("phone", "");
                    resultx.put("landline", "");
                    this.spiderWebsiteMapper.updateById(SpiderWebsite.builder().id(spiderWebsite.getId()).contactPerson(JSON.toJSONString(resultx)).build());
                } else {
                    resultx = jsonArray.getJSONObject(0);
                    JSONArray items = resultx.getJSONArray("items");
                    JSONObject result = new JSONObject();

                    for (int i = 0; i < items.size(); ++i) {
                        JSONObject phoneOne = items.getJSONObject(i);
                        if (phoneOne.getInteger("type") == 1) {
                            result.put("phone", phoneOne.getString("number"));
                        } else {
                            result.put("landline", phoneOne.getString("number"));
                        }
                    }
                    this.spiderWebsiteMapper.updateById(SpiderWebsite.builder().id(spiderWebsite.getId()).contactPerson(JSON.toJSONString(result)).build());
                    log.info("=========updatePhoneJobHandler==== 更新手机号码 id : {}", spiderWebsite.getId());
                }
            });
        }

    }
}
