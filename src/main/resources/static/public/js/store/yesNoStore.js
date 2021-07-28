/**
 * 是否
 *
 * @author TangLiang
 */
var yesNo = [];
yesNo.push({
    CODE_: 'YES',
    NAME_: '是'
}, {
    CODE_: 'NO',
    NAME_: '否'
});

var yesNoStore = Ext.create("Ext.data.Store", {
    storeId: 'yesNoStore',
    fields: ['NAME_', 'CODE_'],
    data: yesNo
});

Ext.data.StoreManager.register(yesNoStore);

var yn = [];
yn.push({
    CODE_: 'Y',
    NAME_: '是'
}, {
    CODE_: 'N',
    NAME_: '否'
});

var ynStore = Ext.create("Ext.data.Store", {
    storeId: 'ynStore',
    fields: ['NAME_', 'CODE_'],
    data: yn
});

Ext.data.StoreManager.register(ynStore);