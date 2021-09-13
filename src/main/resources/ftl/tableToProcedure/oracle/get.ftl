CREATE OR REPLACE PROCEDURE ${getProcName}(
${getInParams}
) IS
/*查询某个${tableDesc}*/
BEGIN
  OPEN ${result} FOR
    SELECT *
      FROM ${tableName}${getSqlWhere};
  ${message} := 'success';
EXCEPTION
  WHEN OTHERS THEN
    ${message} := SQLERRM;
END ${getProcName};