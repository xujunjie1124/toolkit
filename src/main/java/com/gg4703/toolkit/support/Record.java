package com.gg4703.toolkit.support;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Record {

    /**
     * 交易类型
     */
    private String transactionType;
    /**
     * 交易日期
     */
    private Date date;
    /**
     * 分类
     */
    private String category;
    /**
     * 子分类
     */
    private String subCategory;
    /**
     * 账户类型
     */
    private String accountType;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 成员
     */
    private String member;
    /**
     * 备注
     */
    private String remark;
}
