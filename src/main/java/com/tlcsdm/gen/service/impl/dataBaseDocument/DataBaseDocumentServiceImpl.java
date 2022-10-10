package com.tlcsdm.gen.service.impl.dataBaseDocument;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.tlcsdm.gen.factory.DataSourceUtilFactory;
import com.tlcsdm.gen.service.DataBaseDocumentService;
import com.tlcsdm.gen.util.dataSource.DataSourceUtilTypes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

/**
 * @author: TangLiang
 * @date: 2021/10/8 13:47
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DataBaseDocumentServiceImpl implements DataBaseDocumentService {

	@Override
	public String executeFile(String url, String driver, String userName, String password, String version,
			String description, String fileName, String engineFileType, List<String> tableNames,
			List<String> tablePrefixs) {
		DataSource dataSource = DataSourceUtilFactory
				.getDataSourceUtil(url, driver, userName, password, DataSourceUtilTypes.HIKARI).getDataSource();
		// 创建 screw 的配置
		Configuration config = Configuration.builder()
				// 版本
				.version(version)
				// 描述
				.description(description)
				// 数据源
				.dataSource(dataSource)
				// 引擎配置
				.engineConfig(buildEngineConfig(EngineFileType.valueOf(engineFileType)))
				// 处理配置
				.produceConfig(buildProcessConfig(tableNames, tablePrefixs)).build();
		return new DocumentationExecute(config).executeFile();
	}

	/**
	 * 创建 screw 的引擎配置
	 */
	private EngineConfig buildEngineConfig(EngineFileType engineFileType) {
		return EngineConfig.builder()
				// 文件类型
				.fileType(engineFileType)
				// 文件类型
				.produceType(EngineTemplateType.freemarker).build();
	}

	/**
	 * 创建 screw 的处理配置，一般可忽略 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
	 */
	private ProcessConfig buildProcessConfig(List<String> tableNames, List<String> tablePrefixs) {
		return ProcessConfig.builder()
				// 根据名称指定表生成
				.designatedTableName(tableNames)
				// 根据表前缀生成
				.designatedTablePrefix(tablePrefixs)
				// 根据表后缀生成
				.designatedTableSuffix(Collections.emptyList())
				// 忽略表名 可以传空串
				// .ignoreTableName(Arrays.asList(""))
				// 忽略表前缀 可以传null
				// .ignoreTablePrefix(Collections.singletonList(""))
				// 忽略表后缀 可以传null
				// .ignoreTableSuffix(Collections.singletonList(""))
				.build();
	}

}
