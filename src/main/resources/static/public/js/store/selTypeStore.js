/**
 * 查询条件类型
 *
 * @author TangLiang
 */
var selType = [];
selType.push({
    CODE_: '',
    NAME_: ''
}, {
    CODE_: '0',
    NAME_: '精确查询'
}, {
    CODE_: '1',
    NAME_: '模糊查询'
}, {
    CODE_: '2',
    NAME_: '区间查询'
});

var selTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'selTypeStore',
    fields: ['NAME_', 'CODE_'],
    data: selType
});

Ext.data.StoreManager.register(selTypeStore);