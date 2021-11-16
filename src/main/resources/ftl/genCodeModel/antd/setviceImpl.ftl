package ${package}.service.impl;

import ${package}.base.BaseSqlCriteria;
import ${package}.base.BaseUtils;
import ${package}.service.${module}Service;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ${author}
 * @date ${date}
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ${module}ServiceImpl implements ${module}Service {
    private final JdbcTemplate ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate;

    @Override
    public Map<String, Object> load${module}(${loadInParams}) {
        Map<String, Object> paramMap = new HashMap<>(8);
        String sql = "";
        paramMap.put("BATCH_CODE_", BATCH_CODE_);
        paramMap.put("FTY_CODE_", FTY_CODE_);
        paramMap.put("YWFW_CODE_", YWFW_CODE_);
        paramMap.put("ITEM_CODE_", ITEM_CODE_);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(annualJdbcTemplate);
        List<Map<String, Object>> result namedParameterJdbcTemplate.queryForList(sql, paramMap);
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return new HashMap<>(4);
        }
    }

    @Override
    public List<Map<String, Object>> select${module}(${selInParams}, Integer page, Integer limit) {
        BaseSqlCriteria baseSqlCriteria = buildSqlCriteriaProductSale(false, ${selSqlParams});
        String sql = baseSqlCriteria.getSql();
        Map<String, Object> paramMap = baseSqlCriteria.getParamMap();

        if (page != null && limit != null && limit > 0) {// 分页
            int start = (page - 1) * limit + 1;
            int end = page * limit;
            sql = "select * from (select FULLTABLE.*, ROWNUM RN from (" + sql + ") FULLTABLE where ROWNUM <= " + end + ") where RN >= " + start;
        }

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate);
        return namedParameterJdbcTemplate.queryForList(sql, paramMap);
    }

    @Override
    public int count${module}(${selInParams}) {
        BaseSqlCriteria baseSqlCriteria = buildSqlCriteriaProductSale(true, ${selSqlParams});// 根据查询条件组装总数统计SQL语句
        String sql = baseSqlCriteria.getSql();
        Map<String, Object> paramMap = baseSqlCriteria.getParamMap();

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate);
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    @Override
    public int insert${module}(${insInParams}) {
        String sql = "insert into BUD_DATAYEAR_COST (DATAYEAR_COST_ID_, BATCH_CODE_, FTY_CODE_, YWFW_CODE_, ITEM_CODE_, ITEM_NAME_, PRODUCT_CODE_, WORKMORE_CODE_, AMOUNT_) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate.update(sql, ${insSqlParams});
    }

    @Override
    public int update${module}(${updInParams}) {
        String sql = "update BUD_DATAYEAR_COST set AMOUNT_ = ? where DATAYEAR_COST_ID_ = ?";
        return ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate.update(sql, ${updSqlParams};
    }

    @Override
    public int delete${module}(${delInParams}) {
        String sql = "delete from BUD_DATAYEAR_COST where BATCH_CODE_ = ? and FTY_CODE_ = ? and YWFW_CODE_ = ? and ITEM_CODE_ = ?";
        return ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate.update(sql, ${delSqlParams});
    }

<#if hasDelBatch>
    @Override
    public int deletePersonToWkItemBatch(${delBatchInParams}) {
        if (ID_LIST.size() == 0) return 0;
        Map<String, Object> paramMap = new HashMap<>(4);
        String sql = "delete from BASE_PERSONTOWKITEM where ID_ in (:ID_LIST)";
        paramMap.put("ID_LIST", ID_LIST);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate);
        return namedParameterJdbcTemplate.update(sql, paramMap);
    }

</#if>
    private BaseSqlCriteria buildSqlCriteriaProductSale(boolean count, ${selInParams}) {
        String sql;
        Map<String, Object> paramMap = new HashMap<>(8);
        if (count) {
            sql = "select count(*) from BUD_DATAYEAR_COST where 1=1";
        } else {
            sql = "select ITEM_CODE_, ITEM_NAME_, sum(AMOUNT_) as AMOUNT_ from BUD_DATAYEAR_COST where 1=1";
        }
        if (StringUtils.isNotEmpty(BATCH_CODE_)) {
            sql += " and BATCH_CODE_ = :BATCH_CODE_";
            paramMap.put("BATCH_CODE_", BATCH_CODE_);
        }
        if (StringUtils.isNotEmpty(FTY_CODE_)) {
            sql += " and FTY_CODE_ = :FTY_CODE_";
            paramMap.put("FTY_CODE_", FTY_CODE_);
        }
        if (StringUtils.isNotEmpty(YWFW_CODE_)) {
            sql += " and YWFW_CODE_ = :YWFW_CODE_";
            paramMap.put("YWFW_CODE_", YWFW_CODE_);
        }
        if (StringUtils.isNotEmpty(ITEM_NAME_)) {
            sql += " and ITEM_NAME_ like '" + ITEM_NAME_ + "%'";
            paramMap.put("ITEM_NAME_", ITEM_NAME_);
        }

        sql += " order by ITEM_CODE_, ITEM_NAME_";
        return new BaseSqlCriteria(sql, paramMap);
    }
}
