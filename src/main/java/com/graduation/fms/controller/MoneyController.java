package com.graduation.fms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.fms.dao.Money;
import com.graduation.fms.dao.MoneyDetail;
import com.graduation.fms.dao.Ud;
import com.graduation.fms.dao.User;
import com.graduation.fms.mapper.MoneyDetailMapper;
import com.graduation.fms.mapper.MoneyMapper;
import com.graduation.fms.mapper.UdMapper;
import com.graduation.fms.utils.ListUtils;
import com.oracle.webservices.internal.api.message.BaseDistributedPropertySet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/fms/money")
@CrossOrigin(origins = "*",allowCredentials ="true")
public class MoneyController {

    @Resource
    private MoneyMapper moneyMapper;

    @Resource
    private MoneyDetailMapper moneyDetailMapper;

    @Resource
    private UdMapper udMapper;

    @RequestMapping("/getMyMoney")
    public Map<String, Object> getMyMoney(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal allMoney = new BigDecimal("0.00");
        QueryWrapper<Money> wrapper = new QueryWrapper<>();
        wrapper
                .eq("user_id", userId)
                .eq("money_type", "银行卡");
        Money money = moneyMapper.selectOne(wrapper);
        result.put("yhk", money==null?zero:money.getMoney());
        allMoney = allMoney.add(money==null?zero:money.getMoney());
        QueryWrapper<Money> wrapper1 = new QueryWrapper<>();
        wrapper1
                .eq("user_id", userId)
                .eq("money_type", "支付宝");
        Money money1 = moneyMapper.selectOne(wrapper1);
        result.put("zfb", money1==null?zero:money1.getMoney());
        allMoney = allMoney.add(money1==null?zero:money1.getMoney());
        QueryWrapper<Money> wrapper2 = new QueryWrapper<>();
        wrapper2
                .eq("user_id", userId)
                .eq("money_type", "微信");
        Money money2 = moneyMapper.selectOne(wrapper2);
        result.put("wx", money2==null?zero:money2.getMoney());
        allMoney = allMoney.add(money2==null?zero:money2.getMoney());
        QueryWrapper<Money> wrapper3 = new QueryWrapper<>();
        wrapper3
                .eq("user_id", userId)
                .eq("money_type", "基金");
        List<Money> moneyList = moneyMapper.selectList(wrapper3);
        BigDecimal bigDecimal = new BigDecimal("0.00");
        for (Money map:moneyList) {
            bigDecimal = bigDecimal.add(map==null?zero:map.getMoney());
        }
        result.put("jj", bigDecimal);
        allMoney = allMoney.add(bigDecimal);
        result.put("money", allMoney);
        result.put("success", true);
        return result;
    }

    @RequestMapping("/getMyMoneyDetail")
    public Map<String, Object> getMyMoneyDetail(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String moneyType = params.get("moneyType");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty() || moneyType.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        BigDecimal zero = new BigDecimal("0.00");
        Money money = null;
        QueryWrapper<Money> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (moneyType.equals("银行卡")) {
            wrapper.eq("money_type", "银行卡");
            money = moneyMapper.selectOne(wrapper);
            result.put("success", true);
            result.put("money", money==null?zero:money.getMoney());
            result.put("moneyId", money==null?null:money.getMoneyId());
            return result;
        } else if (moneyType.equals("支付宝")) {
            wrapper.eq("money_type", "支付宝");
            money = moneyMapper.selectOne(wrapper);
            result.put("success", true);
            result.put("money", money==null?zero:money.getMoney());
            result.put("moneyId", money==null?null:money.getMoneyId());
            return result;
        } else if (moneyType.equals("微信")) {
            wrapper.eq("money_type", "微信");
            money = moneyMapper.selectOne(wrapper);
            result.put("success", true);
            result.put("money", money==null?zero:money.getMoney());
            result.put("moneyId", money==null?null:money.getMoneyId());
            return result;
        } else if (moneyType.equals("基金")) {
            wrapper.eq("money_type", "基金");
            List<Money> moneyList = moneyMapper.selectList(wrapper);
            BigDecimal bigDecimal = new BigDecimal("0.00");
            for (Money map:moneyList) {
                bigDecimal = bigDecimal.add(money==null?zero:money.getMoney());
            }
            result.put("success", true);
            result.put("money", bigDecimal);
            result.put("moneyId", null);
            return result;
        } else {
            result.put("msg", "金额类型错误");
            return result;
        }
    }

    @RequestMapping("/addMyMoneyDetail")
    public Map<String, Object> addMyMoneyDetail(@RequestBody Map<String, String> params) {
        String moneyId = params.get("moneyId");
        String userId = params.get("userId");
        String moneyType = params.get("moneyType");
        String type = params.get("type");
        String addmoney = params.get("addmoney");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty() || moneyType.isEmpty() || type.isEmpty() || addmoney.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        Money money = new Money();
        money.setUserId(Integer.valueOf(userId));
        money.setMoney(new BigDecimal(addmoney));
        money.setMoneyType(moneyType);
        if (type.equals("add")) {
            QueryWrapper<Money> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            if (moneyType.equals("银行卡")) {
                wrapper.eq("money_type", "银行卡");
                if (moneyMapper.selectOne(wrapper)!=null) {
                    result.put("msg", "已绑定过银行卡");
                    return result;
                }
            } else if (moneyType.equals("支付宝")) {
                wrapper.eq("money_type", "支付宝");
                if (moneyMapper.selectOne(wrapper)!=null) {
                    result.put("msg", "已绑定过支付宝");
                    return result;
                }
            } else if (moneyType.equals("微信")) {
                wrapper.eq("money_type", "微信");
                if (moneyMapper.selectOne(wrapper)!=null) {
                    result.put("msg", "已绑定过微信");
                    return result;
                }
            } else {
                result.put("msg", "金额类型错误");
                return result;
            }
            moneyMapper.insert(money);
        } else if (type.equals("update")) {
            if (moneyId.isEmpty() || moneyId=="null") {
                result.put("msg", "参数不能为空");
                return result;
            }
            money.setMoneyId(Integer.valueOf(moneyId));
            Money money1 = moneyMapper.selectById(Integer.valueOf(moneyId));
            money.setMoney(money.getMoney().add(money1.getMoney()));
            moneyMapper.updateById(money);
        } else if (type.equals("recharge")) {
            if (moneyId.isEmpty() || moneyId=="null") {
                result.put("msg", "参数不能为空");
                return result;
            }
            QueryWrapper<Money> wrapper = new QueryWrapper<>();
            wrapper
                    .eq("user_id", userId)
                    .eq("money_type", "银行卡");
            Money yhk = moneyMapper.selectOne(wrapper);
            if (yhk==null || yhk.getMoney().compareTo(new BigDecimal(addmoney))==-1) {
                result.put("msg", "未绑定银行卡或银行卡金额不足");
                return result;
            }
            yhk.setMoney(yhk.getMoney().subtract(new BigDecimal(addmoney)));
            moneyMapper.updateById(yhk);
            MoneyDetail moneyDetail = new MoneyDetail();
            moneyDetail.setMoneyId(yhk.getMoneyId());
            moneyDetail.setMoney(new BigDecimal(addmoney));
            moneyDetail.setType("支出");
            moneyDetail.setToFor(type);
            moneyDetailMapper.insert(moneyDetail);
            QueryWrapper<Money> wrapper1 = new QueryWrapper<>();
            wrapper1
                    .eq("user_id", userId)
                    .eq("money_type", moneyType);
            Money wz = moneyMapper.selectOne(wrapper1);
            wz.setMoney(wz.getMoney().add(new BigDecimal(addmoney)));
            moneyMapper.updateById(wz);
            moneyDetail.setMoneyId(wz.getMoneyId());
            moneyDetail.setType("收入");
            moneyDetail.setToFor("银行卡");
            moneyDetailMapper.insert(moneyDetail);
            result.put("success", true);
            return result;
        } else if (type.equals("withdraw")) {
            if (moneyId.isEmpty() || moneyId=="null") {
                result.put("msg", "参数不能为空");
                return result;
            }
            QueryWrapper<Money> wrapper = new QueryWrapper<>();
            QueryWrapper<Money> wrapper1 = new QueryWrapper<>();
            wrapper1
                    .eq("user_id", userId)
                    .eq("money_type", moneyType);
            Money wz = moneyMapper.selectOne(wrapper1);
            if (wz==null || wz.getMoney().compareTo(new BigDecimal(addmoney))==-1) {
                result.put("msg", "未绑定"+moneyType+"或"+moneyType+"金额不足");
                return result;
            }
            wz.setMoney(wz.getMoney().subtract(new BigDecimal(addmoney)));
            moneyMapper.updateById(wz);
            MoneyDetail moneyDetail = new MoneyDetail();
            moneyDetail.setMoneyId(wz.getMoneyId());
            moneyDetail.setMoney(new BigDecimal(addmoney));
            moneyDetail.setType("支出");
            moneyDetail.setToFor("银行卡");
            moneyDetailMapper.insert(moneyDetail);
            wrapper
                    .eq("user_id", userId)
                    .eq("money_type", "银行卡");
            Money yhk = moneyMapper.selectOne(wrapper);
            yhk.setMoney(yhk.getMoney().add(new BigDecimal(addmoney)));
            moneyMapper.updateById(yhk);
            moneyDetail.setMoneyId(yhk.getMoneyId());
            moneyDetail.setType("收入");
            moneyDetail.setToFor(type);
            moneyDetailMapper.insert(moneyDetail);
            result.put("success", true);
            return result;
        } else {
            result.put("msg", "参数错误");
            return result;
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping("/getMyJJ")
    public List<Map<String, Object>> getMyJJ(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        if (userId.isEmpty()) {
            return null;
        }
        List<Map<String, Object>> moneyList = moneyMapper.getMyJJ(Integer.valueOf(userId));
        for (Map<String, Object> map : moneyList) {
            List<Map<String, Object>> udList = udMapper.getYUdByFundID((Integer) map.get("fundId"));
            if (udList.size()==0) {
                map.put("ud", "0.00");
                map.put("y", "0");
            } else {
                BigDecimal ud = new BigDecimal(udList.get(0).get("ud").toString());
                map.put("ud", ud);
                BigDecimal money = new BigDecimal(map.get("money").toString());
                BigDecimal udMoney = money.subtract(money.divide(new BigDecimal("1.00").add(ud.divide(new BigDecimal("100")))));
                map.put("y", udMoney);
            }
            map.put("c", new BigDecimal(map.get("money").toString()).subtract(new BigDecimal(map.get("initMoney").toString())));
        }
        return ListUtils.combineMap(moneyList,"fundId","money");
    }
}

