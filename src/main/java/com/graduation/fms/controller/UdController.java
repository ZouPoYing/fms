package com.graduation.fms.controller;


import com.graduation.fms.dao.Fund;
import com.graduation.fms.dao.Ud;
import com.graduation.fms.mapper.FundMapper;
import com.graduation.fms.mapper.UdMapper;
import com.graduation.fms.utils.DateUtils;
import com.graduation.fms.utils.ListUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MyBatisPlusAutoGenerator
 * @since 2021-04-30
 */
@RestController
@RequestMapping("/fms/ud")
@CrossOrigin(origins = "*",allowCredentials ="true")
public class UdController {

    @Resource
    private UdMapper udMapper;

    @Resource
    private FundMapper fundMapper;

    @RequestMapping("/getSelectDay")
    public List<String> getSelectDay(@RequestBody Map<String, String> params) throws Exception {
        String fundId = params.get("fundId");
        if (fundId.isEmpty()) {
            return null;
        }
        Fund fund = fundMapper.selectById(fundId);
        List<Date> listD2Y = DateUtils.getListD2Y(fund.getCreateTime());
        List<Date> udDayList = udMapper.getUdDayListByFundID(Integer.valueOf(fundId));
        List<Date> list = ListUtils.listrem(listD2Y,udDayList);
        DateUtils.listSort(list,0,ListUtils.listrem(listD2Y,udDayList).size()-1);
        return DateUtils.DL2SLNYR(list);
    }

    @RequestMapping("/setUd")
    public Map<String, Object> setUd(@RequestBody Map<String, String> params) throws Exception {
        String fundId = params.get("fundId");
        String day = params.get("day");
        String ud = params.get("ud");
        Map<String, Object> result = new HashMap<>();
        if (fundId.isEmpty() || day.isEmpty() || ud.isEmpty()) {
            result.put("msg", "参数不能为空");
            return null;
        }
        Ud ud1 = new Ud();
        ud1.setFundId(Integer.valueOf(fundId));
        ud1.setDay(DateUtils.S2D(day));
        ud1.setUd(new BigDecimal(ud));
        udMapper.insert(ud1);
        result.put("success", true);
        return result;
    }
}

