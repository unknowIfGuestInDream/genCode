/**
 * 生成代码模板
 *
 * @author FengGuanZhong
 * @private
 */

var genProcedureModelType = [];
genProcedureModelType.push(
    {
        CODE_: '1',
        NAME_: '大连模版restful风格'
    }, {
        CODE_: '2',
        NAME_: '大连模版demo风格'
    });

var genProcedureModelTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'genProcedureModelTypeStore',
    fields: ['NAME_', 'CODE_'],
    data: genProcedureModelType
});

Ext.data.StoreManager.register(genProcedureModelTypeStore);