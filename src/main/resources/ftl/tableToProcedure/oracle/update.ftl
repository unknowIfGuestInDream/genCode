CREATE OR REPLACE PROCEDURE ${updProcName}(
${updInParams}
) IS
/*修改${tableDesc}*/
BEGIN
  UPDATE ${tableName}
    SET ${updSqlParams}${getSqlWhere};
  ${message} :='success';
EXCEPTION
  WHEN OTHERS THEN
  V_V_INFO := SQLERRM;
END ${updProcName};