package com.newangels.gen.dataBaseProcedure;

import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DBUtil;
import com.newangels.gen.util.DataBaseFactory;
import com.newangels.gen.util.DataBaseType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

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
        DBUtil dbUtil = (DBUtil) DBUtil.getDbUtil().clone();
        dbUtil.setUrl("jdbc:mysql://42.192.10.174:3306/gen?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai&autoReconnect=true");
        dbUtil.setDriverClass("com.mysql.jdbc.Driver");
        dbUtil.setUserName("tangliang");
        dbUtil.setPassword("tangliang");
        DataBaseProcedureService dbProcedure = DataBaseFactory.getDataBaseProcedureService(DataBaseType.fromTypeName(V_TYPE));
        List<Map<String, Object>> list = dbUtil.executeQuery("select * from database_info");
        System.out.println(list);
    }
}
