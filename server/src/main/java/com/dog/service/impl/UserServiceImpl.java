package com.dog.service.impl;

import com.dog.common.BusinessException;
import com.dog.common.ResultCode;
import com.dog.entity.User;
import com.dog.mapper.UserMapper;
import com.dog.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        return user;
    }

    @Override
    public List<User> list(String keyword, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return userMapper.selectList(keyword, offset, pageSize);
    }

    @Override
    public long count(String keyword) {
        return userMapper.countList(keyword);
    }

    @Override
    public void register(User user) {
        User exist = userMapper.selectByUsername(user.getUsername());
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setRole("user");
        user.setStatus(1);
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        User exist = getById(user.getId());
        if (exist == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        userMapper.update(user);
    }

    @Override
    public void delete(Long id) {
        User user = getById(id);
        if ("admin".equals(user.getRole())) {
            throw new BusinessException("不能删除管理员账号");
        }
        userMapper.deleteById(id);
    }

    @Override
    public long countTotal() {
        return userMapper.countTotal();
    }
}
