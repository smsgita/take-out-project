package com.dmj.service;

import com.dmj.dto.UserLoginDTO;
import com.dmj.entity.User;

public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);
}
