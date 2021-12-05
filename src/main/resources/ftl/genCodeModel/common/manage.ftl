<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${moduleDesc}管理</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="public/css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="public/css/ext-zh_CN.css"/>
    <script type="text/javascript" src="public/js/ext-all.js"></script>
    <script type="text/javascript" src="public/js/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="public/js/base.js"></script>
</head>
<body>
<script>
    Ext.onReady(function () {
        Ext.getBody().mask('加载中...');

        var ${module?uncap_first}Store = Ext.create('Ext.data.Store', {
            storeId: '${module?uncap_first}Store',
            autoLoad: false,//true为自动加载
            loading: false,//自动加载时必须为true
            pageSize: -1,
            fields: ['ID', 'NAME', 'URL', 'DRIVER', 'USERNAME', 'PASSWORD', 'UPDATE_TIME', 'CREATE_TIME'],
            proxy: {
                url: '/${package?substring(package?last_index_of(".")+1)?lower_case}/select${module}',
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
            }
        });

        var buttonPanel = Ext.create('Ext.Panel', {
            id: 'buttonPanel',
            defaults: {
                style: 'margin: 2px;'
            },
            items: [{
                xtype: 'button',
                text: '查询',
                icon: 'public/image/btn/search.png',
                handler: _select${module}
            }, {
                xtype: 'button',
                text: '新增',
                icon: 'public/image/btn/add.png',
                handler: _preInsert${module}
            }, {
                xtype: 'button',
                text: '修改',
                icon: 'public/image/btn/edit.png',
                handler: _preUpdate${module}
            }, {
                xtype: 'button',
                text: '删除',
                icon: 'public/image/btn/delete.png',
                handler: _delete${module}
            }]
        });

        var ${module?uncap_first}Panel = Ext.create('Ext.grid.Panel', {
            id: '${module?uncap_first}Panel',
            store: ${module?uncap_first}Store,
            columnLines: true,
            selModel: {
                selType: 'checkboxmodel',
                mode: 'SINGLE'
            },
            frame: true,
            columns: [{
                xtype: 'rownumberer',
                align: 'center',
                width: 50
            }, {
                text: '数据源名称',
                dataIndex: 'NAME',
                flex: 1,
                minWidth: 150
            }, {
                text: '驱动类名称',
                dataIndex: 'DRIVER',
                flex: 2,
                minWidth: 200
            }, {
                text: '链接',
                dataIndex: 'URL',
                flex: 2,
                minWidth: 200
            }, {
                text: '用户名',
                dataIndex: 'USERNAME',
                width: 150
            }, {
                text: '创建日期',
                dataIndex: 'CREATE_TIME',
                width: 150
            }, {
                text: '更新日期',
                dataIndex: 'UPDATE_TIME',
                width: 150
            }],
            viewConfig: {
                emptyText: '<div style="text-align: center; padding-top: 50px; font: italic bold 20px Microsoft YaHei;">没有数据</div>',
                enableTextSelection: true
            }
        });

        Ext.create('Ext.container.Viewport', {
            layout: {
                type: 'border',
                regionWeights: {
                    west: 1,
                    north: -1,
                    south: -1,
                    east: 1
                }
            },
            defaults: {
                border: false
            },
            items: [{
                region: 'north',
                items: [buttonPanel]
            }, {
                region: 'center',
                layout: 'fit',
                items: [${module?uncap_first}Panel]
            }]
        });

        _init();
    });

    function _init() {
        for (var i = 0; i < Ext.data.StoreManager.getCount(); i++) {
            if (Ext.data.StoreManager.getAt(i).isLoading()) {
                return;
            }
        }

        _select${module}();
        Ext.getBody().unmask();
    }

    //查询${moduleDesc}
    function _select${module}() {
        var ${module?uncap_first}Store = Ext.data.StoreManager.lookup('${module?uncap_first}Store');
        ${module?uncap_first}Store.proxy.extraParams = {};
        ${module?uncap_first}Store.load();
    }

    function _preInsert${module}() {
        returnValue = null;
        win = Ext.create('Ext.window.Window', {
            title: '新增${moduleDesc}',
            modal: true,
            autoShow: true,
            maximizable: true,
            width: document.documentElement.clientWidth * 0.6,
            height: document.documentElement.clientHeight * 0.8,
            html: '<iframe src="/${package?substring(package?last_index_of(".")+1)?lower_case}/preInsert${module}" style="width: 100%; height: 100%;" frameborder="0"></iframe>',
            listeners: {
                close: function (panel, eOpts) {
                    if (returnValue !== null) {
                        if (returnValue === true) {
                            _select${module}();
                            Toast.alert('信息', '新增成功', 2000);
                        } else {
                            Ext.MessageBox.alert('错误', '新增失败', Ext.MessageBox.ERROR);
                        }
                    }
                }
            }
        });
    }

    function _preUpdate${module}() {
        var records = Ext.getCmp('${module?uncap_first}Panel').getSelectionModel().getSelection();
        if (records.length !== 1) {
            Ext.MessageBox.alert('警告', '请选择一条待修改数据', Ext.MessageBox.WARNING);
            return;
        }

        returnValue = null;
        win = Ext.create('Ext.window.Window', {
            title: '修改${moduleDesc}',
            modal: true,
            autoShow: true,
            maximized: false,
            maximizable: true,
            width: document.documentElement.clientWidth * 0.6,
            height: document.documentElement.clientHeight * 0.8,
            html: '<iframe src="/${package?substring(package?last_index_of(".")+1)?lower_case}/preUpdate${module}?ID=' + records[0].get('ID') + '" style="width: 100%; height: 100%;" frameborder="0"></iframe>',
            listeners: {
                close: function (panel, eOpts) {
                    if (returnValue != null) {//更新页面数据
                        if (returnValue === true) {
                            _select${module}();
                            Toast.alert('信息', '修改成功', 2000);
                        } else {
                            Ext.MessageBox.alert('错误', '修改失败', Ext.MessageBox.ERROR);
                        }
                    }
                }
            }
        });
    }

    function _delete${module}() {
        var records = Ext.getCmp('${module?uncap_first}Panel').getSelectionModel().getSelection();
        if (records.length !== 1) {
            Ext.MessageBox.alert('警告', '请选择一条待删除数据', Ext.MessageBox.WARNING);
            return;
        }

        Ext.MessageBox.show({
            title: '请确认',
            msg: '是否删除',
            buttons: Ext.MessageBox.YESNO,
            icon: Ext.MessageBox.QUESTION,
            fn: function (btn) {
                if (btn === 'yes') {
                    Ext.Ajax.request({
                        url: '/${package?substring(package?last_index_of(".")+1)?lower_case}/delete${module}',
                        async: false,
                        params: {
                            'ID': records[0].get('ID'),
                            'URL': records[0].get('URL'),
                            'USERNAME': records[0].get('USERNAME')
                        },
                        callback: function (options, success, response) {
                            if (success) {
                                var data = Ext.decode(response.responseText);
                                if (data.success === true) {
                                    _select${module}();
                                    Toast.alert('信息', '删除成功', 2000);
                                } else {
                                    Ext.MessageBox.alert('错误', '删除失败', Ext.MessageBox.ERROR);
                                }
                            } else {
                                Ext.MessageBox.alert('错误', '服务器错误', Ext.MessageBox.ERROR);
                            }
                        }
                    });
                }
            }
        });
    }
</script>
</body>
</html>