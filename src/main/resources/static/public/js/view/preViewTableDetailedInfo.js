/**
 * 查看表详细信息
 * 目前只支持oracle详情查看 2021-12-24
 * 后期增强根据数据库类型跳转不同页 后续需求方法_getDatabaseType()
 *
 * @param name 表名
 * @param comment 表注释
 * @requires dataBaseStore.js
 */
function _preViewTableDetailedInfo(name, comment) {
    returnValue = null;
    win = Ext.create('Ext.window.Window', {
        title: '表详细信息',
        modal: true,
        autoShow: true,
        maximized: false,
        maximizable: true,
        autoScroll: true,
        width: document.documentElement.clientWidth * 0.7,
        height: document.documentElement.clientHeight * 0.8,
        html: '<iframe src="/gen/viewTableDetailedInfo?name=' + name + '&url=' + Ext.getCmp('dataSource').valueModels[0].data.URL + '&driver=' + Ext.getCmp('dataSource').valueModels[0].data.DRIVER + '&userName=' + Ext.getCmp('dataSource').valueModels[0].data.USERNAME + '&password=' + Ext.getCmp('dataSource').valueModels[0].data.PASSWORD + '&comment=' + comment + '" style="width: 100%; height: 99%;" frameborder="0"></iframe>'
    });
}