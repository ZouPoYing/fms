package com.graduation.fms.mapper;

import com.graduation.fms.dao.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MyBatisPlusAutoGenerator
 * @since 2021-04-25
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT COUNT(*) FROM USER WHERE USER_NAME=#{username}")
    Integer hasUsername(String username);

}
