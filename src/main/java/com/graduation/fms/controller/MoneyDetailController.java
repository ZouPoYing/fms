package com.graduation.fms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.fms.dao.Fund;
import com.graduation.fms.dao.Money;
import com.graduation.fms.dao.MoneyDetail;
import com.graduation.fms.mapper.MoneyDetailMapper;
import com.graduation.fms.mapper.MoneyMapper;
import com.graduation.fms.utils.DateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MyBatisPlusAutoGenerator
 * @since 2021-05-01
 */
@RestController
@RequestMapping("/fms/money-detail")
@CrossOrigin(origins = "*",allowCredentials ="true")
public class MoneyDetailController {

    @Resource
    private MoneyDetailMapper moneyDetailMapper;

    @Resource
    private MoneyMapper moneyMapper;

    @RequestMapping("/getStatistics")
    public Map<String, Object> getStatistics(@RequestBody Map<String, String> params) throws Exception {
        String userId = params.get("userId");
        String type = params.get("type");
        String month = params.get("month");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty() || type.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        SimpleDateFormat NY = new SimpleDateFormat("yyyy-MM");
        if (month.isEmpty()) {
            month = NY.format(new Date());
        } else {
            SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sj.parse(month.substring(0,10)));
            cal.add(Calendar.MONTH, 1);
            month = NY.format(cal.getTime());
        }
        List<Map<String, Object>> statistics = null;
        if (type.equals("全部")) {
            statistics = moneyDetailMapper.getAllStatistics(Integer.valueOf(userId), month);
        } else {
            statistics = moneyDetailMapper.getStatistics(Integer.valueOf(userId), type, month);
        }
        result.put("date", month);
        BigDecimal sr = new BigDecimal("0.00");
        BigDecimal zc = new BigDecimal("0.00");
        result.put("moneyId", 0);
        result.put("sr", sr);
        result.put("srnum", 0);
        result.put("zc", zc);
        result.put("zcnum", 0);
        if (!statistics.isEmpty()) {
            for (Map<String, Object> map : statistics) {
                if (map.get("money") == null) {
                    continue;
                }
                if (map.get("type").equals("收入")) {
                    result.put("sr", ((BigDecimal) result.get("sr")).add((BigDecimal) map.get("money")));
                    result.put("srnum",(int) result.get("srnum")+1);
                } else {
                    result.put("zc", ((BigDecimal) result.get("zc")).add((BigDecimal) map.get("money")));
                    result.put("zcnum",(int) result.get("zcnum")+1);
                }
            }
            result.put("moneyId", statistics.get(0).get("moneyId"));
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping("/addMoneyDetail")
    public Map<String, Object> addMoneyDetail(@RequestBody Map<String, String> params) throws Exception {
        String userId = params.get("userId");
        String moneyType = params.get("moneyType");
        String type = params.get("type");
        String toFor = params.get("toFor");
        String day = params.get("day");
        String money = params.get("money");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty() || type.isEmpty() || moneyType.isEmpty() || toFor.isEmpty() || day.isEmpty() || money.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        QueryWrapper<Money> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("money_type",moneyType);
        Money money1 = moneyMapper.selectOne(wrapper);
        if (money1 == null) {
            result.put("msg", "未绑定"+moneyType);
            return result;
        }
        if (type.equals("收入")) {
            money1.setMoney(money1.getMoney().add(new BigDecimal(money)));
            moneyMapper.updateById(money1);
            MoneyDetail moneyDetail = new MoneyDetail();
            moneyDetail.setMoneyId(money1.getMoneyId());
            moneyDetail.setType(type);
            moneyDetail.setMoney(new BigDecimal(money));
            moneyDetail.setCreateTime(DateUtils.S2D(day));
            moneyDetail.setToFor(toFor);
            moneyDetailMapper.insert(moneyDetail);
        } else {
            if (money1.getMoney().compareTo(new BigDecimal(money))==-1) {
                result.put("msg", moneyType+"余额不足");
                return result;
            }
            money1.setMoney(money1.getMoney().subtract(new BigDecimal(money)));
            moneyMapper.updateById(money1);
            MoneyDetail moneyDetail = new MoneyDetail();
            moneyDetail.setMoneyId(money1.getMoneyId());
            moneyDetail.setType(type);
            moneyDetail.setMoney(new BigDecimal(money));
            moneyDetail.setCreateTime(DateUtils.S2D(day));
            moneyDetail.setToFor(toFor);
            moneyDetailMapper.insert(moneyDetail);
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping("/getStatisticsDetail")
    public List<Map<String, Object>> getStatisticsDetail(@RequestBody Map<String, String> params) throws Exception {
        String month = params.get("month");
        String type = params.get("type");
        String isQB = params.get("isQB");
        String userId = params.get("userId");
        String moneyId = params.get("moneyId");
        if (month.isEmpty() || type.isEmpty() || moneyId.isEmpty() || isQB.isEmpty() || userId.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> statisticsDetail = null;
        if (isQB.equals("全部")) {
            statisticsDetail = moneyDetailMapper.getAllStatisticsDetail(month,type,Integer.valueOf(userId));
        } else {
            statisticsDetail = moneyDetailMapper.getStatisticsDetail(month,type,moneyId);
        }
        return statisticsDetail;
    }

    @RequestMapping("/getStatisticsDetailPie")
    public List<Map<String, Object>> getStatisticsDetailPie(@RequestBody Map<String, String> params) throws Exception {
        String month = params.get("month");
        String type = params.get("type");
        String isQB = params.get("isQB");
        String userId = params.get("userId");
        String moneyId = params.get("moneyId");
        if (month.isEmpty() || type.isEmpty() || isQB.isEmpty() || userId.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> statisticsDetailPie = null;
        if (isQB.equals("全部")) {
            statisticsDetailPie = moneyDetailMapper.getAllStatisticsDetailPie(month,type,Integer.valueOf(userId));
        } else {
            statisticsDetailPie = moneyDetailMapper.getStatisticsDetailPie(month,type,moneyId);
        }
        if (statisticsDetailPie.isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("value", 0);
            result.put("name", "无");
            statisticsDetailPie.add(result);
        }
        return statisticsDetailPie;
    }

    @RequestMapping("/getMoneyDetail")
    public List<Map<String, Object>> getMoneyDetail(@RequestBody Map<String, String> params) throws Exception {
        String month = params.get("month");
        String type = params.get("type");
        String isQB = params.get("isQB");
        String userId = params.get("userId");
        String moneyId = params.get("moneyId");
        if (month.isEmpty() || type.isEmpty() || isQB.isEmpty() || userId.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> moneyDetail = null;
        if (isQB.equals("全部")) {
            moneyDetail = moneyDetailMapper.getAllMoneyDetail(month,type,Integer.valueOf(userId));
        } else {
            moneyDetail = moneyDetailMapper.getMoneyDetail(month,type,moneyId);
        }
        for (Map<String, Object> map : moneyDetail) {
            map.put("date", DateUtils.D2NYR(map.get("date")));
        }
        return moneyDetail;
    }

}

