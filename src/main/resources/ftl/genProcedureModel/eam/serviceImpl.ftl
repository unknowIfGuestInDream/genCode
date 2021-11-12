package ${package}.service.impl;

import ${package}.repository.${module}Repository;
import ${package}.service.${module}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author: ${author}
 * @date: ${date}
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ${module}ServiceImpl implements ${module}Service {
    @Autowired
    private ${module}Repository ${module?uncap_first}Repository;
${serviceImplMethod}
}