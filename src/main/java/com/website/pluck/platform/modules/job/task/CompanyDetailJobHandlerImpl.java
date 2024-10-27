package com.website.pluck.platform.modules.job.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("companyDetailJobHandler")
public class CompanyDetailJobHandlerImpl implements ITask {
    private static final Logger log = LoggerFactory.getLogger(CompanyDetailJobHandlerImpl.class);

    public void run(String params) {
        log.info("--------打印日志---- companyDetailJobHandler :{}", params);
    }
}
