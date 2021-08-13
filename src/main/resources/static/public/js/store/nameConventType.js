/**
 * 规范名称
 *
 * @author FengGuanZhong
 * @private
 */

var nameConventType = [];
nameConventType.push(
    {
        CODE_: '1',
        NAME_: '大连常用规范'
    }, {
        CODE_: '2',
        NAME_: 'EAM3期规范'
    });

var nameConventTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'nameConventTypeStore',
    fields: ['NAME_', 'CODE_'],
    data: nameConventType
});

Ext.data.StoreManager.register(nameConventTypeStore);