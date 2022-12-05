package stukk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import stukk.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wenli
 * @create 2022-09-01 18:06
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
