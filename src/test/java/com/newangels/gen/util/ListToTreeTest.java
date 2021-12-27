package com.newangels.gen.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 唐 亮
 * @date: 2021/12/27 21:58
 * @since: 1.0
 */
@SpringBootTest
public class ListToTreeTest {
    @Autowired
    private ObjectMapper objectMapper;
    public static List<Node> nodeList;

    @Data
    @Accessors(chain = true)
    public static class Node {
        Integer id;
        Integer pid;
        List<Node> children;
    }

    @Test
    public void listToTree() throws JsonProcessingException {
        List<Node> nodes = ListToTreeUtil.ListToTree(nodeList, Node::getId, Node::getPid, Node::getChildren, Node::setChildren);
        //List<Node> nodes = ListToTreeUtil.ListToTree(nodeList, (n) -> n.getPid() == null || n.getPid().equals(0), Node::getId, Node::getPid, Node::getChildren, Node::setChildren);
        System.out.println(objectMapper.writeValueAsString(nodes));
    }

    @BeforeAll
    public static void before() {
        nodeList = new ArrayList<>();
        nodeList.add(new Node().setId(0));
        nodeList.add(new Node().setId(1));
        nodeList.add(new Node().setId(2).setPid(0));
        nodeList.add(new Node().setId(3).setPid(0));
        nodeList.add(new Node().setId(4).setPid(1));
        nodeList.add(new Node().setId(5).setPid(1));
        nodeList.add(new Node().setId(6).setPid(2));
        nodeList.add(new Node().setId(7).setPid(2));
        nodeList.add(new Node().setId(8).setPid(3));
        nodeList.add(new Node().setId(9).setPid(3));
        nodeList.add(new Node().setId(10).setPid(4));
        nodeList.add(new Node().setId(11).setPid(4));
        nodeList.add(new Node().setId(12).setPid(5));
    }

}
