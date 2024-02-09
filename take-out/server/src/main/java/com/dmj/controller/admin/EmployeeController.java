package com.dmj.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dmj.constant.JwtClaimsConstant;
import com.dmj.dto.EmployeeDTO;
import com.dmj.dto.EmployeeLoginDTO;
import com.dmj.dto.EmployeePageQueryDTO;
import com.dmj.entity.Employee;
import com.dmj.properties.JwtProperties;
import com.dmj.result.PageResult;
import com.dmj.result.Result;
import com.dmj.service.EmployeeService;
import com.dmj.utils.JwtUtil;
import com.dmj.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("新增员工")
    public Result save(EmployeeDTO employeeDTO){
        log.info("新增员工：()",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }
    /**
     * 新增员工
     * @param pageQueryDTO
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("新增员工")
    public Result page(EmployeePageQueryDTO pageQueryDTO){
        PageResult pageResult = employeeService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     *  员工启用或停止
     * @param status 状态
     * @param id 员工id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("员工启用或停止")
    public Result startOrUpdate(@PathVariable Integer status,Long id){
        employeeService.startOrUpdate(status,id);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee>getById(@PathVariable Long id){
        Employee employee = employeeService.getByid(id);
        return Result.success(employee);
    }

    /**
     * 员工信息编辑
     * @param employeeDTO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("员工信息编辑")
    public Result<Employee>update(@RequestBody EmployeeDTO employeeDTO){
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
