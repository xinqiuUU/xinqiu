package com.yc.web.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.AdminJsonModel;
import com.yc.bean.User;
import com.yc.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Slf4j
@RequestMapping("/security/admin")
public class AdminUserController {
    @Autowired
    private UserMapper userMapper;
    /**
     * 获取所有的用户参数列表
     */
    @GetMapping("/getAllUser")
    public AdminJsonModel getAllUser(@RequestParam String page,
                                     @RequestParam String limit,
                                     @RequestParam(required = false) String uname,
                                     @RequestParam(required = false) String email,
                                     @RequestParam(required = false) String tel,
                                     @RequestParam(required = false) Integer role,
                                     @RequestParam(required = false) Integer status) {
        // 回传给前端的数据 data msg code(0为查询成功) count
        AdminJsonModel jm = new AdminJsonModel();

        // 确保页面参数和每页条数参数有效
        int currentPage = Integer.parseInt(page);
        int pageSize = Integer.parseInt(limit);

        // 创建分页对象
        Page<User> userPage = new Page<>(currentPage, pageSize);

        // 创建查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 添加查询条件
        if (uname != null && !uname.isEmpty()) {
            queryWrapper.eq("uname", uname); // 用户名
        }
        if (email != null && !email.isEmpty()) {
            queryWrapper.eq("email", email); // 邮箱
        }
        if (tel != null && !tel.isEmpty()) {
            queryWrapper.eq("tel", tel); // 电话
        }
        if (role != null && role != -1) {
            queryWrapper.eq("role", role); // 角色
        }
        if (status != null && status != -1) {
            queryWrapper.eq("status", status); // 状态
        }

        // 查询用户
        List<User> userList = userMapper.selectPage(userPage, queryWrapper).getRecords();

        // 如果查询结果为空
        if (userList == null || userList.isEmpty()) {
            jm.setCode(1);
            jm.setMsg("查询用户失败，用户列表为空");
            return jm;
        }

        jm.setCode(0);
        jm.setMsg("查询用户成功");
        jm.setData(userList);
        jm.setCount(userMapper.selectCount(queryWrapper)); // 获取总条数

        return jm; // 返回结果
    }


    /**
     * 停用一个用户
     */
    @PostMapping("/stopUser") // 处理停用用户的请求
    public AdminJsonModel stopUser(@RequestParam("idsStr") int uid) {
        AdminJsonModel jm = new AdminJsonModel();

        // 创建一个 User 对象，用于更新状态
        User user = new User();
        user.setUid(uid); // 设置要停用的用户 ID
        user.setStatus(0); // 设置状态为 0（停用）

        // 使用 MyBatis Plus 的 updateById 方法进行更新
        int result = userMapper.updateById(user);

        if (result <= 0) {
            jm.setCode(1); // 更新失败
            jm.setMsg("停用用户失败");
        } else {
            jm.setCode(0); // 用户更新成功
            jm.setMsg("用户停用成功");
        }

        return jm; // 返回 AdminJsonModel 对象
    }

    /**
     * 专门用来处理批量操作用户状态的
     * @param status 用户状态
     * @param idsStr 用户 ID 列表（以逗号分隔）
     * @return AdminJsonModel
     */
    @PostMapping("/stopAndNormal") // 处理批量更新用户状态的请求
    public AdminJsonModel stopAndNormal(@RequestParam("status") String status,
                                        @RequestParam("idsStr") String idsStr) {
        AdminJsonModel jm = new AdminJsonModel();

        // 将 id 字符串转换为 List<Integer>
        List<Integer> userIdList = extractNumbers(idsStr);

        // 创建一个 User 对象，准备更新状态
        User user = new User();
        user.setStatus("1".equals(status) ? 1 : 0); // 根据传入的状态设置用户状态

        // 执行批量更新
        int result = userMapper.update(user, Wrappers.<User>lambdaUpdate().in(User::getUid, userIdList));

        if (result <= 0) {
            jm.setCode(1); // 更新失败
            jm.setMsg("更新用户失败");
        } else {
            jm.setCode(0); // 更新成功
            jm.setMsg("更新用户成功");
        }
        return jm; // 返回 AdminJsonModel 对象
    }

    /**
     * 获取字符串中的所有id
     * begin:1,2,3,4,5,
     * @param idsStr
     * @return 数字集合
     */
    public static ArrayList<Integer> extractNumbers(String idsStr) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(idsStr);

        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        return numbers;
    }


}
