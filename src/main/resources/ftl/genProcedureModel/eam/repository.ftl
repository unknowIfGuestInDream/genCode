package ${package}.repository;

import ${package}.util.ProcedureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import oracle.jdbc.OracleTypes;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ${author}
 * @date: ${date}
 * @since: 1.0
 */
@Repository
public class ${module}Repository {
    @Autowired
    private JdbcTemplate ${package?substring(package?last_index_of(".")+1)?lower_case}JdbcTemplate;
${repositoryMethod}
}