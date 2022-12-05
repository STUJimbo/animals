package stukk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_blog")
public class Blog {

    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    String des;
    String image;
    Integer view;
    String content;
}
