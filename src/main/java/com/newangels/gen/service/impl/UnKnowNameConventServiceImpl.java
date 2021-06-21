package com.newangels.gen.service.impl;

import cn.hutool.core.util.StrUtil;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.NameConventType;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 未知命名规范
 * 默认返回过程后缀名
 *
 * @author: TangLiang
 * @date: 2021/6/21 10:34
 * @since: 1.0
 */
@Service
public class UnKnowNameConventServiceImpl implements NameConventService {
    @Override
    public String getName(String procedureName) {
        return procedureName.substring(procedureName.lastIndexOf("_") + 1).toLowerCase();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NameConventFactory.register(NameConventType.UNKNOW, this);
    }
}
