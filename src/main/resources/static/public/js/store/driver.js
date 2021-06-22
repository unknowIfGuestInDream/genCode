/**
 *驱动类
 *
 * @author ZhangXu
 * @private
 */

var oracleDriver = [];
oracleDriver.push({
    CODE_: 'oracle.jdbc.OracleDriver',
    NAME_: 'ORACLE'
}, {
    CODE_: 'com.mysql.jdbc.Driver',
    NAME_: 'MYSQL'
}, {
    CODE_: 'com.mysql.cj.jdbc.Driver',
    NAME_: 'MYSQL8'
}, {
    CODE_: 'org.mariadb.jdbc.Driver',
    NAME_: 'MARIADB'
});

var oracleDriverStore = Ext.create("Ext.data.Store", {
    storeId: 'oracleDriverStore',
    fields: ['NAME_', 'CODE_'],
    data: oracleDriver
});

Ext.data.StoreManager.register(oracleDriverStore);
