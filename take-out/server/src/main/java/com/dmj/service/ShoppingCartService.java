package com.dmj.service;

import com.dmj.dto.ShoppingCartDTO;
import com.dmj.entity.ShoppingCart;
import com.dmj.result.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * 清空购物车
     */
    void clean();

    void sub(ShoppingCartDTO shoppingCartDTO);
}
