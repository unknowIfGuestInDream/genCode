/**
 * java类对象
 *
 * @author TangLiang
 */
var javaClassStore = Ext.create("Ext.data.Store", {
    storeId: 'javaClassStore',
    fields: ['NAME_', 'CODE_'],
    autoLoad: true,
    loading: true,
    proxy: {
        url: '/gen/selectJavaClass',
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


Ext.data.StoreManager.register(javaClassStore);