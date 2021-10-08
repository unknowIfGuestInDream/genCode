package com.newangels.gen.controller;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.newangels.gen.annotation.Log;
import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.service.DataBaseDocumentService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * 数据库文档生成
 *
 * @author: TangLiang
 * @date: 2021/10/8 13:45
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class DataBaseDocumentController {
    private final DataBaseDocumentService dataBaseDocumentService;

    /**
     * 生成数据库文档
     * //TODO 请求加随机数
     */
    @GetMapping("genDataBaseDocument")
    @Log
    public void genDataBaseDocument(String url, String driver, String userName, String password, String version, String description, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //druid连接池有点问题，因此使用hikari连接池，疑似要升级druid版本到1.1.21
        HikariDataSource dataSource = buildDataSource(url, driver, userName, password);
        // 创建 screw 的配置
        Configuration config = Configuration.builder()
                // 版本
                .version("1.0.0")
                // 描述
                .description("文档描述")
                // 数据源
                .dataSource(dataSource)
                // 引擎配置
                .engineConfig(buildEngineConfig())
                // 处理配置
                .produceConfig(buildProcessConfig())
                .build();
        String file = new DocumentationExecute(config).executeFile();
        @Cleanup InputStream inputStream = new ByteArrayInputStream(file.getBytes(StandardCharsets.UTF_8));
        dataSource.close();
        BaseUtils.download(inputStream, "数据库文档.md", request, response);
    }

    /**
     * 创建数据源
     */
    private static HikariDataSource buildDataSource(String url, String driver, String userName, String password) {
        // 创建 HikariConfig 配置类
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        // 设置可以获取 tables remarks 信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
//        hikariConfig.addDataSourceProperty("characterEncoding", "UTF-8");
        // 创建数据源
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 创建 screw 的引擎配置
     */
    private EngineConfig buildEngineConfig() {
        return EngineConfig.builder()
                // 文件类型
                .fileType(EngineFileType.MD)
                // 文件类型
                .produceType(EngineTemplateType.freemarker)
                .build();
    }

    /**
     * 创建 screw 的处理配置，一般可忽略
     * 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
     */
    private ProcessConfig buildProcessConfig() {
        return ProcessConfig.builder()
                // 根据名称指定表生成
                .designatedTableName(Collections.emptyList())
                //根据表前缀生成
                .designatedTablePrefix(Collections.emptyList())
                // 根据表后缀生成
                .designatedTableSuffix(Collections.emptyList())
                // 忽略表名 可以传空串
//                .ignoreTableName(Arrays.asList(""))
                // 忽略表前缀 可以传null
//                .ignoreTablePrefix(Collections.singletonList(""))
                // 忽略表后缀 可以传null
//                .ignoreTableSuffix(Collections.singletonList(""))
                .build();
    }

}
