package stukk;

import org.junit.jupiter.api.Test;
import stukk.utils.QiNiuUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wenli
 * @create 2022-12-09 10:35
 */
public class MyTest {

    @Test
    public void test1() {
        File file = new File("img");
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                String originName = f.getName();
                int i = originName.lastIndexOf(".");
                String suffix = originName.substring(i);
                String fileName = UUID.randomUUID() + suffix;
                fileName = fileName.replaceAll("-", "");
                try (FileInputStream inputStream = new FileInputStream(f);) {
                    QiNiuUtils.upload2QiNiu(inputStream, fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
