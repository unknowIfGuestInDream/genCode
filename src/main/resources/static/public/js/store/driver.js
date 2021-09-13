/**
 * 驱动类
 *
 * @author ZhangXu
 * @private
 */
var driverStore = Ext.create("Ext.data.Store", {
    storeId: 'driverStore',
    fields: ['NAME_', 'CODE_'],
    autoLoad: true,
    loading: true,
    proxy: {
        url: '/gen/selectDataBaseType',
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

Ext.data.StoreManager.register(driverStore);
