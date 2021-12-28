package com.newangels.gen.base;

import com.alibaba.druid.util.StringUtils;
import lombok.Cleanup;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 常用方法封装
 *
 * @author: TangLiang
 * @date: 2021/4/14 15:16
 * @since: 1.0
 */
public class BaseUtils {

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
     * 返回成功信息(用于存储过程方式的结果返回)
     *
     * @param obj 数据集
     * @return java.util.Map
     */
    public static Map<String, Object> success(Object obj) {
        Map<String, Object> result = new HashMap<>(4);
        result.put("success", true);
        result.put("result", obj);
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
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 首字母小写
     *
     * @param string
     * @return
     */
    public static String toLowerCase4Index(String string) {
        if (Character.isLowerCase(string.charAt(0))) {
            return string;
        }

        char[] chars = string.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String toUpperCase4Index(String string) {
        char[] chars = string.toCharArray();
        chars[0] = toUpperCase(chars[0]);
        return String.valueOf(chars);
    }

    /**
     * 字符转成大写
     *
     * @param chars
     * @return
     */
    public static char toUpperCase(char chars) {
        if (97 <= chars && chars <= 122) {
            chars ^= 32;
        }
        return chars;
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
     * 深度克隆
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
}
