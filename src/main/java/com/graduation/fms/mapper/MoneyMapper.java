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

    @Select("SELECT fund.fund_id as fundId,money.money as money,money.create_time as date," +
            "fund.fund_name as fundName,money.init_money as initMoney\n" +
            "FROM money LEFT JOIN fund ON money.fund_id=fund.fund_id where money.user_id=#{userId} and money.money_type='基金'")
    @Results({
            @Result(property = "fundId", column = "fundId"),
            @Result(property = "money", column = "money"),
            @Result(property = "initMoney", column = "initMoney"),
            @Result(property = "date", column = "date"),
            @Result(property = "fundName", column = "fundName")})
    List<Map<String, Object>> getMyJJ(@Param("userId") Integer userId);

    @Select("select sum(money) as allmoney from money where user_id=#{userId} and money_type='基金' " +
            "and fund_id=#{fundId} GROUP BY fund_id")
    @Results({
            @Result(property = "allmoney", column = "allmoney")})
    List<Map<String, Object>> getMyJJMoneyByFundId(@Param("userId") Integer userId, @Param("fundId") Integer fundId);

    @Select("select money_id as moneyId,money,IFNULL(ud,0) as ud from money LEFT join fund on " +
            "money.fund_id=fund.fund_id left join " +
            "ud on fund.fund_id=ud.fund_id where money_type='基金' and ud.day=str_to_date(${preDay},'%Y-%c-%d')")
    @Results({
            @Result(property = "moneyId", column = "moneyId"),
            @Result(property = "ud", column = "ud"),
            @Result(property = "money", column = "money")})
    List<Map<String, Object>> getJJMoneyAndUD(@Param("preDay") String preDay);
}
