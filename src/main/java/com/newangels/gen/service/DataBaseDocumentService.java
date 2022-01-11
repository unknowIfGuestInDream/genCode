package com.newangels.gen.service;

import java.util.List;

/**
 * 数据库文档生成
 *
 * @author: TangLiang
 * @date: 2021/10/8 13:46
 * @since: 1.0
 */
public interface DataBaseDocumentService {

    /**
     * 生成数据库文档文件字符流
     *
     * @param url            路径
     * @param driver         驱动名
     * @param userName       用户
     * @param password       密码
     * @param version        数据库文档版本号
     * @param description    数据库文档描述
     * @param fileName       数据库文档文件名
     * @param tableNames     指定表
     * @param tablePrefixs   指定表前缀
     * @param engineFileType EngineFileType类文件类型 值为(HTML,WORD,MD,XLS)
     */
    String executeFile(String url, String driver, String userName, String password, String version, String description, String fileName, String engineFileType, List<String> tableNames, List<String> tablePrefixs);
}
