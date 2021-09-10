CREATE OR REPLACE PROCEDURE ${delProcName}(${getInParams}) IS
/*删除某个${tableDesc}*/
BEGIN
  DELETE FROM ${tableName}${getSqlWhere};
  ${message} := 'success';
EXCEPTION
  WHEN OTHERS THEN
    ${message} := SQLERRM;
END ${delProcName};