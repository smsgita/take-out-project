package com.dmj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dmj.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
