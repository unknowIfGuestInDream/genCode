/**
 * 自定义电话号码的vtype验证
 * @required Ext.QuickTips.init()
 * @author TangLiang
 */
Ext.apply(Ext.form.field.VTypes, {
    phone: function (v) {
        //规则区号（3-4位数字）-电话号码（7-8位数字）
        return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})$/.test(v);
    },
    phoneText: '请输入有效的电话号码',
    phoneMask: /[\d-]/i//只允许输入数字和-号
});