package com.graduation.fms.mapper;

import com.graduation.fms.dao.Ud;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

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
}
