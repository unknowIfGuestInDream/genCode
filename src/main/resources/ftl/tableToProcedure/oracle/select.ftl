CREATE OR REPLACE PROCEDURE ${selProcName}(
${selectInParams}
) IS
/*查询${tableDesc}*/
BEGIN
  OPEN ${result} FOR
    SELECT *
      FROM ${tableName}${selectSqlWhere}${orderBy!};
  ${message} := 'success';
EXCEPTION
  WHEN OTHERS THEN
    ${message} := SQLERRM;
END ${selProcName};