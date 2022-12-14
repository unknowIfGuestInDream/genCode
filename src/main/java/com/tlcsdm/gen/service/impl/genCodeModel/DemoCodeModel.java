package com.tlcsdm.gen.service.impl.genCodeModel;

import com.tlcsdm.gen.enums.GenCodeModelType;
import com.tlcsdm.gen.factory.AbstractGenCodeModelFactory;
import com.tlcsdm.gen.service.AbstractGenCodeModel;
import org.springframework.stereotype.Service;

/**
 * 大连demo模版
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:24
 * @since: 1.0
 */
@Service
public class DemoCodeModel extends AbstractGenCodeModel {

	@Override
	protected String getFtlPackageName() {
		return "demo";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AbstractGenCodeModelFactory.register(GenCodeModelType.DEMO, this);
	}

}
