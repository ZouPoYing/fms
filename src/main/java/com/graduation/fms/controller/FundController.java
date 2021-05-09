package com.graduation.fms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.fms.dao.Fund;
import com.graduation.fms.dao.Money;
import com.graduation.fms.dao.MoneyDetail;
import com.graduation.fms.dao.Ud;
import com.graduation.fms.mapper.FundMapper;
import com.graduation.fms.mapper.MoneyDetailMapper;
import com.graduation.fms.mapper.MoneyMapper;
import com.graduation.fms.mapper.UdMapper;
import com.graduation.fms.utils.DateUtils;
import com.graduation.fms.utils.MapUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Resource
    private UdMapper udMapper;

    @Resource
    private MoneyMapper moneyMapper;

    @Resource
    private MoneyDetailMapper moneyDetailMapper;

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
        BigDecimal allZD = new BigDecimal("0.00");
        BigDecimal ZD = new BigDecimal("0.00");
        QueryWrapper<Ud> wrapper = new QueryWrapper<>();
        wrapper.eq("fund_id",fundId);
        List<Ud> uds = udMapper.selectList(wrapper);
        if (!uds.isEmpty()) {
            for (Ud ud : uds) {
                allZD = allZD.add(ud.getUd());
                ZD = ud.getUd();
            }
        }
        result.put("allZD", allZD+"%");
        result.put("ZD", ZD+"%");
        result.put("newFundMoney", ((BigDecimal) result.get("fundMoney")).multiply(allZD.divide(new BigDecimal(100.00),4,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1.00))));
        return result;
    }

    @RequestMapping("/getFundLineByFundId")
    public List<Map<String, Object>> getFundLineByFundId(@RequestBody Map<String, String> params) throws Exception {
        String fundId = params.get("fundId");
        if (fundId == "null" || fundId == null ||fundId.isEmpty()) {
            return null;
        }
        QueryWrapper<Ud> wrapper = new QueryWrapper<>();
        wrapper.eq("fund_id",fundId);
        List<Ud> uds = udMapper.selectList(wrapper);
        List<Map<String, Object>> results = new ArrayList<>();
        BigDecimal aZD = new BigDecimal("0.00");
        if (!uds.isEmpty()) {
            for (Ud ud : uds) {
                Map<String, Object> result = new HashMap<>();
                result.put("date",DateUtils.D2S(ud.getDay()).substring(2,10));
                aZD = aZD.add(ud.getUd());
                result.put("aZD",aZD);
                results.add(result);
            }
        }
        return results;
    }

    @RequestMapping("/sellOrBuy")
    public Map<String, Object> sellOrBuy(@RequestBody Map<String, String> params) throws Exception {
        String fundId = params.get("fundId");
        String userId = params.get("userId");
        String title = params.get("title");
        String type = params.get("type");
        String money = params.get("money");
        Map<String, Object> result = new HashMap<>();
        if (fundId.isEmpty() || userId.isEmpty() || title.isEmpty() || type.isEmpty() || money.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        if (title.equals("买入")) {
            QueryWrapper<Money> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId).eq("money_type", type);
            Money money1 = moneyMapper.selectOne(wrapper);
            if (money1==null || money1.getMoney().compareTo(new BigDecimal(money))==-1) {
                result.put("msg", "未绑定"+type+"或"+type+"余额不足");
                return result;
            }
            QueryWrapper<Money> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id",userId).eq("money_type", "基金").eq("fund_id", fundId);
            Money money3 = moneyMapper.selectOne(wrapper1);
            if (money3 == null) {
                Money money2 = new Money();
                money2.setMoney(new BigDecimal(money));
                money2.setUserId(Integer.valueOf(userId));
                money2.setFundId(Integer.valueOf(fundId));
                money2.setMoneyType("基金");
                moneyMapper.insert(money2);
            } else {
                money3.setMoney(money3.getMoney().add(new BigDecimal(money)));
                moneyMapper.updateById(money3);
            }
            money1.setMoney(money1.getMoney().subtract(new BigDecimal(money)));
            moneyMapper.updateById(money1);
            MoneyDetail moneyDetail = new MoneyDetail();
            moneyDetail.setMoneyId(money1.getMoneyId());
            moneyDetail.setType("支出");
            moneyDetail.setMoney(new BigDecimal(money));
            moneyDetail.setToFor("买基金");
            moneyDetailMapper.insert(moneyDetail);
        } else {
            QueryWrapper<Money> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId).eq("money_type", type);
            Money money1 = moneyMapper.selectOne(wrapper);
            if (money1==null) {
                result.put("msg", "未绑定"+type);
                return result; 
            }
            QueryWrapper<Money> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id",userId).eq("money_type", "基金").eq("fund_id", fundId);
            Money money3 = moneyMapper.selectOne(wrapper1);
            if (money3 == null || money3.getMoney().compareTo(new BigDecimal(money))==-1) {
                result.put("msg", "你没买该基金或者你的基金里的钱不够"+money);
                return result;
            }
            if (money3.getMoney().compareTo(new BigDecimal(money))==0){
                moneyMapper.deleteById(money3.getMoneyId());
                money1.setMoney(money1.getMoney().add(new BigDecimal(money)));
                moneyMapper.updateById(money1);
                MoneyDetail moneyDetail = new MoneyDetail();
                moneyDetail.setMoneyId(money1.getMoneyId());
                moneyDetail.setType("收入");
                moneyDetail.setMoney(new BigDecimal(money));
                moneyDetail.setToFor("卖基金");
                moneyDetailMapper.insert(moneyDetail);
            } else {
                money3.setMoney(money3.getMoney().subtract(new BigDecimal(money)));
                moneyMapper.updateById(money3);
                money1.setMoney(money1.getMoney().add(new BigDecimal(money)));
                moneyMapper.updateById(money1);
                MoneyDetail moneyDetail = new MoneyDetail();
                moneyDetail.setMoneyId(money1.getMoneyId());
                moneyDetail.setType("收入");
                moneyDetail.setMoney(new BigDecimal(money));
                moneyDetail.setToFor("卖基金");
                moneyDetailMapper.insert(moneyDetail);
            }
        }
        result.put("success", true);
        return result;
    }
}

