package com.tlcsdm.gen.service.impl.genProcedureModel;

import com.tlcsdm.gen.enums.GenProcedureModelType;
import com.tlcsdm.gen.factory.AbstractGenProcedureModelFactory;
import com.tlcsdm.gen.service.AbstractGenProcedureModel;
import com.tlcsdm.gen.service.NameConventService;
import org.springframework.stereotype.Service;

/**
 * demo项目风格代码
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:24
 * @since: 1.0
 */
@Service
public class OldProcedureModel extends AbstractGenProcedureModel {

	@Override
	protected String getFtlPackageName() {
		return "old";
	}

	@Override
	protected String getMappingType(String procedureName, NameConventService nameConvent) {
		return "RequestMapping";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AbstractGenProcedureModelFactory.register(GenProcedureModelType.DEMO, this);
	}

}
