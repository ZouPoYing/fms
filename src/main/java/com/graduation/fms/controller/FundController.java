package com.graduation.fms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.fms.dao.Fund;
import com.graduation.fms.dao.Money;
import com.graduation.fms.mapper.FundMapper;
import com.graduation.fms.utils.DateUtils;
import com.graduation.fms.utils.MapUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MyBatisPlusAutoGenerator
 * @since 2021-04-27
 */
@RestController
@RequestMapping("/fms/fund")
@CrossOrigin(origins = "*",allowCredentials ="true")
public class FundController {

    @Resource
    private FundMapper fundMapper;

    @RequestMapping("/addFund")
    public Map<String, Object> addFund(@RequestBody Map<String, String> params) throws Exception {
        String userId = params.get("userId");
        String fundName = params.get("fundName");
        String fundRiskLevel = params.get("fundRiskLevel");
        String earning = params.get("earning");
        String antiRisk = params.get("antiRisk");
        String costPerformance = params.get("costPerformance");
        String fundMoney = params.get("fundMoney");
        String fundCompany = params.get("fundCompany");
        String fundManager = params.get("fundManager");
        String isPush = params.get("isPush");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty() || fundName.isEmpty() || fundRiskLevel.isEmpty() || earning.isEmpty() ||
                antiRisk.isEmpty() || costPerformance.isEmpty() || fundMoney.isEmpty() || fundCompany.isEmpty()
                || fundManager.isEmpty() || isPush.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        Fund fund = new Fund();
        fund.setFundName(fundName);
        fund.setFundRiskLevel(fundRiskLevel);
        fund.setEarning(earning);
        fund.setAntiRisk(antiRisk);
        fund.setCostPerformance(costPerformance);
        fund.setFundMoney(new BigDecimal(fundMoney));
        fund.setFundCompany(fundCompany);
        fund.setFundManager(fundManager);
        fund.setIsPush(isPush);
        fundMapper.insert(fund);
        result.put("success", true);
        return result;
    }

    @RequestMapping("/getFund")
    public List<Fund> getFund() {
        return fundMapper.selectList(null);
    }

    @RequestMapping("/updateFund")
    public Map<String, Object> updateFund(@RequestBody Map<String, String> params) throws Exception {
        String userId = params.get("userId");
        String fundId = params.get("fundId");
        String fundName = params.get("fundName");
        String fundRiskLevel = params.get("fundRiskLevel");
        String earning = params.get("earning");
        String antiRisk = params.get("antiRisk");
        String costPerformance = params.get("costPerformance");
        String fundMoney = params.get("fundMoney");
        String fundCompany = params.get("fundCompany");
        String fundManager = params.get("fundManager");
        String isPush = params.get("isPush");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty() || fundName.isEmpty() || fundRiskLevel.isEmpty() || earning.isEmpty() ||
                antiRisk.isEmpty() || costPerformance.isEmpty() || fundMoney.isEmpty() || fundCompany.isEmpty()
                || fundManager.isEmpty() || isPush.isEmpty() || fundId.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        Fund fund = new Fund();
        fund.setFundId(Integer.valueOf(fundId));
        fund.setFundName(fundName);
        fund.setFundRiskLevel(fundRiskLevel);
        fund.setEarning(earning);
        fund.setAntiRisk(antiRisk);
        fund.setCostPerformance(costPerformance);
        fund.setFundMoney(new BigDecimal(fundMoney));
        fund.setFundCompany(fundCompany);
        fund.setFundManager(fundManager);
        fund.setIsPush(isPush);
        fundMapper.updateById(fund);
        result.put("success", true);
        return result;
    }

    @RequestMapping("/getFundPH")
    public List<Fund> getFundPH(@RequestBody Map<String, String> params) {
        String type = params.get("type");
        if (type.isEmpty()) {
            return null;
        }
        QueryWrapper<Fund> wrapper = new QueryWrapper<>();
        if (type.equals("基金规模")) {
            wrapper
                    .eq("is_push","是")
                    .orderByDesc("fund_money");
            return fundMapper.selectList(wrapper);
        } else if (type.equals("收益能力")) {
            wrapper
                    .eq("is_push","是")
                    .orderByDesc("earning");
            return fundMapper.selectList(wrapper);
        } else if (type.equals("抗风险波动")) {
            wrapper
                    .eq("is_push","是")
                    .orderByAsc("anti_risk");
            return fundMapper.selectList(wrapper);
        } else {
            wrapper
                    .eq("is_push","是")
                    .orderByDesc("cost_performance");
            return fundMapper.selectList(wrapper);
        }
    }

    @RequestMapping("/getFundSS")
    public List<Fund> getFundSS(@RequestBody Map<String, String> params) {
        String key = params.get("key");
        if (key.isEmpty()) {
            QueryWrapper<Fund> wrapper = new QueryWrapper<>();
            wrapper
                    .eq("is_push","是");
            return fundMapper.selectList(wrapper);
        }
        QueryWrapper<Fund> wrapper = new QueryWrapper<>();
        wrapper
                .eq("is_push","是")
                .like("fund_id", key).or()
                .like("fund_name", key).or()
                .like("fund_manager", key).or()
                .like("fund_company", key);
        return fundMapper.selectList(wrapper);
    }

    @RequestMapping("/getFundByFundId")
    public Map<String, Object> getFundByFundId(@RequestBody Map<String, String> params) throws Exception {
        String fundId = params.get("fundId");
        Map<String, Object> result = new HashMap<>();
        if (fundId == "null" || fundId == null ||fundId.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        result.put("success", true);
        Fund fund = fundMapper.selectById(Integer.valueOf(fundId));
        result.putAll(MapUtils.java2Map(fund));
        result.put("createTime", DateUtils.D2NYR(fund.getCreateTime()));
        return result;
    }
}

