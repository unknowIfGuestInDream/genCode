package com.newangels.gen.enums;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * enum枚举类测试，拿出enum的值
 *
 * @author: TangLiang
 * @date: 2021/9/11 10:44
 * @since: 1.0
 */
@SpringBootTest
public class enumTest {

    @Test
    public void enum1() {
        for (DataBaseType dataBaseType : DataBaseType.values()) {
            System.out.println(dataBaseType.getTypeName());
            System.out.println(dataBaseType.toString());
        }
    }

}
