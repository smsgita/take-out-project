package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Dish;
import generator.service.DishService;
import generator.mapper.DishMapper;
import org.springframework.stereotype.Service;

/**
* @author dmj
* @description 针对表【dish(菜品)】的数据库操作Service实现
* @createDate 2024-02-09 16:06:39
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

}




