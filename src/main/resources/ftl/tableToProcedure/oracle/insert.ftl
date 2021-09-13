CREATE OR REPLACE PROCEDURE ${insProcName}(
${insInParams}
) IS
/*新增${tableDesc}*/
BEGIN
  INSERT INTO ${tableName}(${insField})
    VALUES (${insValueParams});
  ${message} := 'success';
EXCEPTION
  WHEN OTHERS THEN
    ${message} := SQLERRM;
END ${insProcName};