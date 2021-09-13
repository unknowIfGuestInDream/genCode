package com.newangels.gen.genProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.enums.NameConventType;
import com.newangels.gen.factory.AbstractGenProcedureModelFactory;
import com.newangels.gen.factory.DataBaseProcedureFactory;
import com.newangels.gen.factory.DataSourceUtilFactory;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.AbstractGenProcedureModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 存储过程代码生成测试
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:40
 * @since: 1.0
 */
@SpringBootTest
public class genProcedureTest {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Test
    public void genProcedureEamNew() {
        AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromCode("4"));
        List<String> procedureNameList = new ArrayList<>();
        procedureNameList.add("PRO_BASE_DEPT_GET");
        procedureNameList.add("PRO_BASE_PERSONPOST_GET");
        Map<String, Object> result = genProcedureModel.genCode("code", "cn.newangels.gen", "budgetuser", procedureNameList, "admin", NameConventFactory.getNameConvent(NameConventType.fromCode("2")), DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName("oracle.jdbc.OracleDriver")), DataSourceUtilFactory.getDataSourceUtil("jdbc:oracle:thin:@10.18.26.86:1521/SE_BUDGET", "oracle.jdbc.OracleDriver", "budgetuser", "budgetpassword"), freeMarkerConfigurer.getConfiguration());
        System.out.println(result.get("controller"));
        System.out.println("============================================");
        System.out.println(result.get("service"));
        System.out.println("============================================");
        System.out.println(result.get("serviceImpl"));
        System.out.println("============================================");
        System.out.println(result.get("repository"));
    }

    @Test
    public void genProcedureEam() {
        AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromCode("3"));
        List<String> procedureNameList = new ArrayList<>();
        procedureNameList.add("PRO_BASE_DEPT_GET");
        procedureNameList.add("PRO_BASE_PERSONPOST_GET");
        Map<String, Object> result = genProcedureModel.genCode("code", "cn.newangels.gen", "budgetuser", procedureNameList, "admin", NameConventFactory.getNameConvent(NameConventType.fromCode("2")), DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName("oracle.jdbc.OracleDriver")), DataSourceUtilFactory.getDataSourceUtil("jdbc:oracle:thin:@10.18.26.86:1521/SE_BUDGET", "oracle.jdbc.OracleDriver", "budgetuser", "budgetpassword"), freeMarkerConfigurer.getConfiguration());
        System.out.println(result.get("controller"));
        System.out.println("============================================");
        System.out.println(result.get("service"));
        System.out.println("============================================");
        System.out.println(result.get("serviceImpl"));
        System.out.println("============================================");
        System.out.println(result.get("repository"));
    }

    @Test
    public void genProcedureOld() {
        AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromCode("2"));
        List<String> procedureNameList = new ArrayList<>();
        procedureNameList.add("PRO_BASE_DEPT_GET");
        procedureNameList.add("PRO_BASE_PERSONPOST_GET");
        Map<String, Object> result = genProcedureModel.genCode("code", "cn.newangels.gen", "budgetuser", procedureNameList, "admin", NameConventFactory.getNameConvent(NameConventType.fromCode("2")), DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName("oracle.jdbc.OracleDriver")), DataSourceUtilFactory.getDataSourceUtil("jdbc:oracle:thin:@10.18.26.86:1521/SE_BUDGET", "oracle.jdbc.OracleDriver", "budgetuser", "budgetpassword"), freeMarkerConfigurer.getConfiguration());
        System.out.println(result.get("controller"));
        System.out.println("============================================");
        System.out.println(result.get("service"));
        System.out.println("============================================");
        System.out.println(result.get("serviceImpl"));
        System.out.println("============================================");
        System.out.println(result.get("repository"));
    }

    @Test
    public void genProcedureRestful() {
        AbstractGenProcedureModel genProcedureModel = AbstractGenProcedureModelFactory.getGenProcedureModel(GenProcedureModelType.fromCode("1"));
        List<String> procedureNameList = new ArrayList<>();
        procedureNameList.add("PRO_BASE_DEPT_GET");
        procedureNameList.add("PRO_BASE_PERSONPOST_GET");
        Map<String, Object> result = genProcedureModel.genCode("code", "cn.newangels.gen", "budgetuser", procedureNameList, "admin", NameConventFactory.getNameConvent(NameConventType.fromCode("2")), DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName("oracle.jdbc.OracleDriver")), DataSourceUtilFactory.getDataSourceUtil("jdbc:oracle:thin:@10.18.26.86:1521/SE_BUDGET", "oracle.jdbc.OracleDriver", "budgetuser", "budgetpassword"), freeMarkerConfigurer.getConfiguration());
        System.out.println(result.get("controller"));
        System.out.println("============================================");
        System.out.println(result.get("service"));
        System.out.println("============================================");
        System.out.println(result.get("serviceImpl"));
        System.out.println("============================================");
        System.out.println(result.get("repository"));
    }

}
