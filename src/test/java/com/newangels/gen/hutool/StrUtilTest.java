package com.newangels.gen.hutool;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * hutool StrUtil测试
 *
 * @author: TangLiang
 * @date: 2021/6/20 13:09
 * @since: 1.0
 */
@SpringBootTest
public class StrUtilTest {
    @Test
    public void test() {
        String template = "public class {}Controller {";
        System.out.println(StrUtil.format(template, "Code"));
    }
}
