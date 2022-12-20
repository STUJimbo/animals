package stukk.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import stukk.common.R;
import stukk.utils.QiNiuUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wenli
 * @create 2022-12-09 21:07
 */
@RestController
@RequestMapping("/common")
@Slf4j
@Api(tags = "文件管理（FileController）")
public class FileController {
    @ApiOperation(value = "文件上转")
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        // file是临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除

        // 原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID() + suffix;

        fileName = fileName.replaceAll("-", "");

        QiNiuUtils.upload2QiNiu(file.getInputStream(), fileName);

        return R.success(fileName);
    }
}
