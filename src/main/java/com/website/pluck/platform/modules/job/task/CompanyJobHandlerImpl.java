package com.website.pluck.platform.modules.job.task;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.website.pluck.platform.common.utils.HttpClientUtil;
import com.website.pluck.platform.modules.google.model.SpiderWebsite;
import com.website.pluck.platform.modules.google.service.SpiderWebsiteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component("companyJobHandler")
public class CompanyJobHandlerImpl implements ITask {
    @Resource
    private SpiderWebsiteService spiderWebsiteService;

    public void run(String params) {
        log.info("--------打印日志---- companyJobHandler :{}", params);
        boolean flag = true;
        int pageNum = 1;

        while (flag) {
            try {
                String response = HttpClientUtil.get(params + pageNum + "&mode=default", null, MapUtils.EMPTY_MAP, null, false);
                JSONObject jsonObject = JSONObject.parseObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                if (!Objects.isNull(jsonArray) && !jsonArray.isEmpty()) {
                    List<String> companyIdList = new ArrayList<>();
                    jsonArray.forEach((item) -> {
                        companyIdList.add((String) ((JSONObject) item).get("id"));
                    });
                    List<SpiderWebsite> spiderWebsiteList = this.spiderWebsiteService.list(Wrappers.<SpiderWebsite>lambdaQuery().in(SpiderWebsite::getCompanyId, companyIdList));
                    List<String> spiderIdList = Lists.newArrayList();
                    if (CollectionUtils.isNotEmpty(spiderWebsiteList)) {
                        spiderIdList = spiderWebsiteList.stream().map(SpiderWebsite::getCompanyId).collect(Collectors.toList());
                    }
                    List<String> finalSpiderIdList = spiderIdList;
                    List<String> noExitIds = companyIdList.stream().filter((companyIdx) -> !finalSpiderIdList.contains(companyIdx)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(noExitIds)) {
                        List<SpiderWebsite> insertRes = Lists.newArrayList();
                        Iterator var13 = jsonArray.iterator();
                        while (var13.hasNext()) {
                            Object o = var13.next();
                            if (noExitIds.contains(((JSONObject) o).get("id").toString())) {
                                String companyId = ((JSONObject) o).get("id").toString();
                                String companyUrl = ((JSONObject) o).get("url").toString();
                                String companyName = ((JSONObject) o).get("name").toString();
                                String companyAddress = ((JSONObject) o).get("address").toString();
                                insertRes.add(SpiderWebsite.builder().companyId(companyId).companyUrl(companyUrl).companyName(companyName).companyAddress(companyAddress).keywords("").build());
                            }
                        }
                        this.spiderWebsiteService.saveBatch(insertRes);
                    }
                    ++pageNum;
                    log.info("--------pageNum  :{}-----------", pageNum);
                } else {
                    flag = false;
                }
            } catch (Exception var19) {
                log.error("失败  -- pageNum :{}", pageNum, var19);
            }
        }
    }
}
