package com.graduation.fms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.graduation.fms.dao.MoneyDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
 * @since 2021-05-01
 */
public interface MoneyDetailMapper extends BaseMapper<MoneyDetail> {

    @Select("SELECT money.money_id AS moneyId, money.money_type AS moneyType,money_detail.money_detail_id " +
            "AS moneyDetailId,money_detail.type AS type,money_detail.create_time AS date," +
            "money_detail.to_for AS toFor,money_detail.money AS money FROM money " +
            "LEFT JOIN money_detail ON money.money_id=money_detail.money_id WHERE money.user_id=#{userId} " +
            "AND money.money_type=#{moneyType} AND date_format(money_detail.create_time,'%Y-%m')=" +
            "date_format(str_to_date(#{month},'%Y-%m'),'%Y-%m')")
    @Results({
            @Result(property = "moneyId", column = "moneyId"),
            @Result(property = "moneyType", column = "moneyType"),
            @Result(property = "moneyDetailId", column = "moneyDetailId"),
            @Result(property = "type", column = "type"),
            @Result(property = "toFor", column = "toFor"),
            @Result(property = "money", column = "money"),
            @Result(property = "date", column = "date")})
    List<Map<String, Object>> getStatistics(@Param("userId") Integer userId, @Param("moneyType") String moneyType,
                                            @Param("month") String month);

    @Select("SELECT money.money_id AS moneyId, money.money_type AS moneyType,money_detail.money_detail_id " +
            "AS moneyDetailId,money_detail.type AS type,money_detail.create_time AS date," +
            "money_detail.to_for AS toFor,money_detail.money AS money FROM money " +
            "LEFT JOIN money_detail ON money.money_id=money_detail.money_id WHERE money.user_id=#{userId} " +
            "AND date_format(money_detail.create_time,'%Y-%m')=date_format(str_to_date(#{month},'%Y-%m'),'%Y-%m')")
    @Results({
            @Result(property = "moneyId", column = "moneyId"),
            @Result(property = "moneyType", column = "moneyType"),
            @Result(property = "moneyDetailId", column = "moneyDetailId"),
            @Result(property = "type", column = "type"),
            @Result(property = "toFor", column = "toFor"),
            @Result(property = "money", column = "money"),
            @Result(property = "date", column = "date")})
    List<Map<String, Object>> getAllStatistics(@Param("userId") Integer userId, @Param("month") String month);

    @Select("SELECT DATE_FORMAT(lefttable.date,'%Y-%c') as yearMonth,substr(lefttable.date,9) as date,IFNULL(righttable.m,'0') as money\n" +
            "    FROM\n" +
            "            (SELECT ADDDATE(y.first, x.d - 1) as date\n" +
            "    FROM\n" +
            "            (SELECT 1 AS d UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL\n" +
            "                    SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL\n" +
            "                    SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL SELECT 20 UNION ALL SELECT 21 UNION ALL\n" +
            "                    SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL\n" +
            "                    SELECT 29 UNION ALL SELECT 30 UNION ALL SELECT 31) x,\n" +
            "(SELECT CONCAT(#{month},'-01') as FIRST, DAY(LAST_DAY(str_to_date(#{month},'%Y-%c'))) AS last) y\n" +
            "    WHERE x.d <= y.last) as lefttable\n" +
            "    LEFT JOIN\n" +
            "            (SELECT truncate(sum(af2.money),2) as m,DATE_FORMAT(af2.create_time,'%Y-%c-%d') as gptime\n" +
            "    from money_detail af2 where type=#{type} and money_id=#{moneyId} GROUP BY gptime)\n" +
            "    as righttable\n" +
            "    ON\n" +
            "    DATE_FORMAT(lefttable.date,'%Y-%c-%d')=righttable.gptime")
    @Results({
            @Result(property = "yearMoney", column = "yearMoney"),
            @Result(property = "money", column = "money"),
            @Result(property = "date", column = "date")})
    List<Map<String, Object>> getStatisticsDetail(@Param("month") String month, @Param("type") String type,
                                                  @Param("moneyId") String moneyId);

    @Select("SELECT DATE_FORMAT(lefttable.date,'%Y-%c') as yearMonth,substr(lefttable.date,9) as date,IFNULL(righttable.m,'0') as money\n" +
            "    FROM\n" +
            "            (SELECT ADDDATE(y.first, x.d - 1) as date\n" +
            "    FROM\n" +
            "            (SELECT 1 AS d UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL\n" +
            "                    SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL\n" +
            "                    SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19 UNION ALL SELECT 20 UNION ALL SELECT 21 UNION ALL\n" +
            "                    SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL\n" +
            "                    SELECT 29 UNION ALL SELECT 30 UNION ALL SELECT 31) x,\n" +
            "(SELECT CONCAT(#{month},'-01') as FIRST, DAY(LAST_DAY(str_to_date(#{month},'%Y-%c'))) AS last) y\n" +
            "    WHERE x.d <= y.last) as lefttable\n" +
            "    LEFT JOIN\n" +
            "            (SELECT truncate(sum(af2.money),2) as m,DATE_FORMAT(af2.create_time,'%Y-%c-%d') as gptime\n" +
            "    from money_detail af2 LEFT JOIN money ON af2.money_id=money.money_id where type=#{type} " +
            "and money.user_id=#{userId} GROUP BY gptime)\n" +
            "    as righttable\n" +
            "    ON\n" +
            "    DATE_FORMAT(lefttable.date,'%Y-%c-%d')=righttable.gptime")
    @Results({
            @Result(property = "yearMoney", column = "yearMoney"),
            @Result(property = "money", column = "money"),
            @Result(property = "date", column = "date")})
    List<Map<String, Object>> getAllStatisticsDetail(@Param("month") String month, @Param("type") String type,
                                                  @Param("userId") Integer userId);

    @Select("SELECT to_for as name, money as value FROM money_detail WHERE money_id =#{moneyId}  AND type =#{type}  \n" +
            "AND str_to_date(CONCAT(#{month},'-01'),'%Y-%c-%d') <= money_detail.create_time and " +
            "money_detail.create_time < date_add(str_to_date(CONCAT(#{month},'-01'),'%Y-%c-%d'), interval 1 MONTH)" +
            "GROUP BY to_for")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "value", column = "value")})
    List<Map<String, Object>> getStatisticsDetailPie(@Param("month") String month, @Param("type") String type,
                                                     @Param("moneyId") String moneyId);

    @Select("SELECT to_for as name, money_detail.money as value FROM money_detail LEFT JOIN money ON money_detail.money_id=money.money_id " +
            " WHERE money.user_id =#{userId}  AND type =#{type}  \n" +
            "AND str_to_date(CONCAT(#{month},'-01'),'%Y-%c-%d') <= money_detail.create_time and " +
            "money_detail.create_time < date_add(str_to_date(CONCAT(#{month},'-01'),'%Y-%c-%d'), interval 1 MONTH)" +
            "GROUP BY to_for")
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "value", column = "value")})
    List<Map<String, Object>> getAllStatisticsDetailPie(@Param("month") String month, @Param("type") String type,
                                                        @Param("userId") Integer userId);

    @Select("SELECT money_detail.type as type,money_detail.money as money,money_detail.create_time as date\n" +
            ",money.money_type as moneyType,money_detail.to_for as toFor  \n" +
            "FROM money_detail LEFT JOIN money ON money_detail.money_id=money.money_id \n" +
            "WHERE money.user_id =#{userId}  AND type =#{type}\n" +
            "AND str_to_date(CONCAT(#{month},'-01'),'%Y-%c-%d') <= money_detail.create_time and \n" +
            "money_detail.create_time < date_add(str_to_date(CONCAT(#{month},'-01'),'%Y-%c-%d'), interval 1 MONTH)")
    @Results({
            @Result(property = "type", column = "type"),
            @Result(property = "money", column = "money"),
            @Result(property = "date", column = "date"),
            @Result(property = "toFor", column = "toFor"),
            @Result(property = "moneyType", column = "moneyType")})
    List<Map<String, Object>> getAllMoneyDetail(@Param("month") String month, @Param("type") String type,
                                             @Param("userId") Integer userId);

    @Select("SELECT money_detail.type as type,money_detail.money as money,money_detail.create_time as date\n" +
            ",money.money_type as moneyType,money_detail.to_for as toFor \n" +
            "FROM money_detail LEFT JOIN money ON money_detail.money_id=money.money_id \n" +
            "WHERE money.money_id =#{moneyId}  AND type =#{type}\n" +
            "AND str_to_date(CONCAT(#{month},'-01'),'%Y-%c-%d') <= money_detail.create_time and \n" +
            "money_detail.create_time < date_add(str_to_date(CONCAT(#{month},'-01'),'%Y-%c-%d'), interval 1 MONTH)\n")
    @Results({
            @Result(property = "type", column = "type"),
            @Result(property = "money", column = "money"),
            @Result(property = "date", column = "date"),
            @Result(property = "toFor", column = "toFor"),
            @Result(property = "moneyType", column = "moneyType")})
    List<Map<String, Object>> getMoneyDetail(@Param("month") String month, @Param("type") String type,
                                             @Param("moneyId") String moneyId);


}
