<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>查看${moduleDesc}</title>
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
    var ${module?uncap_first};//加载对象
    Ext.onReady(function () {
        Ext.getBody().mask('加载中...');

        Ext.Ajax.request({//加载对象
            url: AppUrl + '/${package?substring(package?last_index_of(".")+1)?lower_case}/load${module}',
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

        var buttonPanel = Ext.create('Ext.Panel', {
            id: 'buttonPanel',
            defaults: {
                style: 'margin: 2px;'
            },
            items: [{
                xtype: 'button',
                text: '关闭',
                icon: 'public/image/btn/close.png',
                handler: _close
            }]
        });

        var formPanel = Ext.create('Ext.form.Panel', {
            id: 'formPanel',
            layout: 'column',
            frame: true,
            autoScroll: true,
            defaults: {
                labelAlign: 'right',
                labelWidth: 150,
                inputWidth: 300,
                margin: '4,0,0,0'
            },
            items: [${common_viewForm}]
        });

        Ext.create('Ext.container.Viewport', {
            layout: {
                type: 'border',
                regionWeights: {
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
                layout: 'fit',
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

        if (${module?uncap_first} == null || ${module?uncap_first}.length === 0) {
            Toast.alert('错误', '加载数据失败...', 5000);
            setTimeout(_close, 5000);
            return;
        }

        var form = Ext.getCmp('formPanel').getForm();
        for (var key in ${module?uncap_first}) {
            (form.findField(key) != null) ? form.findField(key).setValue(${module?uncap_first}[key]) : 0;
        }
        Ext.getBody().unmask();
    }
</script>
</body>
</html>