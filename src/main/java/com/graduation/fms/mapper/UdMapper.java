package com.graduation.fms.mapper;

import com.graduation.fms.dao.Ud;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MyBatisPlusAutoGenerator
 * @since 2021-04-30
 */
public interface UdMapper extends BaseMapper<Ud> {

    @Select("SELECT DAY FROM UD WHERE FUND_ID=#{fundId}")
    List<Date> getUdDayListByFundID(Integer fundId);

    @Select("SELECT ud FROM UD LEFT JOIN fund ON ud.fund_id=fund.fund_id WHERE fund.FUND_ID=#{fundId} " +
            "and TO_DAYS( NOW( ) ) - TO_DAYS(ud.day) = 1")
    List<Map<String, Object>> getYUdByFundID(Integer fundId);
}
