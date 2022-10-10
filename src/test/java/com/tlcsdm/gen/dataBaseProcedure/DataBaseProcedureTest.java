package com.tlcsdm.gen.dataBaseProcedure;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * @author: TangLiang
 * @date: 2021/6/19 9:09
 * @since: 1.0
 */
@SpringBootTest
public class DataBaseProcedureTest {

	@Test
	public void Tes1() {
		Map<String, Object> result = new HashMap<>(16);
		List<String> list = new ArrayList<>(
				Arrays.asList("get", "select", "selectWithPage", "insert", "update", "save", "delete"));
		result.put("get", "get");
		result.put("select", "select");
		result.put("selectWithPage", "selectWithPage");
		result.put("insert", "insert");
		result.put("update", "update");
		result.put("list", result.keySet());
		System.out.println(result.get("list"));
		System.out.println("=======================");
		result.put("list", list);
		System.out.println(result.get("list"));
	}

}
