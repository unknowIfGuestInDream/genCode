CREATE OR REPLACE PROCEDURE ${procName}(${updInParams}) IS
/*修改${tableDesc}*/
BEGIN
  UPDATE ${tableName}
    SET ${updParams}${getSqlWhere};
  ${message} :='success';
EXCEPTION
  WHEN OTHERS THEN
  V_V_INFO := SQLERRM;
END ${procName};