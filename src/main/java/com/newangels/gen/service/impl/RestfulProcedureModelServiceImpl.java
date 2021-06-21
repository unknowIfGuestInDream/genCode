package com.newangels.gen.service.impl;

import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.factory.GenProcedureModelFactory;
import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.util.GenProcedureModelType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * RestFul风格代码
 *
 * @author: TangLiang
 * @date: 2021/6/20 13:00
 * @since: 1.0
 */
@Service
public class RestfulProcedureModelServiceImpl implements GenProcedureModelService {
    @Override
    public String getControllerCode(String moduleName, String packageName) {
        return "package " + packageName + ".controller;\n" +
                "\n" +
                "import " + packageName + ".annotation.Log;\n" +
                "import " + packageName + ".base.BaseUtils;\n" +
                "import " + packageName + ".service." + moduleName + "Service;\n" +
                "import lombok.RequiredArgsConstructor;\n" +
                "import org.springframework.web.bind.annotation.GetMapping;\n" +
                "import org.springframework.web.bind.annotation.PostMapping;\n" +
                "import org.springframework.web.bind.annotation.RequestParam;\n" +
                "import org.springframework.web.bind.annotation.RestController;\n" +
                "\n" +
                "import javax.servlet.http.HttpServletRequest;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "/**\n" +
                " *\n" +
                " *\n" +
                " * @author: \n" +
                " * @date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "@RestController\n" +
                "@RequiredArgsConstructor\n" +
                "public class " + moduleName + "Controller {\n" +
                "    private final " + moduleName + "Service " + BaseUtils.toLowerCase4Index(moduleName) + "Service;\n" +
                "{}" +
                "}";
    }

    @Override
    public String getServiceCode(String moduleName, String packageName) {
        return "package " + packageName + ".service;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "/**\n" +
                " * \n" +
                " *\n" +
                " * @author: \n" +
                " * @date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "public interface " + moduleName + "Service {\n" +
                "{}" +
                "\n" +
                "}";
    }

    @Override
    public String getServiceImplCode(String moduleName, String packageName) {
        return "package " + packageName + ".service.impl;\n" +
                "\n" +
                "import " + packageName + ".repository." + moduleName + "Repository;\n" +
                "import " + packageName + ".service." + moduleName + "Service;\n" +
                "import lombok.RequiredArgsConstructor;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import org.springframework.transaction.annotation.Propagation;\n" +
                "import org.springframework.transaction.annotation.Transactional;\n" +
                "\n" +
                "import java.util.Map;\n" +
                "\n" +
                "/**\n" +
                " * @author:\n" +
                " * @date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "@Service\n" +
                "@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)\n" +
                "@RequiredArgsConstructor\n" +
                "public class " + moduleName + "ServiceImpl implements " + moduleName + "Service {\n" +
                "    private final " + moduleName + "Repository " + BaseUtils.toLowerCase4Index(moduleName) + "Repository;\n" +
                "{}" +
                "}";
    }

    @Override
    public String getRepositoryCode(String moduleName, String packageName) {
        return "package " + packageName + ".repository;\n" +
                "\n" +
                "import " + packageName + ".util.ProcedureUtils;\n" +
                "import lombok.RequiredArgsConstructor;\n" +
                "import oracle.jdbc.OracleTypes;\n" +
                "import org.springframework.dao.DataAccessException;\n" +
                "import org.springframework.jdbc.core.CallableStatementCallback;\n" +
                "import org.springframework.jdbc.core.CallableStatementCreator;\n" +
                "import org.springframework.jdbc.core.JdbcTemplate;\n" +
                "import org.springframework.stereotype.Repository;\n" +
                "\n" +
                "import java.sql.CallableStatement;\n" +
                "import java.sql.Connection;\n" +
                "import java.sql.ResultSet;\n" +
                "import java.sql.SQLException;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "/**\n" +
                " * @author:\n" +
                " * @date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")) + "\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "@Repository\n" +
                "@RequiredArgsConstructor\n" +
                "public class " + moduleName + "Repository {\n" +
                "    private final JdbcTemplate " + packageName.substring(packageName.lastIndexOf(".") + 1).toLowerCase() + "JdbcTemplate;\n" +
                "{}" +
                "}";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GenProcedureModelFactory.register(GenProcedureModelType.RESTFUL, this);
    }
}
