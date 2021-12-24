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

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Map<String, Object> load${module}(${loadInParams}) {
        String sql = "select * from ${tableName} where ${loadWhere}";
        List<Map<String, Object>> result = ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate.queryForList(sql, ${loadSqlParams});
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return new HashMap<>(4);
        }
    }

    @Override
    public List<Map<String, Object>> select${module}(${selInParams}<#if selInParams?length gt 1>, </#if>Integer page, Integer limit) {
        BaseSqlCriteria baseSqlCriteria = buildSqlCriteria${module}(false<#if selSqlParams?length gt 1>, </#if>${selSqlParams});
        String sql = baseSqlCriteria.getSql();
        Map<String, Object> paramMap = baseSqlCriteria.getParamMap();
        if (page != null && limit != null && limit > 0) {
            int start = (page - 1) * limit;
            sql = sql + " limit " + start + ", " + limit;
        }
        return namedParameterJdbcTemplate.queryForList(sql, paramMap);
    }

    @Override
    public int count${module}(${selInParams}) {
        BaseSqlCriteria baseSqlCriteria = buildSqlCriteria${module}(true<#if selSqlParams?length gt 1>, </#if>${selSqlParams});// 根据查询条件组装总数统计SQL语句
        String sql = baseSqlCriteria.getSql();
        Map<String, Object> paramMap = baseSqlCriteria.getParamMap();
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
    }

    @Override
    public int insert${module}(${insInParams}) {
        String sql = "insert into ${tableName} (${insSqlParams}) values(${insSqlMark})";
        return ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate.update(sql, ${insSqlParams});
    }

    @Override
    public int update${module}(${updInParams}) {
        String sql = "update ${tableName} set ${updSet} where ${updWhere}";
        return ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate.update(sql, ${updJdbcParams});
    }

    @Override
    public int delete${module}(${delInParams}) {
        String sql = "delete from ${tableName} where ${delWhere}";
        return ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate.update(sql, ${delSqlParams});
    }

<#if hasDelBatch>
    @Override
    public int delete${module}Batch(${delBatchInParams}) {
        Map<String, Object> paramMap = new HashMap<>(${delBatchMapSize});
        String sql = "delete from ${tableName} where ${delBatchWhere}";
<#list primarys as item>
        paramMap.put("${item}LIST", ${item}LIST);
</#list>
        return namedParameterJdbcTemplate.update(sql, paramMap);
    }

</#if>
    private BaseSqlCriteria buildSqlCriteria${module}(boolean count<#if selInParams?length gt 1>, </#if>${selInParams}) {
        String sql;
        Map<String, Object> paramMap = new HashMap<>(${selMapSize});
        if (count) {
            sql = "select count(*) from ${tableName} where 1 = 1";
        } else {
            sql = "select * from ${tableName} where 1 = 1";
        }
${selBuildParams}
<#if orderBy??>
        if (!count) {
            sql += " order by ${orderBy}";
        }
</#if>
        return new BaseSqlCriteria(sql, paramMap);
    }
}
