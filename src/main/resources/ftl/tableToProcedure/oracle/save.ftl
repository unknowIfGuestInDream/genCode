CREATE OR REPLACE PROCEDURE ${saveProcName}(${saveInParams}) IS
/*保存${tableDesc}*/
  V_I_NUMBER INT;
BEGIN
  SELECT COUNT(1)
    INTO V_I_NUMBER
    FROM ${tableName}${getSqlWhere};
  IF (V_I_NUMBER = 0) THEN
    INSERT INTO ${tableName}(${insField})
    VALUES (${insParams});
  ELSE
    UPDATE ${tableName}
      SET ${updParams}${getSqlWhere};
  END IF;
  ${message} := 'success';
EXCEPTION
    WHEN OTHERS THEN
      ${message} := SQLERRM;
END ${saveProcName};