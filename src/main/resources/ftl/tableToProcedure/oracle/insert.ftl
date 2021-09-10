CREATE OR REPLACE PROCEDURE ${insProcName}(${insInParams}) IS
/*新增${tableDesc}*/
BEGIN
  INSERT INTO ${tableName}(${insField})
  VALUES (${insParams});
  ${message} := 'success';
EXCEPTION
  WHEN OTHERS THEN
    ${message} := SQLERRM;
END ${insProcName};