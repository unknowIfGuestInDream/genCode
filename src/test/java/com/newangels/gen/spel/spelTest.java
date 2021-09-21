package com.newangels.gen.spel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * spel表达式
 *
 * @author: TangLiang
 * @date: 2021/9/20 16:13
 * @since: 1.0
 */
@SpringBootTest
public class spelTest {
    @Value("#{5}")
    private Integer num1;
    @Value("#{'hello'}")
    private String str1;
    @Value("#{1.024E+3}")
    private Long long1;
    @Value("#{0xFFFF}")
    private Integer num2;
    @Value("#{'true'}")
    private boolean bool1;
    @Value("#{true}")
    private String bool2;
    @Value("#{2+2*3/2}")
    private double dou1;
    @Value("#{10 % 3}")
    private double dou2;
    @Value("#{10 MOD 3}")
    private double dou3;
    @Value("#{2 ^ 3}")
    private double dou4;
    @Value("#{'1 == 2'}")
    private String bool3;
    @Value("#{'1 EQ 2'}")
    private String bool4;
    @Value("#{1 EQ 2}")
    private boolean bool5;
    @Value("#{10 between {5,20}}")
    private boolean bool6;
    @Value("#{new int[3]}")
    private int[] int1;
    @Value("#{{'jack','rose','lili'}}")
    private List<String> list1;
    @Value("#{{0:'jack',1:'rose',2:'lili'}}")
    private Map<String, Object> map1;
    @Value("#{1+1>2 ? 'Y':'N'}")
    private String str3;
    @Value("#{T(Math).abs(-1)}")
    private Integer int2;
    @Value("#{'asdf' instanceof T(String)}")
    private boolean bool7;
    @Value("#{#name?.toUpperCase()}")
    private String str4;
    @Value("#{{1, 3, 5, 7}.?[#this > 3]}")//this表示当前的对象
    private int[] int3;
    @Value("#{not false}")
    private boolean bool8;
    @Value("#{! false}")
    private String str5;
    @Value("#{true && false}")
    private String str6;
    @Value("#{true || false}")
    private String str7;

    @Test
    public void spel1() {
        System.out.println(num1);
        System.out.println(str1);
        System.out.println(long1);
        System.out.println(num2);
        System.out.println(bool1);
        System.out.println(bool2);
        System.out.println(dou1);
        System.out.println(dou2);
        System.out.println(dou3);
        System.out.println(dou4);
        System.out.println(bool3);
        System.out.println(bool4);
        System.out.println(bool5);
        System.out.println(bool6);
        System.out.println(int1.length);
        System.out.println(list1);
        System.out.println(map1);
        System.out.println(str3);
        System.out.println(int2);
        System.out.println(bool7);
        System.out.println(str4);
        System.out.println(int3.length);
        System.out.println(bool8);
        System.out.println(str5);
        System.out.println(str6);
        System.out.println(str7);
    }
}
