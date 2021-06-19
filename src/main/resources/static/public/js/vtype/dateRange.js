/**
 *日期选择范围
 * 使用:
 * {
     id:'beginDate1',
     fieldLabel:'开始日期',
     dateRange:{begin:'beginDate1',end:'endDate1'},//用于vtype类型dateRange
     vtype:'dateRange'
   },{
      id:'endDate1',
      fieldLabel:'结束日期',
      dateRange:{begin:'beginDate1',end:'endDate1'},//用于vtype类型dateRange
      vtype:'dateRange'
   }
 * @required Ext.QuickTips.init()
 * @author TangLiang
 */
Ext.apply(Ext.form.field.VTypes, {
    //验证方法
    dateRange: function (val, field) {
        var beginDate = null,//开始日期
            beginDateCmp = null,//开始日期组件
            endDate = null,//结束日期
            endDateCmp = null,//结束日期组件
            validStatus = true;//验证状态
        if (field.dateRange) {
            //获取开始时间
            if (!Ext.isEmpty(field.dateRange.begin)) {
                beginDateCmp = Ext.getCmp(field.dateRange.begin);
                beginDate = beginDateCmp.getValue();
            }
            //获取结束时间
            if (!Ext.isEmpty(field.dateRange.end)) {
                endDateCmp = Ext.getCmp(field.dateRange.end);
                endDate = endDateCmp.getValue();
            }
        }
        //如果开始日期或结束日期有一个为空则校验通过
        if (!Ext.isEmpty(beginDate) && !Ext.isEmpty(endDate)) {
            validStatus = beginDate <= endDate;
        }

        return validStatus;
    },
    //验证提示信息
    dateRangeText: '开始日期不能大于结束日期，请重新选择。'
});