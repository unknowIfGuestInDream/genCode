/**
 * 数据源信息
 * @type {Ext.data.Store}
 */
var dataBaseStore = Ext.create('Ext.data.Store', {
    storeId: 'dataBaseStore',
    autoLoad: false,//true为自动加载
    loading: false,//自动加载时必须为true
    pageSize: -1,
    fields: ['ID', 'NAME', 'URL', 'DRIVER', 'USERNAME', 'PASSWORD', 'UPDATE_TIME', 'CREATE_TIME'],
    proxy: {
        url: '/gen/selectDataBaseInfo',
        type: 'ajax',
        async: false,
        actionMethods: {
            read: 'GET'
        },
        extraParams: {},
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

Ext.data.StoreManager.register(dataBaseStore);

/**
 * 数据源信息加载
 * @private
 */
function _selectDataBaseInfo() {
    dataBaseStore.proxy.extraParams = {};
    dataBaseStore.load();
}

//添加监听
function _addDataSourceLoad() {
    dataBaseStore.addListener('load', function () {
        Ext.getCmp('dataSource').select(dataBaseStore.first());
    })
}

//获取数据库空间名称
function _getSchema() {
    var jdbcUrl = Ext.getCmp('dataSource').valueModels[0].data.URL;
    var schema = "";
    if (jdbcUrl.startsWith("jdbc:mysql") || jdbcUrl.startsWith("jdbc:mariadb")) {
        var endIndex = jdbcUrl.indexOf('?');
        var startIndex = jdbcUrl.substring(0, endIndex).lastIndexOf('/');
        schema = jdbcUrl.substring(startIndex + 1, endIndex);
    }
    return schema;
}