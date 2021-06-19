/**
 * 修改grid背景色
 * 配合gridPanel的getRowClass属性使用
 * @param record
 * @param rowIndex
 * @param rowParams
 * @param store
 * @returns {string}
 */
function changeRowClass(record, rowIndex, rowParams, store) {
    if (record.get('字段') === '') {
        return "x-grid-record-yellow";
    }
}

//各单位月预算上报流程预算管理办公室(成本)处理页，下发Panel
function changeIssueRowClass(record, rowIndex, rowParams, store) {
    if (record.get('V_FLAG') != '未下发') {
        return "x-grid-record-gray";
    }
}

//月上报科目编辑列表
function changeBudgetMonthReportRowClass(record, rowIndex, rowParams, store) {
    if (record.get('V_INPUT') == '否') {
        return "x-grid-record-white";
    }
}

//月上报单位列表
function changeBudgetMonthOrgRowClass(record, rowIndex, rowParams, store) {
    if (record.get('V_FLAG') == '已发起') {
        return "x-grid-record-green";
    } else if (record.get('V_FLAG') == '未发起') {
        return "x-grid-record-red";
    }

}