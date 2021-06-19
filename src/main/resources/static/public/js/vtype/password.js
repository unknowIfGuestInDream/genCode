/**
 * 确认密码验证
 * 使用
 items:[{
    fieldLabel: "密码",
    id: "pass1",
   }, {
    fieldLabel: "确认密码",
    id: "pass2",
    vtype: "password",//自定义的验证类型
    //vtypeText: "两次密码不一致！",
    confirmTo: "pass1",//要比较的另外一个的组件的id
}
 * @required Ext.QuickTips.init()
 * @author TangLiang
 */
Ext.apply(Ext.form.VTypes, {
    password: function (val, field) {//val指这里的文本框值，field指这个文本框组件，大家要明白这个意思
        if (field.confirmTo) {//confirmTo是我们自定义的配置参数，一般用来保存另外的组件的id值
            var pwd = Ext.get(field.confirmTo);//取得confirmTo的那个id的值
            return (val == pwd.getValue());
        }
        return true;
    },
    passwordText: '两次密码不一致！',
});