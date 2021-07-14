/**
 * 修改grid背景色
 * 配合gridPanel的getRowClass属性使用
 * @param record
 * @param rowIndex
 * @param rowParams
 * @param store
 * @returns {string}
 */
function _changeRowClass(record, rowIndex, rowParams, store) {
    if (record.get('字段') === '') {
        return "x-grid-record-yellow";
    }
}

//未编译的过程加背景色
function _changeInValidRowClass(record, rowIndex, rowParams, store) {
    if (record.get('STATUS') === 'INVALID') {
        return "x-grid-record-red";
    }
}