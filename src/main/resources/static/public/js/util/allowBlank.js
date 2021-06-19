/**
 * 重写allowBank的属性配置
 在allowBlank为true的字段前加红色标识
 也可使用beforeLabelTextTpl属性代替
 */
Ext.override(Ext.form.field.Base, {
    initComponent: function () {
        if (this.allowBlank !== undefined && !this.allowBlank) {
            if (this.fieldLabel) {
                this.fieldLabel = '<font color=red>*</font>' + this.fieldLabel;
            }
        }
        this.callParent(arguments);
    }
});