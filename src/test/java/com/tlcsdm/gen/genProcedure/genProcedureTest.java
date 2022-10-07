package com.tlcsdm.gen.genProcedure;

import com.tlcsdm.gen.enums.DataBaseType;
import com.tlcsdm.gen.enums.GenProcedureModelType;
import com.tlcsdm.gen.enums.NameConventType;
import com.tlcsdm.gen.factory.AbstractGenProcedureModelFactory;
import com.tlcsdm.gen.factory.DataBaseProcedureFactory;
import com.tlcsdm.gen.factory.DataSourceUtilFactory;
import com.tlcsdm.gen.factory.NameConventFactory;
import com.tlcsdm.gen.service.AbstractGenProcedureModel;
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
        Map<String, Object> result = genProcedureModel.genCode("code", "cn.tlcsdm.gen", "budgetuser", procedureNameList, "admin", NameConventFactory.getNameConvent(NameConventType.fromCode("2")), DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName("oracle.jdbc.OracleDriver")), DataSourceUtilFactory.getDataSourceUtil("jdbc:oracle:thin:@10.18.26.86:1521/SE_BUDGET", "oracle.jdbc.OracleDriver", "budgetuser", "budgetpassword"), freeMarkerConfigurer.getConfiguration());
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
        Map<String, Object> result = genProcedureModel.genCode("code", "cn.tlcsdm.gen", "budgetuser", procedureNameList, "admin", NameConventFactory.getNameConvent(NameConventType.fromCode("2")), DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName("oracle.jdbc.OracleDriver")), DataSourceUtilFactory.getDataSourceUtil("jdbc:oracle:thin:@10.18.26.86:1521/SE_BUDGET", "oracle.jdbc.OracleDriver", "budgetuser", "budgetpassword"), freeMarkerConfigurer.getConfiguration());
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
        Map<String, Object> result = genProcedureModel.genCode("code", "cn.tlcsdm.gen", "budgetuser", procedureNameList, "admin", NameConventFactory.getNameConvent(NameConventType.fromCode("2")), DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName("oracle.jdbc.OracleDriver")), DataSourceUtilFactory.getDataSourceUtil("jdbc:oracle:thin:@10.18.26.86:1521/SE_BUDGET", "oracle.jdbc.OracleDriver", "budgetuser", "budgetpassword"), freeMarkerConfigurer.getConfiguration());
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
        Map<String, Object> result = genProcedureModel.genCode("code", "cn.tlcsdm.gen", "budgetuser", procedureNameList, "admin", NameConventFactory.getNameConvent(NameConventType.fromCode("2")), DataBaseProcedureFactory.getDataBaseProcedure(DataBaseType.fromTypeName("oracle.jdbc.OracleDriver")), DataSourceUtilFactory.getDataSourceUtil("jdbc:oracle:thin:@10.18.26.86:1521/SE_BUDGET", "oracle.jdbc.OracleDriver", "budgetuser", "budgetpassword"), freeMarkerConfigurer.getConfiguration());
        System.out.println(result.get("controller"));
        System.out.println("============================================");
        System.out.println(result.get("service"));
        System.out.println("============================================");
        System.out.println(result.get("serviceImpl"));
        System.out.println("============================================");
        System.out.println(result.get("repository"));
    }

}
