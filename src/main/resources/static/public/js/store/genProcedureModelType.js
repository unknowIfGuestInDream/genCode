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
        NAME_: 'RESTFUL'
    });

var genProcedureModelTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'genProcedureModelTypeStore',
    fields: ['NAME_', 'CODE_'],
    data: genProcedureModelType
});

Ext.data.StoreManager.register(genProcedureModelTypeStore);