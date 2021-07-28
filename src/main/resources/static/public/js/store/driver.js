/**
 *驱动类
 *
 * @author ZhangXu
 * @private
 */

var driver = [];
driver.push({
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
}, {
    CODE_: 'com.microsoft.sqlserver.jdbc.SQLServerDriver',
    NAME_: 'SQLSERVER'
});

var driverStore = Ext.create("Ext.data.Store", {
    storeId: 'driverStore',
    fields: ['NAME_', 'CODE_'],
    data: driver
});

Ext.data.StoreManager.register(driverStore);
