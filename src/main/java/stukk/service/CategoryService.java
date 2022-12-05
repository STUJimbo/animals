package stukk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import stukk.entity.Category;

/**
 * @author wenli
 * @create 2022-09-01 16:13
 */
public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
