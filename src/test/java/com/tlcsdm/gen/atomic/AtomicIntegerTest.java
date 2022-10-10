package com.tlcsdm.gen.atomic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger测试
 *
 * @author: TangLiang
 * @date: 2021/9/13 14:15
 * @since: 1.0
 */
@SpringBootTest
public class AtomicIntegerTest {

	@Test
	public void test1() {
		Map<String, AtomicInteger> map = new HashMap<>();
		for (int i = 0; i < 3; i++) {
			if (map.get("sss") == null) {
				map.put("sss", new AtomicInteger(0));
				continue;
			}
			System.out.println(map.get("sss").incrementAndGet());
		}
	}

}
