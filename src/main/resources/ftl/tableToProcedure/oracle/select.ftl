CREATE OR REPLACE PROCEDURE ${procName}(${selectInParams}) IS
/*查询${tableDesc}*/
BEGIN
  OPEN ${result} FOR
    SELECT * FROM ${tableName}${selectSqlWhere};
  ${message} := 'success';
EXCEPTION
  WHEN OTHERS THEN
    ${message} := SQLERRM;
END ${procName};