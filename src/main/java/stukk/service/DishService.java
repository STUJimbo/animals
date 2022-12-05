package stukk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import stukk.dto.DishDto;
import stukk.entity.Dish;

/**
 * @author wenli
 * @create 2022-09-01 18:08
 */
public interface DishService extends IService<Dish> {
    // 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void saveWithFlavor(DishDto dishDto);

    // 根据id来查询菜品信息和口味信息
    DishDto getByIdWithFlavor(Long dishId);

    // 根据id来修改菜品基本信息和口味信息
    void updateWithFlavor(DishDto dishDto);
}
