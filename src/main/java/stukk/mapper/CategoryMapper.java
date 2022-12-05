package stukk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import stukk.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wenli
 * @create 2022-09-01 16:12
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
