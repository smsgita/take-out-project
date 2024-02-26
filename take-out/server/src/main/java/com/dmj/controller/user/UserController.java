package com.dmj.controller.user;

import com.dmj.constant.JwtClaimsConstant;
import com.dmj.dto.UserLoginDTO;
import com.dmj.entity.User;
import com.dmj.properties.JwtProperties;
import com.dmj.result.Result;
import com.dmj.service.UserService;
import com.dmj.utils.JwtUtil;
import com.dmj.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "C端用户相关接口")
@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信用户登录：{}",userLoginDTO.getCode());

        // 微信登录
        User user = userService.wxLogin(userLoginDTO);
        // 为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());

        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token).build();

        return Result.success(userLoginVO);
    }
}
