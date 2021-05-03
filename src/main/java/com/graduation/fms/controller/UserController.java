package com.graduation.fms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.fms.dao.User;
import com.graduation.fms.mapper.UserMapper;
import com.graduation.fms.utils.DateUtils;
import com.graduation.fms.utils.MapUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MyBatisPlusAutoGenerator
 * @since 2021-04-25
 */
@RestController
@RequestMapping("/fms/user")
@CrossOrigin(origins = "*",allowCredentials ="true")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @RequestMapping("/regist")
    public Map<String, Object> regist(@RequestBody Map<String, String> params) {
        String userName = params.get("userName");
        String password = params.get("password");
        String question = params.get("question");
        String answer = params.get("answer");
        Map<String, Object> result = new HashMap<>();
        if (userName.isEmpty() || password.isEmpty() || question.isEmpty() || answer.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        if (userMapper.selectList(wrapper).size()>0) {
            result.put("msg", "用户名已注册");
            return result;
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setQuestion(question);
        user.setAnswer(answer);
        userMapper.insert(user);
        result.put("success", true);
        return result;
    }

    @RequestMapping("/forget")
    public Map<String, Object> forget(@RequestBody Map<String, String> params) {
        String userName = params.get("userName");
        String password = params.get("password");
        String question = params.get("question");
        String answer = params.get("answer");
        Map<String, Object> result = new HashMap<>();
        if (userName.isEmpty() || password.isEmpty() || question.isEmpty() || answer.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        if (userMapper.selectList(wrapper).size()<=0) {
            result.put("msg", "用户名不存在");
            return result;
        }
        wrapper
                .eq("question", question)
                .eq("answer", answer);
        if (userMapper.selectList(wrapper).size()<=0) {
            result.put("msg", "密保问题或密保答案错误");
            return result;
        }
        User user = new User();
        user.setPassword(password);
        userMapper.update(user, wrapper);
        result.put("success", true);
        return result;
    }

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> params) {
        String userName = params.get("userName");
        String password = params.get("password");
        Map<String, Object> result = new HashMap<>();
        if (userName.isEmpty() || password.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("user_name", userName)
                .eq("password", password);
        if (userMapper.selectList(wrapper).size()<=0) {
            result.put("msg", "用户名或密码错误");
            return result;
        }
        result.put("success", true);
        result.put("userId", userMapper.selectList(wrapper).get(0).getUserId());
        return result;
    }

    @RequestMapping("/loginByAdmin")
    public Map<String, Object> loginByAdmin(@RequestBody Map<String, String> params) {
        String userName = params.get("userName");
        String password = params.get("password");
        Map<String, Object> result = new HashMap<>();
        if (userName.isEmpty() || password.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("user_name", userName)
                .eq("password", password)
                .eq("user_type", "管理员");;
        if (userMapper.selectList(wrapper).size()<=0) {
            result.put("msg", "用户名或密码错误");
            return result;
        }
        result.put("success", true);
        result.put("userId", userMapper.selectList(wrapper).get(0).getUserId());
        return result;
    }

    @RequestMapping("/loginByUser")
    public Map<String, Object> loginByUser(@RequestBody Map<String, String> params) {
        String userName = params.get("userName");
        String password = params.get("password");
        Map<String, Object> result = new HashMap<>();
        if (userName.isEmpty() || password.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("user_name", userName)
                .eq("password", password)
                .eq("user_type", "普通用户");
        if (userMapper.selectList(wrapper).size()<=0) {
            result.put("msg", "用户名或密码错误");
            return result;
        }
        result.put("success", true);
        result.put("userId", userMapper.selectList(wrapper).get(0).getUserId());
        return result;
    }

    @RequestMapping("/getUserById")
    public Map<String, Object> getUserById(@RequestBody Map<String, String> params) throws Exception {
        String userId = params.get("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        result.put("success", true);
        User user = userMapper.selectById(userId);
        result.putAll(MapUtils.java2Map(user));
        result.put("createTime", DateUtils.D2NYR(user.getCreateTime()));
        result.put("updateTime", DateUtils.D2NYR(user.getUpdateTime()));
        return result;
    }

    @RequestMapping("/updateUser")
    public Map<String, Object> updateUser(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        String userName = params.get("userName");
        String name = params.get("name");
        String password = params.get("password");
        String telephone = params.get("telephone");
        String email = params.get("email");
        String answer = params.get("answer");
        Map<String, Object> result = new HashMap<>();
        if (userId.isEmpty() || userName.isEmpty() || password.isEmpty() || answer.isEmpty()) {
            result.put("msg", "参数不能为空");
            return result;
        }
        User user = userMapper.selectById(userId);
        User user1 = new User();
        if (user.getUserName().equals(userName)) {
            user1.setUserId(Integer.valueOf(userId));
            user1.setName(name);
            user1.setPassword(password);
            user1.setTelephone(telephone);
            user1.setEmail(email);
            user1.setAnswer(answer);
        } else if (userMapper.hasUsername(userName)>0) {
            result.put("msg", "用户名已被使用");
            return result;
        }
        user1.setUserName(userName);
        userMapper.updateById(user1);
        result.put("success", true);
        return result;
    }

}

