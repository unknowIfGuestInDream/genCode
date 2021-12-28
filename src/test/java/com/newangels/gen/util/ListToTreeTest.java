package com.newangels.gen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 唐 亮
 * @date: 2021/12/27 21:58
 * @since: 1.0
 */
@SpringBootTest
public class ListToTreeTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listToTree() throws JsonProcessingException {
        List<Map<String, Object>> list = new ArrayList<>();
        init(list);
        List<Map<String, Object>> children = ListToTreeUtil.listToTree(list, (m) -> "-1".equals(m.get("PID")), "ID", "PID", true);
        List<Map<String, Object>> children1 = ListToTreeUtil.listToTree(list, (m) -> "-1".equals(m.get("PID")), (m) -> m.get("ID"), (n) -> n.get("PID"), true);
        System.out.println(objectMapper.writeValueAsString(list));
        System.out.println(objectMapper.writeValueAsString(children));
        System.out.println(objectMapper.writeValueAsString(children1));
    }

    public void init(List<Map<String, Object>> list) {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("ID", "1");
        map1.put("PID", "-1");
        list.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("ID", "2");
        map2.put("PID", "1");
        list.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("ID", "3");
        map3.put("PID", "2");
        list.add(map3);
        Map<String, Object> ma4 = new HashMap<>();
        ma4.put("ID", "4");
        ma4.put("PID", "2");
        list.add(ma4);
    }

}
