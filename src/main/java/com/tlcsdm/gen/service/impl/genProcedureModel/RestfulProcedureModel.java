package com.tlcsdm.gen.service.impl.genProcedureModel;

import com.tlcsdm.gen.enums.GenProcedureModelType;
import com.tlcsdm.gen.factory.AbstractGenProcedureModelFactory;
import com.tlcsdm.gen.service.AbstractGenProcedureModel;
import com.tlcsdm.gen.service.NameConventService;
import org.springframework.stereotype.Service;

/**
 * RestFul风格代码
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:24
 * @since: 1.0
 */
@Service
public class RestfulProcedureModel extends AbstractGenProcedureModel {

	@Override
	protected String getFtlPackageName() {
		return "restful";
	}

	@Override
	protected String getMappingType(String procedureName, NameConventService nameConvent) {
		if ("select".equals(nameConvent.getName(procedureName)) || "load".equals(nameConvent.getName(procedureName))) {
			return "GetMapping";
		}
		return "PostMapping";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		AbstractGenProcedureModelFactory.register(GenProcedureModelType.RESTFUL, this);
	}

}
