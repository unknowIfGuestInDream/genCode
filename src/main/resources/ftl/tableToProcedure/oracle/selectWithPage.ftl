CREATE OR REPLACE PROCEDURE ${selProcName}(
${selectWithPageInParams}
) IS
/*查询${tableDesc}*/
BEGIN
  IF ${page} IS NOT NULL AND ${limit} IS NOT NULL AND ${limit} > 0 THEN
    OPEN ${result} FOR
      SELECT *
        FROM (SELECT FULLTABLE.*, ROWNUM RN
          FROM (SELECT * FROM ${tableName}${selWithPageSqlWhere}${orderBy!}) FULLTABLE
                WHERE ROWNUM <= ${page} * ${limit})
        WHERE RN > (${page} - 1) * ${limit};
  ELSE
    OPEN V_C_CURSOR FOR
      SELECT * FROM ${tableName}${selectSqlWhere}${orderBy!};
  END IF;
  SELECT COUNT(1)
    INTO ${total}
    FROM ${tableName}${selectSqlWhere};
  ${message} := 'success';
EXCEPTION
  WHEN OTHERS THEN
    ${message} := SQLERRM;
END ${selProcName};