-- Create sequence
create sequence ${tableName?upper_case}_SEQ
minvalue 1
maxvalue 9999999999999999999999
start with 1
increment by 1
nocache;

CREATE OR REPLACE TRIGGER ${tableName?upper_case}_TRI
  BEFORE INSERT ON ${tableName?upper_case}
  REFERENCING OLD AS OLD NEW AS NEW
  FOR EACH ROW
BEGIN
  SELECT ${tableName?upper_case}_SEQ.NEXTVAL INTO :NEW.${primaryKey} FROM DUAL;
END ${tableName?upper_case}_TRI;