/**
 * 清除缓存
 * @param name 缓存key值
 * @author TangLiang
 */
function _clearCaches(name) {
    Ext.Ajax.request({
        url: '/gen/clearCaches/' + name,
        async: false,
        method: 'DELETE',
        params: {},
        callback: function (options, success, response) {
            if (success) {
                Toast.alert('信息', '缓存清除成功', 2000);
            } else {
                Ext.MessageBox.alert('错误', '服务器错误', Ext.MessageBox.ERROR);
            }
        }
    });
}
