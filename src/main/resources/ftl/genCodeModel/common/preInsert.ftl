<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>新增${moduleDesc}</title>
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
        Ext.getBody().mask('加载中...');//加载时页面遮盖

        //按钮栏
        var buttonPanel = Ext.create('Ext.Panel', {
            id: 'buttonPanel',
            defaults: {
                style: 'margin: 2px;'
            },
            items: [{
                xtype: 'button',
                text: '保存',
                icon: 'public/image/btn/save.png',
                handler: _insert${module}
            }, {
                xtype: 'button',
                text: '关闭',
                icon: 'public/image/btn/close.png',
                handler: _close
            }]
        });

        var formPanel = Ext.create('Ext.form.Panel', {//表单
            id: 'formPanel',
            layout: 'column',
            frame: true,
            autoScroll: true,
            defaults: {
                labelAlign: 'right',
                labelWidth: 150,
                inputWidth: 300,
            },
            items: [{
                xtype: 'textfield',
                name: 'NAME',
                fieldLabel: '数据源名称',
                allowBlank: false
            }, {
                xtype: 'combo',
                queryMode: 'local',
                store: driverStore,
                valueField: 'CODE_',
                displayField: 'NAME_',
                id: 'DRIVER',
                name: 'DRIVER',
                fieldLabel: '驱动类名称',
                allowBlank: false,
                forceSelection: true,
                style: 'clear:both'
            }, {
                xtype: 'textfield',
                id: 'URL',
                name: 'URL',
                inputWidth: 600,
                fieldLabel: '链接',
                allowBlank: false,
                style: 'clear:both'
            }, {
                xtype: 'textfield',
                name: 'USERNAME',
                fieldLabel: '用户名',
                allowBlank: false,
                style: 'clear:both'
            }, {
                xtype: 'textfield',
                name: 'PASSWORD',
                fieldLabel: '密码',
                allowBlank: false,
                style: 'clear:both'
            }]
        });

        Ext.create('Ext.container.Viewport', {//整体布局
            layout: {
                type: 'border',//border布局
                regionWeights: {//四个角的归属
                    west: -1,
                    north: 1,
                    south: 1,
                    east: -1
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
                layout: 'fit',//充满
                items: [formPanel]
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

        //默认为oracle驱动
        Ext.getCmp('DRIVER').setValue('oracle.jdbc.OracleDriver');
        Ext.getCmp('URL').setValue('jdbc:oracle:thin:@10.18.26.86:1521:SID');

        Ext.getBody().unmask();
    }

    //新增${moduleDesc}
    function _insert${module}() {
        Ext.getCmp('formPanel').getForm().submit({//提交表单
            url: '/${package?substring(package?last_index_of(".")+1)?lower_case}/insert${module}',
            submitEmptyText: false,
            waitMsg: '进行中',
            success: function (form, action) {
                var data = action.result;
                parent.returnValue = data.success;
                _close();
            },
            failure: function (form, action) {
                switch (action.failureType) {
                    case Ext.form.action.Action.CLIENT_INVALID:
                        Ext.MessageBox.alert('错误', '请填写必填项', Ext.MessageBox.ERROR);
                        break;
                    case Ext.form.action.Action.SERVER_INVALID:
                        Ext.MessageBox.alert('错误', action.result.message, Ext.MessageBox.ERROR);
                        break;
                    case Ext.form.action.Action.CONNECT_FAILURE:
                        Ext.MessageBox.alert('错误', '服务器错误', Ext.MessageBox.ERROR);
                }
            }
        });
    }
</script>
</body>
</html>