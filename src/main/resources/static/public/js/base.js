//消息框
Toast = function () {
    var toastContainer;

    function createMessageBar(title, msg) {
        return '<div class="x-message-box" style="text-align: center; color: #666;"><div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div><div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc" style="font: bold 15px Microsoft YaHei;">' + title + ' : ' + msg + '</div></div></div><div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div></div>';
    }

    return {
        alert: function (title, msg, delay) {
            if (!toastContainer) {
                toastContainer = Ext.DomHelper.insertFirst(document.body, {
                    id: 'toastContainer',
                    style: 'position: absolute; left: 0; right: 0; margin: auto; width: 360px; z-index: 20000; background: #87CEFA; '
                }, true);
            }

            var message = Ext.DomHelper.append(toastContainer, createMessageBar(title, msg), true);
            message.hide();
            message.slideIn('t').ghost("t", {
                delay: delay,
                remove: true
            });
        }
    };
}();

//获取路径
function getPath() {
    var _location = document.location.toString();
    var applicationNameIndex = _location.indexOf('/',
        _location.indexOf('://') + 3);
    var applicationName = _location.substring(0, applicationNameIndex) + '/';
    var webFolderIndex = _location.indexOf('/', _location
            .indexOf(applicationName)
        + applicationName.length);
    var webFolderFullPath = _location.substring(0, webFolderIndex);
    return webFolderFullPath;
}

//当前项目路径
var AppUrl = getPath() + '/';
var win;//窗口
var returnValue;//子页返回值
//返回成功信息
var SUCCESS = 'success';
//必填样式 配合afterLabelTextTpl/beforeLabelTextTpl使用
var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';

/**
 * 子页关闭方法
 * @private
 */
function _close() {
    parent.win.close();
}

/**
 * gridpanel数据提示框显示全部信息
 * 需要开启 Ext.QuickTips.init();
 * 使用方式
 * renderer: _viewValueTip
 * @author TangLiang
 * @param value
 * @param metaData
 * @param record
 * @param rowIndex
 * @param colIndex
 * @returns {*}
 * @private
 */
function _viewValueTip(value, metaData, record, rowIndex, colIndex) {
    metaData.tdAttr = 'qclass="x-tip" data-qwidth="300" data-qtip="'
        + (Ext.isEmpty(value) ? '' : value) + '"';
    return value;
}

/**
 * store增加自定义数据
 * @private
 */
function _addCustomizeData(store, code, name) {
    store.insert(0, {CODE_: code, NAME_: name});
}

/**
 * store增加空数据
 * @private
 */
function _addEmptyData(store) {
    _addCustomizeData(store, '', '全部');
}

/**
 * 数据加载
 * @param store 需要刷新的store
 * @param extraParams store请求参数 如 {'V_IP': V_IP}
 * @private
 */
function _loadStore(store, extraParams) {
    store.proxy.extraParams = extraParams;
    store.load();
}

/**
 * 新窗口打开, 代替window.open
 * 通过创建a标签以及单击a标签事件完成
 * @param id a标签id
 * @param href a标签路径
 * @private
 */
function _openLink(id, href) {
    var a = document.createElement("a");
    a.id = id;
    a.target = '_blank';
    a.href = href;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
}

//复制内容到剪切板
function _copyText(text) {
    var tag = document.createElement('textarea');
    tag.setAttribute('id', 'cp_hgz_textarea');
    tag.value = text;
    document.getElementsByTagName('body')[0].appendChild(tag);
    document.getElementById('cp_hgz_textarea').select();
    document.execCommand('copy');
    document.getElementById('cp_hgz_textarea').remove();
    Toast.alert('信息', '已复制到剪切板', 2000);
}

