package ${package}.base;

import java.util.Map;

public class BaseSqlCriteria {
    private String sql;
    private Map<String, Object> paramMap;

    public BaseSqlCriteria(String sql, Map<String, Object> paramMap) {
        super();
        this.sql = sql;
        this.paramMap = paramMap;
    }

    public String getSql() {
        return sql;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}