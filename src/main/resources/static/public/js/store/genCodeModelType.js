/**
 * 生成后台代码模板
 *
 * @author TangLiang
 * @private
 */
var genCodeModelTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'genCodeModelTypeStore',
    fields: ['NAME_', 'CODE_'],
    autoLoad: true,
    loading: true,
    proxy: {
        url: '/gen/selectGenCodeModelType',
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

Ext.data.StoreManager.register(genCodeModelTypeStore);