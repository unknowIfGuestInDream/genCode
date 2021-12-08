/**
 * 排序类型
 *
 * @author TangLiang
 */
var orderType = [];
orderType.push({
    CODE_: '',
    NAME_: ''
}, {
    CODE_: 'ASC',
    NAME_: 'ASC'
}, {
    CODE_: 'DESC',
    NAME_: 'DESC'
});

var orderTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'orderTypeStore',
    fields: ['NAME_', 'CODE_'],
    data: orderType
});

Ext.data.StoreManager.register(orderTypeStore);