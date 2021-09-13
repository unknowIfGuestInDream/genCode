/**
 * 生成代码模板
 *
 * @author FengGuanZhong
 * @private
 */
var genProcedureModelTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'genProcedureModelTypeStore',
    fields: ['NAME_', 'CODE_'],
    autoLoad: true,
    loading: true,
    proxy: {
        url: '/gen/selectGenProcedureModelType',
        type: 'ajax',
        async: true,//false=同步.
        actionMethods: {
            read: 'GET'
        },
        extraParams: {},
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    listeners: {
        load: function (store, records, successful, eOpts) {
            _init();
        }
    }
});

Ext.data.StoreManager.register(genProcedureModelTypeStore);