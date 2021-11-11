package ${package}.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

/**
 * 存储过程返回结果工具类
 *
 * @author: TangLiang
 * @date: 2021/4/14 14:24
 * @since: 1.0
 */
public class ProcedureUtils {

    public static List<Map<String, Object>> resultHash(ResultSet rs) throws SQLException {

        List<Map<String, Object>> result = new ArrayList<>();

        ResultSetMetaData rsm = rs.getMetaData();

        while (rs.next()) {
            Map<String, Object> model = new HashMap<>(16);
            for (int i = 1; i <= rsm.getColumnCount(); i++) {
                switch (rsm.getColumnType(i)) {
                    case Types.ROWID:
                        model.put(rsm.getColumnName(i),
                                rs.getString(i) == null ? "" : rs.getString(i));
                        break;
                    case Types.DATE:
                        model.put(rsm.getColumnName(i),
                                rs.getString(i) == null ? "" : rs.getString(i)
                                        .split("\\.")[0]);
                        break;
                    case Types.TIMESTAMP:
                        model.put(rsm.getColumnName(i),
                                rs.getString(i) == null ? "" : rs.getString(i));
                        break;
                    case Types.DOUBLE:
                        model.put(rsm.getColumnName(i),
                                (rs.getString(i) == null ? "" : rs.getDouble(i)));
                        break;
                    case Types.BLOB:
                        model.put(
                                rsm.getColumnName(i),
                                (rs.getObject(i) == null ? "" : new String(rs
                                        .getBytes(i))));
                        break;
                    case Types.CLOB:
                        model.put(
                                rsm.getColumnName(i),
                                (rs.getObject(i) == null ? "" : rs.getString(i)));
                        break;
                    default:
                        model.put(rsm.getColumnName(i),
                                rs.getObject(i) == null ? "" : rs.getObject(i));
                }
            }
            result.add(model);
        }
        rs.close();

        return result;
    }

    public static List<Map<String, Object>> resultLinkedHash(ResultSet rs) throws SQLException {

        List<Map<String, Object>> result = new ArrayList<>();

        ResultSetMetaData rsm = rs.getMetaData();

        while (rs.next()) {
            Map<String, Object> model = new LinkedHashMap<>();
            for (int i = 1; i <= rsm.getColumnCount(); i++) {
                switch (rsm.getColumnType(i)) {
                    case Types.DATE:
                        model.put(rsm.getColumnName(i),
                                rs.getString(i) == null ? "" : rs.getString(i)
                                        .split("\\.")[0]);
                        break;
                    case Types.DOUBLE:
                        model.put(rsm.getColumnName(i),
                                (rs.getString(i) == null ? "" : rs.getDouble(i)));
                        break;
                    case Types.BLOB:
                        model.put(
                                rsm.getColumnName(i),
                                (rs.getObject(i) == null ? "" : new String(rs
                                        .getBytes(i))));
                        break;
                    default:
                        model.put(rsm.getColumnName(i),
                                rs.getObject(i) == null ? "" : rs.getObject(i));
                }
            }
            result.add(model);
        }
        rs.close();

        return result;
    }

    public static Page<Map<String, Object>> pageList(Pageable pageable, List<Map<String, Object>> list) {
        if (list == null) {
            return new PageImpl<Map<String, Object>>(new ArrayList<>(), pageable, 0);
        }
        int total = list.size();
        int fromIndex = pageable.getPageSize() * pageable.getPageNumber();
        List<Map<String, Object>> result = null;
        if (total > pageable.getPageSize() * (pageable.getPageNumber() + 1)) {
            result = list.subList(fromIndex, pageable.getPageSize());
        } else {
            result = list.subList(fromIndex, total);
        }
        return new PageImpl<>(result, pageable, total);
    }
}
