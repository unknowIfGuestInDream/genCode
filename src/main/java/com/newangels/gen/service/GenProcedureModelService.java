package com.newangels.gen.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * 存储过程代码生成模板
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:15
 * @since: 1.0
 */
public interface GenProcedureModelService extends InitializingBean {

    /**
     * 控制层代码生成
     *
     * @param moduleName  模块名
     * @param packageName 包名
     */
    String getControllerCode(String moduleName, String packageName);

    /**
     * 控制层代码生成
     *
     * @param moduleName  模块名
     * @param packageName 包名
     */
    String getServiceCode(String moduleName, String packageName);

    /**
     * 控制层代码生成
     *
     * @param moduleName  模块名
     * @param packageName 包名
     */
    String getServiceImplCode(String moduleName, String packageName);

    /**
     * 控制层代码生成
     *
     * @param moduleName  模块名
     * @param packageName 包名
     */
    String getRepositoryCode(String moduleName, String packageName);

    /**
     * BaseUtils工具类
     *
     * @param packageName 包名
     */
    default String getBaseUtils(String packageName) {
        return "package " + packageName + ".base;\n" +
                "\n" +
                "import com.alibaba.druid.util.StringUtils;\n" +
                "import lombok.Cleanup;\n" +
                "import org.apache.poi.hssf.usermodel.*;\n" +
                "import org.apache.poi.ss.usermodel.Workbook;\n" +
                "\n" +
                "import javax.servlet.ServletOutputStream;\n" +
                "import javax.servlet.http.Cookie;\n" +
                "import javax.servlet.http.HttpServletRequest;\n" +
                "import javax.servlet.http.HttpServletResponse;\n" +
                "import java.io.*;\n" +
                "import java.net.InetAddress;\n" +
                "import java.net.URLEncoder;\n" +
                "import java.net.UnknownHostException;\n" +
                "import java.nio.charset.StandardCharsets;\n" +
                "import java.util.*;\n" +
                "import java.util.stream.Collectors;\n" +
                "\n" +
                "/**\n" +
                " * @author: TangLiang\n" +
                " * @date: 2021/4/14 15:16\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "public class BaseUtils {\n" +
                "\n" +
                "    /**\n" +
                "     * 类不能实例化\n" +
                "     */\n" +
                "    private BaseUtils() {\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 不同浏览器将下载文件名处理为中文\n" +
                "     *\n" +
                "     * @param request HttpServletRequest对象\n" +
                "     * @param s       文件名\n" +
                "     * @return 文件名\n" +
                "     * @throws UnsupportedEncodingException 不支持的解码方式\n" +
                "     */\n" +
                "    public static String getFormatString(HttpServletRequest request, String s) throws UnsupportedEncodingException {\n" +
                "        String filename = s;\n" +
                "        String userAgent = request.getHeader(\"User-Agent\").toUpperCase();\n" +
                "        if (userAgent.indexOf(\"FIREFOX\") > 0) {\n" +
                "            filename = new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1); // firefox浏览器\n" +
                "        } else if (userAgent.indexOf(\"MSIE\") > 0) {\n" +
                "            filename = URLEncoder.encode(s, \"UTF-8\");// IE浏览器\n" +
                "        } else if (userAgent.indexOf(\"CHROME\") > 0) {\n" +
                "            filename = new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);// 谷歌\n" +
                "        }\n" +
                "        return filename;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 下载文件\n" +
                "     *\n" +
                "     * @param inputStream 文件输入流\n" +
                "     * @param fileName    文件名\n" +
                "     * @param request     request\n" +
                "     * @param response    response\n" +
                "     */\n" +
                "    public static void download(InputStream inputStream, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {\n" +
                "        fileName = getFormatString(request, fileName);\n" +
                "        response.reset();\n" +
                "        response.setHeader(\"Content-Disposition\", \"attachment; filename=\" + fileName);// 下载模式\n" +
                "        @Cleanup ServletOutputStream out = response.getOutputStream();\n" +
                "        byte[] content = new byte[65535];\n" +
                "        int length = 0;\n" +
                "        while ((length = inputStream.read(content)) != -1) {\n" +
                "            out.write(content, 0, length);\n" +
                "            out.flush();\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 下载文件\n" +
                "     *\n" +
                "     * @param wb       excel对象\n" +
                "     * @param fileName 文件名\n" +
                "     * @param request  request\n" +
                "     * @param response response\n" +
                "     */\n" +
                "    public static void download(Workbook wb, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {\n" +
                "        fileName = getFormatString(request, fileName);\n" +
                "        response.setContentType(\"application/vnd.ms-excel;charset=UTF-8\");\n" +
                "        response.setHeader(\"Content-Disposition\", \"attachment;filename=\" + fileName);\n" +
                "        OutputStream os = response.getOutputStream();\n" +
                "        wb.write(os);\n" +
                "        os.flush();\n" +
                "        os.close();\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 流转字节数组\n" +
                "     *\n" +
                "     * @param inputStream 流\n" +
                "     * @return 流转字节数组\n" +
                "     * @throws Exception IOException\n" +
                "     */\n" +
                "    public static byte[] inputStreamToBytes(InputStream inputStream) throws Exception {\n" +
                "        ByteArrayOutputStream baos = new ByteArrayOutputStream();\n" +
                "        byte[] content = new byte[65535];\n" +
                "        int length = 0;\n" +
                "        while ((length = inputStream.read(content)) != -1) {\n" +
                "            baos.write(content, 0, length);\n" +
                "        }\n" +
                "\n" +
                "        return baos.toByteArray();\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 流转字符串\n" +
                "     *\n" +
                "     * @param inputStream 流\n" +
                "     * @return 流转字符串\n" +
                "     * @throws Exception IOException\n" +
                "     */\n" +
                "    public static String inputStreamToString(InputStream inputStream) throws Exception {\n" +
                "        ByteArrayOutputStream baos = new ByteArrayOutputStream();\n" +
                "        byte[] content = new byte[65535];\n" +
                "        int length = 0;\n" +
                "        while ((length = inputStream.read(content)) != -1) {\n" +
                "            baos.write(content, 0, length);\n" +
                "        }\n" +
                "\n" +
                "        return baos.toString();\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 个位数填充0\n" +
                "     *\n" +
                "     * @param str 需要填充的字符串\n" +
                "     * @return 填充后结果\n" +
                "     */\n" +
                "    public static String fillWithZero(String str) {\n" +
                "        if (str != null && str.length() < 2) {\n" +
                "            return \"0\" + str;\n" +
                "        }\n" +
                "        return str;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 获得UUID\n" +
                "     *\n" +
                "     * @return UUID\n" +
                "     */\n" +
                "    public static String getUuid() {\n" +
                "        return UUID.randomUUID().toString().replaceAll(\"-\", \"\");\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 返回成功信息(用于存储过程方式的结果返回)\n" +
                "     *\n" +
                "     * @param result 数据集\n" +
                "     * @return java.util.Map\n" +
                "     */\n" +
                "    public static Map<String, Object> success(Map<String, Object> result) {\n" +
                "        result.put(\"success\", true);\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 返回成功信息(用于存储过程方式的结果返回)\n" +
                "     *\n" +
                "     * @return java.util.Map\n" +
                "     */\n" +
                "    public static Map<String, Object> success() {\n" +
                "        return Collections.singletonMap(\"success\", true);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 返回失败信息(用于存储过程方式的结果返回)\n" +
                "     *\n" +
                "     * @param message 错误信息\n" +
                "     * @return java.util.Map\n" +
                "     */\n" +
                "    public static Map<String, Object> failed(String message) {\n" +
                "        Map<String, Object> result = new HashMap<>(4);\n" +
                "        result.put(\"success\", false);\n" +
                "        result.put(\"message\", message);\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 返回失败信息(用于存储过程方式的结果返回)\n" +
                "     *\n" +
                "     * @param result  数据集\n" +
                "     * @param message 错误信息\n" +
                "     * @return java.util.Map\n" +
                "     */\n" +
                "    public static Map<String, Object> failed(Map<String, Object> result, String message) {\n" +
                "        result.put(\"success\", false);\n" +
                "        result.put(\"message\", message);\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 获取请求客户端ip地址\n" +
                "     *\n" +
                "     * @param request request\n" +
                "     * @return 客户端ip地址\n" +
                "     */\n" +
                "    public static String getIp(HttpServletRequest request) {\n" +
                "        String ip = request.getHeader(\"X-FORWARDED-FOR\");\n" +
                "        if (StringUtils.isEmpty(ip) || \"unknown\".equalsIgnoreCase(ip)) {\n" +
                "            ip = request.getHeader(\"Proxy-Client-IP\");\n" +
                "        }\n" +
                "        if (StringUtils.isEmpty(ip) || \"unknown\".equalsIgnoreCase(ip)) {\n" +
                "            ip = request.getHeader(\"WL-Proxy-Client-IP\");\n" +
                "        }\n" +
                "        if (StringUtils.isEmpty(ip) || \"unknown\".equalsIgnoreCase(ip)) {\n" +
                "            ip = request.getHeader(\"HTTP_CLIENT_IP\");\n" +
                "        }\n" +
                "        if (StringUtils.isEmpty(ip) || \"unknown\".equalsIgnoreCase(ip)) {\n" +
                "            ip = request.getHeader(\"HTTP_X_FORWARDED_FOR\");\n" +
                "        }\n" +
                "        if (StringUtils.isEmpty(ip) || \"unknown\".equalsIgnoreCase(ip)) {\n" +
                "            ip = request.getRemoteAddr();\n" +
                "        }\n" +
                "        if (\"0:0:0:0:0:0:0:1\".equals(ip)) {\n" +
                "            return \"127.0.0.1\";\n" +
                "        }\n" +
                "\n" +
                "        return ip;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 获取请求url路径\n" +
                "     *\n" +
                "     * @param request request\n" +
                "     * @return url路径\n" +
                "     */\n" +
                "    public static String getUrl(HttpServletRequest request) {\n" +
                "        String url = request.getScheme() + \"://\" + request.getServerName() + \":\" + request.getServerPort() + request.getServletPath();\n" +
                "        if (request.getQueryString() != null) {\n" +
                "            url += \"?\" + request.getQueryString();\n" +
                "        }\n" +
                "\n" +
                "        return url;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 主机名\n" +
                "     *\n" +
                "     * @return 主机名\n" +
                "     */\n" +
                "    public static String getHostName() {\n" +
                "        try {\n" +
                "            return InetAddress.getLocalHost().getHostName();\n" +
                "        } catch (UnknownHostException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        return \"未知\";\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 获取cookie值\n" +
                "     *\n" +
                "     * @param key     cookie键\n" +
                "     * @param request request\n" +
                "     * @return cookie值\n" +
                "     */\n" +
                "    public static String getCookieValue(String key, HttpServletRequest request) {\n" +
                "        Cookie[] cookies = request.getCookies();\n" +
                "        if (cookies != null) {\n" +
                "            for (Cookie cookie : cookies) {\n" +
                "                if (key.equals(cookie.getName())) {\n" +
                "                    return cookie.getValue();\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        return \"\";\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 分页\n" +
                "     *\n" +
                "     * @param list  需要分页的list\n" +
                "     * @param page  当前页数\n" +
                "     * @param limit 每次展示页数\n" +
                "     * @return 分页后的list\n" +
                "     */\n" +
                "    public static <T> List<T> page(List<T> list, Integer page, Integer limit) {\n" +
                "        return page != null && limit != null && page > 0 && limit > 0 ?\n" +
                "                list.stream()\n" +
                "                        .skip(limit * (page - 1))\n" +
                "                        .limit(limit)\n" +
                "                        .collect(Collectors.toList())\n" +
                "                : list;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 通用excel导出\n" +
                "     *\n" +
                "     * @param wb    HSSFWorkbook对象\n" +
                "     * @param sheet HSSFSheet\n" +
                "     * @param list  导出数据 如果有数据需要格式化或者其它操作，可将数据处理后再传入\n" +
                "     * @param map   key 表头 value 字段名\n" +
                "     */\n" +
                "    public static void dealCommonExcel(HSSFWorkbook wb, HSSFSheet sheet, List<Map<String, Object>> list, LinkedHashMap<String, String> map) {\n" +
                "        int length = map.size() + 1;\n" +
                "        List<String> keyList = new ArrayList<>(length);\n" +
                "        List<String> valueList = new ArrayList<>(length);\n" +
                "        map.forEach((key, value) -> {\n" +
                "            keyList.add(key);\n" +
                "            valueList.add(value);\n" +
                "        });\n" +
                "        for (int i = 0; i < length; i++) {\n" +
                "            sheet.autoSizeColumn(i);\n" +
                "            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 2);\n" +
                "        }\n" +
                "\n" +
                "        HSSFRow row = sheet.createRow(0);\n" +
                "        row.setHeightInPoints(30);\n" +
                "\n" +
                "        //标题栏样式\n" +
                "        HSSFCellStyle style = wb.createCellStyle();\n" +
                "        HSSFFont font = wb.createFont();\n" +
                "        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直\n" +
                "        font.setFontHeightInPoints((short) 12);//设置字体大小\n" +
                "        style.setFont(font);\n" +
                "        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);\n" +
                "        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框\n" +
                "        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框\n" +
                "        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框\n" +
                "        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框\n" +
                "\n" +
                "        HSSFCell cell0 = row.createCell(0);\n" +
                "        cell0.setCellValue(\"序号\");\n" +
                "        cell0.setCellStyle(style);\n" +
                "        for (int i = 1; i < length; i++) {\n" +
                "            HSSFCell cell = row.createCell(i);\n" +
                "            cell.setCellValue(keyList.get(i - 1));\n" +
                "            cell.setCellStyle(style);\n" +
                "        }\n" +
                "\n" +
                "        //添加边框\n" +
                "        HSSFCellStyle cellStyle = wb.createCellStyle();\n" +
                "        cellStyle.setWrapText(true);//自动换行\n" +
                "        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框\n" +
                "        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框\n" +
                "        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框\n" +
                "        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框\n" +
                "        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中\n" +
                "\n" +
                "        for (int i = 0; i < list.size(); i++) {\n" +
                "            row = sheet.createRow(i + 1);\n" +
                "            row.setHeightInPoints(25);\n" +
                "\n" +
                "            HSSFCell cellContent = row.createCell(0);\n" +
                "            cellContent.setCellValue(i + 1);\n" +
                "            cellContent.setCellStyle(cellStyle);\n" +
                "\n" +
                "            for (int j = 1; j < length; j++) {\n" +
                "                cellContent = row.createCell(j);\n" +
                "                cellContent.setCellValue(String.valueOf(list.get(i).get(valueList.get(j - 1))));\n" +
                "                cellContent.setCellStyle(cellStyle);\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 设置cookie\n" +
                "     *\n" +
                "     * @param response HttpServletResponse\n" +
                "     * @param name     cookie名字\n" +
                "     * @param value    cookie值\n" +
                "     * @param maxAge   cookie生命周期  以秒为单位\n" +
                "     */\n" +
                "    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {\n" +
                "        Cookie cookie = new Cookie(name, value);\n" +
                "        cookie.setPath(\"/\");\n" +
                "        if (maxAge > 0) cookie.setMaxAge(maxAge);\n" +
                "        response.addCookie(cookie);\n" +
                "    }\n" +
                "}\n";
    }

    /**
     * 存储过程返回结果工具类
     *
     * @param packageName 包名
     */
    default String getProcedureUtils(String packageName) {
        return "package " + packageName + ".util;\n" +
                "\n" +
                "import org.springframework.data.domain.Page;\n" +
                "import org.springframework.data.domain.PageImpl;\n" +
                "import org.springframework.data.domain.Pageable;\n" +
                "\n" +
                "import java.sql.ResultSet;\n" +
                "import java.sql.ResultSetMetaData;\n" +
                "import java.sql.SQLException;\n" +
                "import java.sql.Types;\n" +
                "import java.util.*;\n" +
                "\n" +
                "/**\n" +
                " * 存储过程返回结果工具类\n" +
                " *\n" +
                " * @author: TangLiang\n" +
                " * @date: 2021/4/14 14:24\n" +
                " * @since: 1.0\n" +
                " */\n" +
                "public class ProcedureUtils {\n" +
                "\n" +
                "    public static List<Map> resultHash(ResultSet rs) throws SQLException {\n" +
                "\n" +
                "        List<Map> result = new ArrayList<Map>();\n" +
                "\n" +
                "        ResultSetMetaData rsm = rs.getMetaData();\n" +
                "\n" +
                "        while (rs.next()) {\n" +
                "            Map model = new HashMap(16);\n" +
                "            for (int i = 1; i <= rsm.getColumnCount(); i++) {\n" +
                "                switch (rsm.getColumnType(i)) {\n" +
                "                    case Types.ROWID:\n" +
                "                        model.put(rsm.getColumnName(i),\n" +
                "                                rs.getString(i) == null ? \"\" : rs.getString(i));\n" +
                "                        break;\n" +
                "                    case Types.DATE:\n" +
                "                        model.put(rsm.getColumnName(i),\n" +
                "                                rs.getString(i) == null ? \"\" : rs.getString(i)\n" +
                "                                        .split(\"\\\\.\")[0]);\n" +
                "                        break;\n" +
                "                    case Types.TIMESTAMP:\n" +
                "                        model.put(rsm.getColumnName(i),\n" +
                "                                rs.getString(i) == null ? \"\" : rs.getString(i));\n" +
                "                        break;\n" +
                "                    case Types.DOUBLE:\n" +
                "                        model.put(rsm.getColumnName(i),\n" +
                "                                (rs.getString(i) == null ? \"\" : rs.getDouble(i)));\n" +
                "                        break;\n" +
                "                    case Types.BLOB:\n" +
                "                        model.put(\n" +
                "                                rsm.getColumnName(i),\n" +
                "                                (rs.getObject(i) == null ? \"\" : new String(rs\n" +
                "                                        .getBytes(i))));\n" +
                "                        break;\n" +
                "                    case Types.CLOB:\n" +
                "                        model.put(\n" +
                "                                rsm.getColumnName(i),\n" +
                "                                (rs.getObject(i) == null ? \"\" : rs.getString(i)));\n" +
                "                        break;\n" +
                "                    default:\n" +
                "                        model.put(rsm.getColumnName(i),\n" +
                "                                rs.getObject(i) == null ? \"\" : rs.getObject(i));\n" +
                "                }\n" +
                "            }\n" +
                "            result.add(model);\n" +
                "        }\n" +
                "        rs.close();\n" +
                "\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    public static List<Map> resultLinkedHash(ResultSet rs) throws SQLException {\n" +
                "\n" +
                "        List<Map> result = new ArrayList<Map>();\n" +
                "\n" +
                "        ResultSetMetaData rsm = rs.getMetaData();\n" +
                "\n" +
                "        while (rs.next()) {\n" +
                "            Map model = new LinkedHashMap();\n" +
                "            for (int i = 1; i <= rsm.getColumnCount(); i++) {\n" +
                "                switch (rsm.getColumnType(i)) {\n" +
                "                    case Types.DATE:\n" +
                "                        model.put(rsm.getColumnName(i),\n" +
                "                                rs.getString(i) == null ? \"\" : rs.getString(i)\n" +
                "                                        .split(\"\\\\.\")[0]);\n" +
                "                        break;\n" +
                "                    case Types.DOUBLE:\n" +
                "                        model.put(rsm.getColumnName(i),\n" +
                "                                (rs.getString(i) == null ? \"\" : rs.getDouble(i)));\n" +
                "                        break;\n" +
                "                    case Types.BLOB:\n" +
                "                        model.put(\n" +
                "                                rsm.getColumnName(i),\n" +
                "                                (rs.getObject(i) == null ? \"\" : new String(rs\n" +
                "                                        .getBytes(i))));\n" +
                "                        break;\n" +
                "                    default:\n" +
                "                        model.put(rsm.getColumnName(i),\n" +
                "                                rs.getObject(i) == null ? \"\" : rs.getObject(i));\n" +
                "                }\n" +
                "            }\n" +
                "            result.add(model);\n" +
                "        }\n" +
                "        rs.close();\n" +
                "\n" +
                "        return result;\n" +
                "    }\n" +
                "\n" +
                "    public static Page<Map> pageList(Pageable pageable, List<Map> list) {\n" +
                "        if (list == null) {\n" +
                "            return new PageImpl<Map>(new ArrayList<Map>(), pageable, 0);\n" +
                "        }\n" +
                "        Integer total = list.size();\n" +
                "        Integer fromIndex = pageable.getPageSize() * pageable.getPageNumber();\n" +
                "        List<Map> result = null;\n" +
                "        if (total > pageable.getPageSize() * (pageable.getPageNumber() + 1)) {\n" +
                "            result = list.subList(fromIndex, pageable.getPageSize());\n" +
                "        } else {\n" +
                "            result = list.subList(fromIndex, total);\n" +
                "        }\n" +
                "        return new PageImpl<Map>(result, pageable, total);\n" +
                "    }\n" +
                "}";
    }
}
