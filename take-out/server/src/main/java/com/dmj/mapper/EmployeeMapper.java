package com.dmj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dmj.annotation.AutoFill;
import com.dmj.dto.EmployeePageQueryDTO;
import com.dmj.entity.Employee;
import com.dmj.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    Page<Employee> pageQuery(IPage page, EmployeePageQueryDTO pageQueryDTO);

    @AutoFill(OperationType.INSERT)
    default void add(Employee employee){
        this.insert(employee);
    };
}
