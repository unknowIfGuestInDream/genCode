package com.newangels.gen.util;

import com.newangels.gen.enums.JavaClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author: 唐 亮
 * @date: 2022/1/3 18:43
 * @since: 1.0
 */
@SpringBootTest
public class UriComponentsTest {

    //构造一个简单的URI
    @Test
    public void UriComponents1(){
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.github.com").path("/constructing-uri")
                .queryParam("name", "tom")
                .build();
        System.out.println(uriComponents.toUriString());
    }

    //构造一个编码的URI
    @Test
    public void UriComponents2(){
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.github.com").path("/constructing uri").build().encode();
        System.out.println(uriComponents.toUriString());
    }


    //通过模板构造URI
    @Test
    public void UriComponents3(){
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.github.com").path("/&#123;path-name&#125;")
                .query("name=&#123;keyword&#125;")
                .buildAndExpand("constructing-uri", "tomcat");
        System.out.println(uriComponents.toUriString());
    }


    //从已有的URI中获取信息
    @Test
    public void UriComponents4(){
        // 使用fromUriString()方法，便可以把一个字符串URI转换为UriComponents对象，
        // 并且可以通过getQueryParams()方法取出参数。
        UriComponents result = UriComponentsBuilder
                .fromUriString("https://www.github.com/constructing-uri?name=tomcat").build();
        MultiValueMap<String, String> expectedQueryParams = new LinkedMultiValueMap<>(1);
        expectedQueryParams.add("name", "tomcat");
        System.out.println(result.getQueryParams());
    }
}
