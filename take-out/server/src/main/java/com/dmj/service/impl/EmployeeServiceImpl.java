package com.dmj.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dmj.constant.MessageConstant;
import com.dmj.constant.StatusConstant;
import com.dmj.context.BaseContext;
import com.dmj.dto.EmployeeDTO;
import com.dmj.dto.EmployeeLoginDTO;
import com.dmj.dto.EmployeePageQueryDTO;
import com.dmj.entity.Employee;
import com.dmj.exception.AccountLockedException;
import com.dmj.exception.AccountNotFoundException;
import com.dmj.exception.PasswordErrorException;
import com.dmj.mapper.EmployeeMapper;
import com.dmj.result.PageResult;
import com.dmj.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    /**
     * 新增员工
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        // 属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);
        // 设置账号的状态，默认为正常状态 1表示正常 0表示锁定
        employee.setStatus(StatusConstant.ENABLE);
        // 设置密码，默认密码为123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // 设置当前记录的创建时间和修改时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        // 设置当前记录创建人的id和修改人id
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.add(employee);
    }

    /**
     *  员工分页查询
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO pageQueryDTO) {
        Page<Employee> employeePage = employeeMapper.pageQuery(new Page(pageQueryDTO.getPage(), pageQueryDTO.getPageSize()), pageQueryDTO);
        long total = employeePage.getTotal();
        List<Employee> records = employeePage.getRecords();

        return new PageResult(total,records);
    }

    /**
     * 员工启用或禁用
     * @param status
     * @param id
     */
    @Override
    public void startOrUpdate(Integer status, Long id) {
        Employee employee = employeeMapper.selectById(id);
        employee.setStatus(status);
        int numbers = employeeMapper.updateById(employee);
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Override
    public Employee getByid(Long id) {
        return employeeMapper.selectById(id);
    }

    /**
     * 员工信息编辑
     * @param employeeDTO
     * @return
     */
    @Override
    public int update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        return employeeMapper.updateById(employee);
    }
}
