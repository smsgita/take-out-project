package com.dmj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dmj.dto.EmployeeDTO;
import com.dmj.dto.EmployeeLoginDTO;
import com.dmj.dto.EmployeePageQueryDTO;
import com.dmj.entity.Employee;
import com.dmj.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param pageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO pageQueryDTO);

    /**
     * 禁用或启用员工
     * @param status
     * @param id
     */
    void startOrUpdate(Integer status, Long id);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    Employee getByid(Long id);

    /**
     * 员工信息编辑
     * @param employeeDTO
     * @return
     */
    int update(EmployeeDTO employeeDTO);
}
