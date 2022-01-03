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
            pageSize: 20,
            fields: [${common_storeParams!}],
            proxy: {
                url: AppUrl + '/${package?substring(package?last_index_of(".")+1)?lower_case}/select${module}',
                type: 'ajax',
                async: true,//false=同步.
                actionMethods: {
                    read: 'GET'
                },
                extraParams: {},
                reader: {
                    type: 'json',
                    root: 'result',
                    totalProperty: 'total'
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
                handler: <#if hasDelBatch>_delete${module}Batch<#else>_delete${module}</#if>
            }<#if hasExport>, {
                xtype: 'button',
                text: '导出',
                icon: 'public/image/btn/download.png',
                handler: _export${module}
            }</#if>]
        });

        var formPanel = Ext.create('Ext.Panel', {
            id: 'formPanel',
            layout: 'column',
            frame: true,
            defaults: {
                labelAlign: 'right',
                labelWidth: 100,
                inputWidth: 140,
                margin: '4,0,0,0'
            },
            items: [${common_selForm}]
        });

        var ${module?uncap_first}Panel = Ext.create('Ext.grid.Panel', {
            id: '${module?uncap_first}Panel',
            store: ${module?uncap_first}Store,
            columnLines: true,
            selModel: {
                selType: 'checkboxmodel',
                mode: <#if hasDelBatch!'SINGLE'>'SIMPLE'<#else>'SINGLE'</#if>
            },
            frame: true,
            columns: [{
                xtype: 'rownumberer',
                align: 'center',
                width: 50
            }, ${common_gridParams!}],
            viewConfig: {
                emptyText: '<div style="text-align: center; padding-top: 50px; font: italic bold 20px Microsoft YaHei;">没有数据</div>',
                enableTextSelection: true
            },
            dockedItems: [{
                xtype: 'pagingtoolbar',
                store: ${module?uncap_first}Store,
                dock: 'bottom',
                displayInfo: true
            }]
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
                items: [buttonPanel, formPanel]
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
        <#if common_selExtraParams?? && common_selExtraParams?length gt 0>
        ${module?uncap_first}Store.proxy.extraParams = {
            ${common_selExtraParams}
        };
        </#if>
        ${module?uncap_first}Store.load();
    }

    //新增${moduleDesc}页
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

    //修改${moduleDesc}页
    function _preUpdate${module}() {
        var records = Ext.getCmp('${module?uncap_first}Panel').getSelectionModel().getSelection();
        if (records.length !== 1) {
            Ext.MessageBox.alert('警告', '请选择一条待修改数据', Ext.MessageBox.WARNING);
            return;
        }
        var urlParam = <#if primarys?? && primarys?size gt 0><#list primarys as item>'<#if item_index = 0>?</#if>${item}=' + records[0].get('${item}')<#if item_has_next> + <#else>;</#if></#list><#else>'';</#if>
        returnValue = null;
        win = Ext.create('Ext.window.Window', {
            title: '修改${moduleDesc}',
            modal: true,
            autoShow: true,
            maximized: false,
            maximizable: true,
            width: document.documentElement.clientWidth * 0.6,
            height: document.documentElement.clientHeight * 0.8,
            html: '<iframe src="/${package?substring(package?last_index_of(".")+1)?lower_case}/preUpdate${module}' + urlParam + '" style="width: 100%; height: 100%;" frameborder="0"></iframe>',
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

    //删除${moduleDesc}
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
                        url: AppUrl + '/${package?substring(package?last_index_of(".")+1)?lower_case}/delete${module}',
                        async: false,
                        params: {
                            <#list primarys as item>
                            '${item}': records[0].get('${item}')<#if item_has_next>,</#if>
                            </#list>
                        },
                        callback: function (options, success, response) {
                            if (success) {
                                var data = Ext.decode(response.responseText);
                                if (data.success) {
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
    <#if hasDelBatch>

    //批量删除${moduleDesc}
    function _delete${module}Batch() {
        var records = Ext.getCmp('${module?uncap_first}Panel').getSelectionModel().getSelection();
        if (records.length === 0) {
            Ext.MessageBox.alert('警告', '请选择待删除数据', Ext.MessageBox.WARNING);
            return;
        }
        <#list primarys as item>
        var ${item}LIST = [];
        </#list>
        for (var i = 0, length = records.length; i < length; i++) {
            <#list primarys as item>
            ${item}LIST.push(records[i].data.${item});
            </#list>
        }
        Ext.MessageBox.show({
            title: '请确认',
            msg: '是否删除',
            buttons: Ext.MessageBox.YESNO,
            icon: Ext.MessageBox.QUESTION,
            fn: function (btn) {
                if (btn === 'yes') {
                    Ext.Ajax.request({
                        url: AppUrl + '/${package?substring(package?last_index_of(".")+1)?lower_case}/delete${module}Batch',
                        async: false,
                        params: {
                            <#list primarys as item>
                            '${item}LIST': ${item}LIST<#if item_has_next>,</#if>
                            </#list>
                        },
                        callback: function (options, success, response) {
                            if (success) {
                                var data = Ext.decode(response.responseText);
                                if (data.success) {
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
    </#if>
    <#if hasExport>

    //导出${moduleDesc}
    function _export${module}() {
        document.location.href = AppUrl + '/${package?substring(package?last_index_of(".")+1)?lower_case}/export${module}<#if common_exportParamUrl?? && common_exportParamUrl?length gt 0>?' +
            ${common_exportParamUrl};<#else>';</#if>
    }
    </#if>
</script>
</body>
</html>