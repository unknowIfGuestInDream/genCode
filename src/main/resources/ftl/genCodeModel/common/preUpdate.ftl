<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改${moduleDesc}</title>
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
    <#list primarys as item>
    var ${item} = null;
    </#list>
    if (location.href.split('?')[1] !== undefined) {
        var parameters = Ext.urlDecode(location.href.split('?')[1]);
        <#list primarys as item>
        (parameters.${item} !== undefined) ? ${item} = parameters.${item} : 0;
        </#list>
    }
    var ${module?uncap_first};//被修改对象
    Ext.onReady(function () {
        Ext.getBody().mask('加载中...');//加载时页面遮盖

        Ext.Ajax.request({//加载被修改对象
            url: gatewayUrl + '/${package?substring(package?last_index_of(".")+1)?lower_case}/load${module}',
            async: false,//同步加载
            method: 'GET',
            params: {
                <#list primarys as item>
                '${item}': ${item}<#if item_has_next>,</#if>
                </#list>
            },
            callback: function (options, success, response) {
                if (success) {
                    var data = Ext.decode(response.responseText);
                    if (data.success) {
                        ${module?uncap_first} = data.result;
                    }
                }
            }
        });

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
                handler: _update${module}
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
                margin: '4'
            },
            items: [${updForm}]
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

    //初始化
    function _init() {
        for (var i = 0; i < Ext.data.StoreManager.getCount(); i++) {//检查是否所有自动加载数据已经全部加载完成
            if (Ext.data.StoreManager.getAt(i).isLoading()) {
                return;
            }
        }

        if (${module?uncap_first} == null) {
            Toast.alert('警告', '当前无${moduleDesc}, 请刷新页面重新选择', 5000);
            setTimeout(_close, 5000);
            return;
        }
        var formPanel = Ext.getCmp('formPanel').getForm();
        for (var key in ${module?uncap_first}) {//装载被修改数据到页面
            (formPanel.findField(key) != null) ? formPanel.findField(key).setValue(${module?uncap_first}[key]) : 0;
        }
        Ext.getBody().unmask();//取消页面遮盖
    }

    //修改${moduleDesc}
    function _update${module}() {
        Ext.getCmp('formPanel').getForm().submit({//提交表单
            url: AppUrl + '/${package?substring(package?last_index_of(".")+1)?lower_case}/update${module}',
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