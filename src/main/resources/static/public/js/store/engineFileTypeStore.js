/**
 * 文档文件类型
 *
 * @author TangLiang
 * @private
 */
var engineFileTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'engineFileTypeStore',
    fields: ['NAME_', 'CODE_'],
    autoLoad: true,
    loading: true,
    proxy: {
        url: '/gen/selectEngineFileType',
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

Ext.data.StoreManager.register(engineFileTypeStore);