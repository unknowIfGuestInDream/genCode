package com.tlcsdm.gen.math;

import com.tlcsdm.gen.base.BaseUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: TangLiang
 * @date: 2021/12/2 16:10
 * @since: 1.0
 */
@SpringBootTest
public class MathTest {

	@Test
	public void test1() {
		// System.out.println(Math.log(1/0.75f) / Math.log(2));
		// System.out.println(Math.log(100/0.75f) / Math.log(2));
		System.out.println(Math.log(13 / 0.75f) / Math.log(2));
		// System.out.println(Math.floor(Math.log(127/0.75f) / Math.log(2)));
		// System.out.println(Math.log(3/0.75f) / Math.log(2));
		// System.out.println(Math.log(7/0.75f) / Math.log(2));
		// Math.floor(11.7)
		System.out.println(Math.pow(2, 4));
		System.out.println(BaseUtils.newHashMapWithExpectedSize(3));
		System.out.println(BaseUtils.newHashMapWithExpectedSize(7));
		System.out.println(BaseUtils.newHashMapWithExpectedSize(13));
	}

	@Test
	public void test2() {
		for (int i = 0; i < 10; i++) {
			System.out.println(i % 2 == 0);
		}
	}

}
