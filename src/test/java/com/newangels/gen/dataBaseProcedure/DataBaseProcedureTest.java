package com.newangels.gen.dataBaseProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.DataBaseProcedureFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author: TangLiang
 * @date: 2021/6/19 9:09
 * @since: 1.0
 */
@SpringBootTest
public class DataBaseProcedureTest {

    @Test
    public void test() {
        String V_TYPE = "oracle.jdbc.driver.OracleDriver";
        DBUtil dbUtil = DBUtil.getDbUtil();
        dbUtil.setUrl("jdbc:mysql://42.192.10.174:3306/gen?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true");
        dbUtil.setDriverClass("com.mysql.jdbc.Driver");
        dbUtil.setUserName("tangliang");
        dbUtil.setPassword("tangliang");
        DataBaseProcedureService dbProcedure = DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName(V_TYPE));
        List<Map<String, Object>> list = dbUtil.executeQuery("select * from database_info");
        System.out.println(list);
    }

    @Test
    public void Tes1() {
        Map<String, Object> result = new HashMap<>(16);
        List<String> list = new ArrayList<>(Arrays.asList("get", "select", "selectWithPage", "insert", "update", "save", "delete"));
        result.put("get", "get");
        result.put("select", "select");
        result.put("selectWithPage", "selectWithPage");
        result.put("insert", "insert");
        result.put("update", "update");
        result.put("list", result.keySet());
        System.out.println(result.get("list"));
        System.out.println("=======================");
        result.put("list", list);
        System.out.println(result.get("list"));
    }
}
