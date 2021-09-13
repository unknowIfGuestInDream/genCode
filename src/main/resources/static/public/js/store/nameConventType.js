/**
 * 规范名称
 *
 * @author FengGuanZhong
 * @private
 */
var nameConventTypeStore = Ext.create("Ext.data.Store", {
    storeId: 'nameConventTypeStore',
    fields: ['NAME_', 'CODE_'],
    autoLoad: true,
    loading: true,
    proxy: {
        url: '/gen/selectNameConventType',
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

Ext.data.StoreManager.register(nameConventTypeStore);