package com.company.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Copyright: 2016 Joindata. All rights reserved.
 * author：===>>     panic     <===
 * Create Time:   ===>>  2017/6/14 18:24  <===
 * email：panic4java@gmail.com
 * describe:主页面list bean
 * ********************************************************
 */
public class OrderListBean implements Serializable {
    //"订单id")
    public Long orderId;
    //"订单号")
    public String orderNo;
    //"支付类型")
    public Integer payType;
    //"订单类型")
    public Integer orderType;
    //"下单人手机号")
    public String phone;
    //"下单时间")
    public Long createTime;
    //"就餐恩书")
    public String peopleNumber;
    //"订单金额")
    public double totalprice;
    //"订单状态")
    public Integer status;
    //"是否已读")
    public Integer isRead;
    //"就餐号")
    public String eatNumber;
    //"店铺id")
    public String fsShopGUID;
    //"修改标识 第一位修改")
    public String attribute;
    //"桌名")
    public String tableName;
    //"预点类型 1.现点堂吃 2.现点打包 3.预点堂吃 4.预点打包")
    public String eatStyle;
    //"预点吃饭 时间")
    public Long eatTime;

    //"是否需要发票0不需要1需要")
    public Integer isInvoice;
    //"是否打印0未打印1已打印")
    public Integer isPrint;
    //"打印类型0蓝牙1网络 -1")
    public Integer printType;
}
