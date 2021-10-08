package com.newangels.gen.service.impl.dataBaseDocument;

import com.newangels.gen.service.DataBaseDocumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: TangLiang
 * @date: 2021/10/8 13:47
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DataBaseDocumentServiceImpl implements DataBaseDocumentService {
}
