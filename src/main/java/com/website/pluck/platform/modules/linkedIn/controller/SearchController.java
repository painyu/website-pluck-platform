package com.website.pluck.platform.modules.linkedIn.controller;

import com.alibaba.fastjson.JSON;
import com.website.pluck.platform.common.utils.HttpClientUtil;
import com.website.pluck.platform.common.utils.R;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping({"/linked/search"})
public class SearchController {
    @Resource
    private MongoTemplate mongoTemplate;

    public SearchController() {
    }

    @GetMapping({"/searchKeyword/{keyword}"})
    public R searchKeyword(@PathVariable("keyword") String keyword) {
        Map<String, String> headers = new HashMap();
        headers.put("Csrf-Token", "ajax:7275486490648609496");
        headers.put("X-RestLi-Protocol-Version", "2.0.0");
        headers.put("cookie", "JSESSIONID=ajax:7275486490648609496;li_at=AQEDAVBArpQAJakoAAABkGfsyA0AAAGROfGRw1YAy5Bb7ueQuCt5D93ZpY4Ye1TuhRhlhLoQknR7MKQKCg2EFPMhtWqWCA6gPSN6ViVpCIlTGGHBe2UY6diQIXIWAID94DdKBKEmUXnU8oFWnq1dJH4q");
        Map<String, String> urlParams = new HashMap();
        urlParams.put("variables", "(start:40,origin:GLOBAL_SEARCH_HEADER,query:(keywords:%E4%BB%A3%E7%90%86%E5%95%86,flagshipSearchIntent:SEARCH_SRP,queryParameters:List((key:primaryResultType,value:List(CONTENT)),(key:resultType,value:List(ALL))),includeFiltersInResponse:false))");
        urlParams.put("queryId", "voyagerSearchDashClusters.37920f17209f22c510dd410658abc540");
        String response = HttpClientUtil.get("https://www.linkedin.com/voyager/api/graphql", urlParams, headers, (String) null, false);
        return R.ok().put("data", JSON.parseObject(response).getJSONObject("data").getJSONObject("searchDashClustersByAll").getJSONArray("elements").getJSONObject(0).getJSONArray("items"));
    }
}
