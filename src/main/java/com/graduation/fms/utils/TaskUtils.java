package com.graduation.fms.utils;

import com.graduation.fms.dao.Money;
import com.graduation.fms.mapper.MoneyMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class TaskUtils {

    @Resource
    private MoneyMapper moneyMapper;

    //3.添加定时任务
    //@Scheduled(cron = "0/15 * * * * ?")
    //@Scheduled(cron = "0 0 0 1/1 * ? ") //每天凌晨0点0分0秒执行一次
    private void configureTasks() throws Exception {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        changeMoney();
    }

    public void changeMoney() throws Exception {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        Date day = dft.parse(dft.format(date.getTime()));
        String preDay = dft.format(day);
        preDay = "'"+preDay+"'";
        List<Map<String, Object>> moneyAndUD = moneyMapper.getJJMoneyAndUD(preDay);
        for (Map<String, Object> map : moneyAndUD) {
            Money money = new Money();
            money.setMoney(new BigDecimal(map.get("money").toString()));
            money.setMoney(money.getMoney().multiply(new BigDecimal("1.00").
                    add(new BigDecimal(map.get("ud").toString()).divide(new BigDecimal("100")))));
            money.setMoneyId((Integer) map.get("moneyId"));
            moneyMapper.updateById(money);
        }
    }

}
