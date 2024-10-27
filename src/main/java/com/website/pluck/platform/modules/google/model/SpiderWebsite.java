package com.website.pluck.platform.modules.google.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpiderWebsite implements Serializable {
    @TableId
    private Long id;
    private String companyId;
    private String companyUrl;
    private String companyName;
    private String companyAddress;
    private String businessScope;
    private String scaleNum;
    private String contactPerson;
    private String website;
    private String companyEstablished;
    private String product;
    private String emails;
    private String keywords;
    private Date createTime;
    private Date updateTime;
}
