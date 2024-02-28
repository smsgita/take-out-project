package com.dmj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dmj.dto.OrdersPageQueryDTO;
import com.dmj.entity.Orders;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void IUpdate(Orders orders);

    /**
     * 分页条件查询并按下单时间排序
     * @param ordersPageQueryDTO
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
    @Update("update orders set pay_status = 1 ,status = 2 where number = #{orderNumber}")
    void updatePayStatusByNumber(String orderNumber);
}
