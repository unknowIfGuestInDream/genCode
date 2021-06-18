package com.newangels.gen;

import com.newangels.gen.util.DBUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: TangLiang
 * @date: 2021/6/18 17:21
 * @since: 1.0
 */
@SpringBootTest
public class DbUtilTest {

    @Test
    public void test1() {
        DBUtil dbUtil = DBUtil.getDbUtil();
        System.out.println(dbUtil);
        System.out.println(dbUtil.getUrl());
        DBUtil oracle = (DBUtil) dbUtil.clone();
        System.out.println(oracle);
        System.out.println(oracle.getUrl());
        oracle.setUrl("hahaha");
        System.out.println(oracle.getUrl());
        System.out.println(dbUtil.getUrl());
        DBUtil oracle1 = (DBUtil) dbUtil.clone();
        System.out.println(oracle1);
        System.out.println(oracle1.getUrl());
    }
}
