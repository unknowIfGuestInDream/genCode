package ${package}.base;

import org.apache.commons.lang3.StringUtils;
import lombok.Cleanup;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: TangLiang
 * @date: 2021/4/14 15:16
 * @since: 1.0
 */
public class BaseUtils {

    /**
     * 最大上传文件大小
     */
    public static long MAX_UPLOAD_SIZE = 1048576 * 30;
    /**
     * 返回结果集key值
     */
    public static String DATA = "data";

    /**
     * 类不能实例化
     */
    private BaseUtils() {
    }

    /**
     * 不同浏览器将下载文件名处理为中文
     *
     * @param request HttpServletRequest对象
     * @param s       文件名
     * @return 文件名
     * @throws UnsupportedEncodingException 不支持的解码方式
     */
    public static String getFormatString(HttpServletRequest request, String s) throws UnsupportedEncodingException {
        String filename = s;
        String userAgent = request.getHeader("User-Agent").toUpperCase();
        if (userAgent.indexOf("FIREFOX") > 0) {
            filename = new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1); // firefox浏览器
        } else if (userAgent.indexOf("MSIE") > 0) {
            filename = URLEncoder.encode(s, "UTF-8");// IE浏览器
        } else if (userAgent.indexOf("CHROME") > 0) {
            filename = new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);// 谷歌
        }
        return filename;
    }

    /**
     * 下载文件
     *
     * @param inputStream 文件输入流
     * @param fileName    文件名
     * @param request     request
     * @param response    response
     */
    public static void download(InputStream inputStream, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileName = getFormatString(request, fileName);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);// 下载模式
        @Cleanup ServletOutputStream out = response.getOutputStream();
        byte[] content = new byte[65535];
        int length = 0;
        while ((length = inputStream.read(content)) != -1) {
            out.write(content, 0, length);
            out.flush();
        }
    }

    /**
     * 下载文件
     *
     * @param wb       excel对象
     * @param fileName 文件名
     * @param request  request
     * @param response response
     */
    public static void download(Workbook wb, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileName = getFormatString(request, fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
    }

    /**
     * 流转字节数组
     *
     * @param inputStream 流
     * @return 流转字节数组
     * @throws Exception IOException
     */
    public static byte[] inputStreamToBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] content = new byte[65535];
        int length = 0;
        while ((length = inputStream.read(content)) != -1) {
            baos.write(content, 0, length);
        }

        return baos.toByteArray();
    }

    /**
     * 流转字符串
     *
     * @param inputStream 流
     * @return 流转字符串
     * @throws Exception IOException
     */
    public static String inputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] content = new byte[65535];
        int length = 0;
        while ((length = inputStream.read(content)) != -1) {
            baos.write(content, 0, length);
        }

        return baos.toString();
    }

    /**
     * 深度克隆
     *
     * @param object
     * @return
     */
    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            if (object != null) {
                objectOutputStream.writeObject(object);
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            return objectInputStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * transfer xml to bean
     *
     * @param xml 待转化的xml字符串
     * @param c   转化后的类
     * @param <T> 转化后类类型
     * @return 转化后的类对象
     */
    public static <T> T xmlToBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String convertExcelToHtmlByWb(Workbook workbook) {
        return getExcelHtml(workbook, true);
    }

    /**
     * @功能描述 POI 读取 Excel 转 HTML 支持 2003xls 和 2007xlsx 版本 包含样式
     * @author Devil
     * @创建时间 2015/4/19 21:34
     */
    private static String getExcelHtml(Workbook wb, boolean isWithStyle) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);// 获取第一个Sheet的内容
            Map<String, List<Picture>> sheetPictureMap = getSheetPictrues(sheet, wb);// 获取excel中的图片

            int lastRowNum = sheet.getLastRowNum();
            Map<String, String> map[] = getRowSpanColSpanMap(sheet);
            sb.append("<table style='border-collapse:collapse;' width='100%'>");
            Row row = null; // 兼容
            Cell cell = null; // 兼容

            for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
                row = sheet.getRow(rowNum);
                if (row == null) {
                    sb.append("<tr><td > &nbsp;</td></tr>");
                    continue;
                }
                sb.append("<tr>");
                int lastColNum = row.getLastCellNum();
                for (int colNum = 0; colNum < lastColNum; colNum++) {
                    cell = row.getCell(colNum);
                    if (cell == null) { // 特殊情况 空白的单元格会返回null
                        sb.append("<td align='left' valign='center' style='border: 1px solid rgb(0, 0, 0); width: 2304px; font-size: 110%; font-weight: 400;'>&nbsp;</td>");
                        continue;
                    }

                    String pictureKey = rowNum + "," + colNum;
                    String pictureHtml = "";
                    Boolean hasPicture = false;// 判断该行是否存在图片
                    if (sheetPictureMap.containsKey(pictureKey)) {
                        List<Picture> pictureList = sheetPictureMap.get(pictureKey);
                        for (Picture picture : pictureList) {
                            //pictureHtml += "<img src=data:image/jpeg;base64," + new String(Base64.encodeBase64(picture.getPictureData().getData())) + " oncontextmenu=\"return false;\" ondragstart=\"return false;\"style=\"height:" + picture.getImageDimension().getHeight() + "px;width:" + picture.getImageDimension().getHeight() + "px;position:absolute;top:" + picture.getClientAnchor().getDy1() / 12700 + "px;left:" + picture.getClientAnchor().getDx1() / 12700 + "px\">";
                        }
                        hasPicture = true;
                    }

                    String stringValue = getCellValue(cell);
                    if (map[0].containsKey(rowNum + "," + colNum)) {
                        String pointString = map[0].get(rowNum + "," + colNum);
                        map[0].remove(rowNum + "," + colNum);
                        int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
                        int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
                        int rowSpan = bottomeRow - rowNum + 1;
                        int colSpan = bottomeCol - colNum + 1;
                        sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' ");
                    } else if (map[1].containsKey(rowNum + "," + colNum)) {
                        map[1].remove(rowNum + "," + colNum);
                        continue;
                    } else {
                        sb.append("<td ");
                    }

                    // 判断是否需要样式
                    if (isWithStyle) {
                        dealExcelStyle(wb, sheet, cell, sb, hasPicture);// 处理单元格样式
                    }

                    sb.append(">");
                    if (sheetPictureMap.containsKey(pictureKey)) {
                        sb.append(pictureHtml);
                    }
                    if ((stringValue == null || "".equals(stringValue.trim())) && !row.getZeroHeight()) {
                        sb.append(" &nbsp; ");
                    } else {
                        // 将ascii码为160的空格转换为html下的空格（&nbsp;）
                        sb.append(stringValue.replace(String.valueOf((char) 160), "&nbsp;"));
                    }
                    sb.append("</td>");
                }
                sb.append("</tr>");
            }

            sb.append("</table>");
            sb.append("<br /><br />");
        }

        return sb.toString();
    }

    /**
     * 获取Excel图片公共方法
     *
     * @param sheet    当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（1,1）String，value:图片流Picture
     */
    public static Map<String, List<Picture>> getSheetPictrues(Sheet sheet, Workbook workbook) {
        if (workbook instanceof XSSFWorkbook) {
            return getSheetPictureMap2007((XSSFSheet) sheet);
        } else if (workbook instanceof HSSFWorkbook) {
            return getSheetPictrues2003((HSSFSheet) sheet);
        } else {
            return null;
        }
    }

    /**
     * 获取Excel2007图片
     *
     * @param sheet 当前sheet对象
     * @return Map key:图片单元格索引（1,1）String，value:图片流Picture
     */
    private static Map<String, List<Picture>> getSheetPictureMap2007(XSSFSheet sheet) {
        Map<String, List<Picture>> sheetPictureMap = new HashMap<String, List<Picture>>();

        for (POIXMLDocumentPart documentPart : sheet.getRelations()) {
            if (documentPart instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) documentPart;
                List<XSSFShape> shapeList = drawing.getShapes();
                for (XSSFShape shape : shapeList) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    CTMarker ctMarker = anchor.getFrom();
                    String pictureKey = ctMarker.getRow() + "," + ctMarker.getCol();
                    List<Picture> pictureList = sheetPictureMap.get(pictureKey);
                    if (pictureList == null) {
                        pictureList = new ArrayList<>();
                        sheetPictureMap.put(pictureKey, pictureList);
                    }
                    pictureList.add(picture);
                }
            }
        }

        return sheetPictureMap;
    }

    /**
     * 获取Excel2003图片
     *
     * @param sheet 当前sheet对象
     * @return Map key:图片单元格索引（1,1）String，value:图片流Picture
     */
    private static Map<String, List<Picture>> getSheetPictrues2003(HSSFSheet sheet) {
        Map<String, List<Picture>> sheetPictureMap = new HashMap<String, List<Picture>>();

        // 处理sheet中的图形
        HSSFPatriarch hssfPatriarch = sheet.getDrawingPatriarch();
        if (hssfPatriarch != null) {
            // 获取所有的形状图
            List<HSSFShape> shapes = hssfPatriarch.getChildren();
            for (HSSFShape sp : shapes) {
                if (sp instanceof HSSFPicture) {
                    // 转换
                    HSSFPicture picture = (HSSFPicture) sp;
                    // 图形定位
                    if (picture.getAnchor() instanceof HSSFClientAnchor) {
                        HSSFClientAnchor anchor = (HSSFClientAnchor) picture.getAnchor();
                        String pictureKey = String.valueOf(anchor.getRow1()) + "," + String.valueOf(anchor.getCol1());
                        List<Picture> pictureList = sheetPictureMap.get(pictureKey);
                        if (pictureList == null) {
                            pictureList = new ArrayList<>();
                            sheetPictureMap.put(pictureKey, pictureList);
                        }
                        pictureList.add(picture);
                    }
                }
            }
        }
        return sheetPictureMap;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {
        Map<String, String> map0 = new HashMap<String, String>();
        Map<String, String> map1 = new HashMap<String, String>();
        int mergedNum = sheet.getNumMergedRegions();
        CellRangeAddress range = null;
        for (int i = 0; i < mergedNum; i++) {
            range = sheet.getMergedRegion(i);
            int topRow = range.getFirstRow();
            int topCol = range.getFirstColumn();
            int bottomRow = range.getLastRow();
            int bottomCol = range.getLastColumn();
            map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
            int tempRow = topRow;
            while (tempRow <= bottomRow) {
                int tempCol = topCol;
                while (tempCol <= bottomCol) {
                    map1.put(tempRow + "," + tempCol, "");
                    tempCol++;
                }
                tempRow++;
            }
            map1.remove(topRow + "," + topCol);
        }
        Map[] map = {map0, map1};
        return map;
    }

    /**
     * 获取表格单元格Cell内容
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
        String result = new String();
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:// 数字类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#.####");
                    }
                    result = format.format(value);
                }
                break;
            case Cell.CELL_TYPE_STRING:// String类型
                result = cell.getRichStringCellValue().toString();
                break;
            case Cell.CELL_TYPE_BLANK:
                result = "";
                break;
            default:
                result = "";
                break;
        }
        return result;
    }

    /**
     * 处理表格样式
     *
     * @param wb
     * @param sheet
     * @param cell
     * @param sb
     */
    private static void dealExcelStyle(Workbook wb, Sheet sheet, Cell cell, StringBuffer sb, Boolean hasPicture) {
        boolean rowInvisible = sheet.getRow(cell.getRowIndex()).getZeroHeight();

        int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
        int columnHeight = (int) (sheet.getRow(cell.getRowIndex()).getHeight() / 15.625);
        if (rowInvisible) {
            columnHeight = 0;
        }

        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle != null) {
            short alignment = cellStyle.getAlignment();
            sb.append("align='" + convertAlignToHtml(alignment) + "' ");// 单元格内容的水平对齐方式
            short verticalAlignment = cellStyle.getVerticalAlignment();
            sb.append("valign='" + convertVerticalAlignToHtml(verticalAlignment) + "' ");// 单元格中内容的垂直排列方式

            if (wb instanceof XSSFWorkbook) {
                XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont();
                short boldWeight = xf.getBoldweight();
                sb.append("style='");
                sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                sb.append("font-size: " + xf.getFontHeight() / 1.5 + "%;"); // 字体大小
                sb.append("width:" + columnWidth + "px;");
                sb.append("height:" + columnHeight + "px;");
                if (hasPicture) {
                    sb.append("height:" + columnHeight + "px;position:relative;");
                }

                XSSFColor xc = xf.getXSSFColor();
                if (xc != null && !"".equals(xc)) {
                    sb.append("color:#" + xc.getARGBHex().substring(2) + ";"); // 字体颜色
                }

                XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
                if (bgColor != null && !"".equals(bgColor)) {
                    sb.append("background-color:#" + bgColor.getARGBHex().substring(2) + ";"); // 背景颜色
                }
                if (!rowInvisible) {
                    sb.append(getBorderStyle(0, cellStyle.getBorderTop(), ((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
                    sb.append(getBorderStyle(1, cellStyle.getBorderRight(), ((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
                    sb.append(getBorderStyle(2, cellStyle.getBorderBottom(), ((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
                    sb.append(getBorderStyle(3, cellStyle.getBorderLeft(), ((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));
                }
            } else if (wb instanceof HSSFWorkbook) {
                HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
                short boldWeight = hf.getBoldweight();
                short fontColor = hf.getColor();
                sb.append("style='");
                HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式
                HSSFColor hc = palette.getColor(fontColor);
                sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
                sb.append("font-size: " + hf.getFontHeight() / 1.5 + "%;"); // 字体大小
                String fontColorStr = convertToStardColor(hc);
                if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
                    sb.append("color:" + fontColorStr + ";"); // 字体颜色
                }
                sb.append("width:" + columnWidth + "px;");
                sb.append("height:" + columnHeight + "px;");
                if (hasPicture) {
                    sb.append("height:" + columnHeight + "px;position:relative;");
                }
                short bgColor = cellStyle.getFillForegroundColor();
                hc = palette.getColor(bgColor);
                String bgColorStr = convertToStardColor(hc);
                if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
                    sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
                }
                if (!rowInvisible) {
                    sb.append(getBorderStyle(palette, 0, cellStyle.getBorderTop(), cellStyle.getTopBorderColor()));
                    sb.append(getBorderStyle(palette, 1, cellStyle.getBorderRight(), cellStyle.getRightBorderColor()));
                    sb.append(getBorderStyle(palette, 3, cellStyle.getBorderLeft(), cellStyle.getLeftBorderColor()));
                    sb.append(getBorderStyle(palette, 2, cellStyle.getBorderBottom(), cellStyle.getBottomBorderColor()));
                }
            }

            sb.append("' ");
        }
    }

    private static String[] bordesr = {"border-top:", "border-right:", "border-bottom:", "border-left:"};
    private static String[] borderStyles = {"solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid", "solid", "solid", "solid", "solid"};

    private static String getBorderStyle(HSSFPalette palette, int b, short s, short t) {
        if (s == 0) {
            return bordesr[b] + borderStyles[s] + "#d0d7e5 0px;";
        }

        String borderColorStr = convertToStardColor(palette.getColor(t));
        borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr;
        return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
    }

    private static String getBorderStyle(int b, short s, XSSFColor xc) {
        if (s == 0) {
            return bordesr[b] + borderStyles[s] + "#d0d7e5 0px;";
        }

        if (xc != null && !"".equals(xc)) {
            String borderColorStr = xc.getARGBHex();// t.getARGBHex();
            borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr.substring(2);
            return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
        }

        return "";
    }

    //整理数据
    public static List<Map<String, Object>> listMapper(List<Map<String, Object>> list) {
        List data = new ArrayList();
        for (Object o : list) {
            data.add(modelMapper((Map<String, Object>) o));
        }
        return data;
    }

    //数据转换gantt
    public static Map<String, Object> modelMapper(Map<String, Object> o) {
        Map<String, Object> task = new HashMap();
        task.put("UID", o.get("V_GUID"));                        //唯一标识符
        task.put("ID", o.get("ID"));                          //序号
        task.put("Name", o.get("V_PORJECT_NAME"));//工程名称
        task.put("Start", o.get("V_BDATE"));//开工时间
        task.put("Finish", o.get("V_EDATE"));//竣工时间
        task.put("Duration", o.get("V_Duration"));//工期
        task.put("PercentComplete", o.get("V_PROGRESS"));//进度
        task.put("V_FLAG", o.get("V_FLAG"));//计划类型
        task.put("V_CONTENT", o.get("V_CONTENT"));//维修内容
        task.put("ParentTaskUID", o.get("V_GUID_UP"));//父节点

        return task;
    }

    //进度计算
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //不同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //同一年
        {
            return day2 - day1;
        }
    }

    /**
     * 单元格内容的水平对齐方式
     *
     * @param alignment
     * @return
     */
    private static String convertAlignToHtml(short alignment) {
        String align = "left";
        switch (alignment) {
            case CellStyle.ALIGN_LEFT:
                align = "left";
                break;
            case CellStyle.ALIGN_CENTER:
                align = "center";
                break;
            case CellStyle.ALIGN_RIGHT:
                align = "right";
                break;
            default:
                break;
        }
        return align;
    }

    /**
     * 单元格中内容的垂直排列方式
     *
     * @param verticalAlignment
     * @return
     */
    private static String convertVerticalAlignToHtml(short verticalAlignment) {
        String valign = "middle";
        switch (verticalAlignment) {
            case CellStyle.VERTICAL_BOTTOM:
                valign = "bottom";
                break;
            case CellStyle.VERTICAL_CENTER:
                valign = "center";
                break;
            case CellStyle.VERTICAL_TOP:
                valign = "top";
                break;
            default:
                break;
        }
        return valign;
    }

    private static String convertToStardColor(HSSFColor hc) {
        StringBuffer sb = new StringBuffer("");
        if (hc != null) {
            if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
                return null;
            }
            sb.append("#");
            for (int i = 0; i < hc.getTriplet().length; i++) {
                sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
            }
        }

        return sb.toString();
    }

    /**
     * 个位数填充0
     *
     * @param str 需要填充的字符串
     * @return 填充后结果
     */
    public static String fillWithZero(String str) {
        if (str != null && str.length() < 2) {
            return "0" + str;
        }
        return str;
    }

    /**
     * 获得UUID
     *
     * @return UUID
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 返回成功信息(用于加载对象)
     *
     * @param result 加载对象数据
     * @return java.util.Map
     */
    public static Map<String, Object> loadSuccess(Map<String, Object> result) {
        Map<String, Object> data = new HashMap<>(4);
        data.put(DATA, result);
        return success(data);
    }

    /**
     * 返回成功信息(用于存储过程方式的结果返回)
     *
     * @param result 数据集
     * @return java.util.Map
     */
    public static Map<String, Object> success(Map<String, Object> result) {
        result.put("success", true);
        return result;
    }

    /**
     * 返回成功信息(用于JdbcTemplate的结果返回)
     *
     * @param list 数据集
     * @return java.util.Map
     */
    public static <T> Map<String, Object> success(List<T> list) {
        Map<String, Object> result = new HashMap<>(4);
        result.put(DATA, list);
        result.put("success", true);
        return result;
    }

    /**
     * 返回成功信息(用于JdbcTemplate的结果返回)
     *
     * @param list  数据集
     * @param total 数据集总数
     * @return java.util.Map
     */
     public static <T> Map<String, Object> success(List<T> list, int total) {
         Map<String, Object> result = success(list);
         result.put("total", total);
         return result;
    }

    /**
     * 返回成功信息(用于存储过程方式的结果返回)
     *
     * @return java.util.Map
     */
    public static Map<String, Object> success() {
        return Collections.singletonMap("success", true);
    }

    /**
     * 返回失败信息(用于存储过程方式的结果返回)
     *
     * @param message 错误信息
     * @return java.util.Map
     */
    public static Map<String, Object> failed(String message) {
        Map<String, Object> result = new HashMap<>(4);
        result.put("success", false);
        result.put("message", message);
        return result;
    }

    /**
     * 返回失败信息(用于存储过程方式的结果返回)
     *
     * @param result  数据集
     * @param message 错误信息
     * @return java.util.Map
     */
    public static Map<String, Object> failed(Map<String, Object> result, String message) {
        result.put("success", false);
        result.put("message", message);
        return result;
    }

    /**
     * 获取请求客户端ip地址
     *
     * @param request request
     * @return 客户端ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            return "127.0.0.1";
        }
        //解决经过nginx转发, 配置了proxy_set_header x-forwarded-for $proxy_add_x_forwarded_for;带来的多ip的情况
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }

        return ip;
    }

    /**
     * 获取请求url路径
     *
     * @param request request
     * @return url路径
     */
    public static String getUrl(HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getServletPath();
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }

        return url;
    }

    /**
     * 主机名
     *
     * @return 主机名
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "未知";
    }

    /**
     * 获取cookie值
     *
     * @param key     cookie键
     * @param request request
     * @return cookie值
     */
    public static String getCookieValue(String key, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    /**
     * 分页
     *
     * @param list  需要分页的list
     * @param page  当前页数
     * @param limit 每次展示页数
     * @return 分页后的list
     */
    public static <T> List<T> page(List<T> list, Integer page, Integer limit) {
        return page != null && limit != null && page > 0 && limit > 0 ?
                list.stream()
                        .skip(limit * (page - 1))
                        .limit(limit)
                        .collect(Collectors.toList())
                : list;
    }

    /**
     * 通用excel导出
     *
     * @param wb    HSSFWorkbook对象
     * @param sheet HSSFSheet
     * @param list  导出数据 如果有数据需要格式化或者其它操作，可将数据处理后再传入
     * @param map   key 表头 value 字段名
     */
    public static void dealCommonExcel(HSSFWorkbook wb, HSSFSheet sheet, List<Map<String, Object>> list, LinkedHashMap<String, String> map) {
        int length = map.size() + 1;
        List<String> keyList = new ArrayList<>(length);
        List<String> valueList = new ArrayList<>(length);
        map.forEach((key, value) -> {
            keyList.add(key);
            valueList.add(value);
        });
        for (int i = 0; i < length; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 2);
        }

        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);

        //标题栏样式
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        font.setFontHeightInPoints((short) 12);//设置字体大小
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        HSSFCell cell0 = row.createCell(0);
        cell0.setCellValue("序号");
        cell0.setCellStyle(style);
        for (int i = 1; i < length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(keyList.get(i - 1));
            cell.setCellStyle(style);
        }

        //添加边框
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);//自动换行
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            row.setHeightInPoints(25);

            HSSFCell cellContent = row.createCell(0);
            cellContent.setCellValue(i + 1);
            cellContent.setCellStyle(cellStyle);

            for (int j = 1; j < length; j++) {
                cellContent = row.createCell(j);
                cellContent.setCellValue(valueOf(list.get(i).get(valueList.get(j - 1))));
                cellContent.setCellStyle(cellStyle);
            }
        }
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 设置cookie
     *
     * @param response HttpServletResponse
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 根据容量获取map初始大小
     * 参考JDK8中putAll方法中的实现以及
     * guava的newHashMapWithExpectedSize方法
     *
     * @param expectedSize 容量大小
     */
    public static int newHashMapWithExpectedSize(int expectedSize) {
        if (expectedSize < 3) {
            return 4;
        } else {
            return expectedSize < 1073741824 ? (int) ((float) expectedSize / 0.75F + 1.0F) : 2147483647;
        }
    }

    /**
     * 将java.util.Date对象转化为String字符串
     *
     * @param date      要格式的java.util.Date对象
     * @param strFormat 输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
     * @return 表示日期的字符串
     */
    public static String dateToStr(Date date, String strFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
        String str = simpleDateFormat.format(date);
        return str;
    }

    /**
     * 从map中取一列数据
     */
    public static <T> List<T> collect(List<Map<String, Object>> data, String key) {
        List<T> result = new ArrayList<>();
        for (Map<String, Object> datum : data) {
            result.add((T) datum.get(key));
        }
        return result;
    }

}
