/**
 * 暂存箱功能
 * @author TangLiang
 */
var temWin;

//暂存数据
var temporaryStorageStore = Ext.create('Ext.data.Store', {
    storeId: 'temporaryStorageStore',
    fields: ['NAME_'],
});

Ext.data.StoreManager.register(temporaryStorageStore);

var temporaryStoragePanel = Ext.create('Ext.grid.Panel', {
    id: 'temporaryStoragePanel',
    store: temporaryStorageStore,
    columnLines: true,
    frame: true,
    selModel: {
        selType: 'checkboxmodel',
        mode: 'SIMPLE'
    },
    columns: [{
        xtype: 'rownumberer',
        align: 'center',
        width: 50
    }, {
        text: '存储过程名称',
        dataIndex: 'NAME_',
        style: 'text-align: center;',
        flex: 1,
        width: 200
    }],
    viewConfig: {
        emptyText: '<div style="text-align: center; padding-top: 50px; font: italic bold 20px Microsoft YaHei;">无暂存数据</div>',
        enableTextSelection: true
    },
    dockedItems: [{
        xtype: 'form',
        border: false,
        defaults: {
            margin: '4,0,0,0'
        },
        items: [{
            xtype: 'button',
            icon: 'public/image/btn/delete.png',
            text: '删除',
            handler: _deleteTemporaryStorage
        }, {
            xtype: 'button',
            icon: 'public/image/btn/refresh.png',
            text: '清空暂存数据',
            handler: _emptyTemporaryStorage
        }, {
            xtype: 'button',
            text: '关闭',
            icon: 'public/image/btn/close.png',
            handler: _winclose
        }]
    }]
});

//查看暂存
function _viewTemporaryStorage() {
    temWin = Ext.create('Ext.window.Window', {
        title: '暂存箱',
        modal: true,
        autoShow: true,
        maximized: false,
        layout: "fit",
        maximizable: true,
        autoScroll: true,
        width: document.documentElement.clientWidth * 0.8,
        height: document.documentElement.clientHeight * 0.8,
        closeAction: 'hide', //关闭窗体实际上是隐藏窗体并未关闭销毁此窗体对象(节约资源)
        plain: true,//窗体主体部分背景颜色透明
        items: [temporaryStoragePanel]
    });
    temWin.show();
}

//删除暂存数据
function _deleteTemporaryStorage() {
    var records = Ext.getCmp('temporaryStoragePanel').getSelectionModel().getSelection();
    if (records.length === 0) {
        Ext.MessageBox.alert('警告', '请选择暂存存储过程', Ext.MessageBox.WARNING);
        return;
    }
    temporaryStorageStore.remove(records);
}

//清空暂存数据
function _emptyTemporaryStorage() {
    if (temporaryStorageStore.getCount() === 0) {
        return;
    }
    temporaryStorageStore.removeAll();
}

function _winclose() {
    temWin.close();
}