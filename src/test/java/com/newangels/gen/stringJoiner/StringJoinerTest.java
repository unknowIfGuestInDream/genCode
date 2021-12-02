package com.newangels.gen.stringJoiner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.StringJoiner;

/**
 * @author: TangLiang
 * @date: 2021/12/2 11:18
 * @since: 1.0
 */
@SpringBootTest
public class StringJoinerTest {

    @Test
    public void test1() {
        StringJoiner s1 = new StringJoiner(", ");
        StringJoiner s2 = new StringJoiner(". ");
        s1.add("1");
        s1.add("2");
        s1.add("3");
        s2.add("4");
        s2.add("5");
        s2.add("6");
        System.out.println(s1.merge(s2).toString());
        System.out.println(s2.merge(s1).toString());
    }
}
