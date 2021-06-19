package com.newangels.gen.dataBaseProcedure;

import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DBUtil;
import com.newangels.gen.util.DataBaseFactory;
import com.newangels.gen.util.DataBaseType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        dbUtil.setUrl("");
        dbUtil.setDriverClass("");
        dbUtil.setUserName("");
        dbUtil.setPassword("");
        DataBaseProcedureService dbProcedure = DataBaseFactory.getDataBaseProcedureService(DataBaseType.fromTypeName(V_TYPE));

    }
}
