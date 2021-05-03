package com.graduation.fms.mapper;

import com.graduation.fms.dao.Money;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MyBatisPlusAutoGenerator
 * @since 2021-04-27
 */
public interface MoneyMapper extends BaseMapper<Money> {

    @Select("SELECT fund.fund_id as fundId,money.money as money,money.create_time as date,fund.fund_name as fundName\n" +
            "FROM money LEFT JOIN fund ON money.fund_id=fund.fund_id where money.user_id=#{userId} and money.money_type='基金'")
    @Results({
            @Result(property = "fundId", column = "fundId"),
            @Result(property = "money", column = "money"),
            @Result(property = "date", column = "date"),
            @Result(property = "fundName", column = "fundName")})
    List<Map<String, Object>> getMyJJ(@Param("userId") Integer userId);
}
