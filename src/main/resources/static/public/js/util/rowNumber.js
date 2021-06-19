/**
 * 让所有Grid的行号在翻页后连续递增并且其宽度自动设为30
 * @author TangLiang
 */
Ext.override(Ext.grid.RowNumberer, {
    width: 30,
    renderer: function (value, cellmeta, record, rowIndex, columnIndex, store) {
        return store.lastOptions.params.start + rowIndex + 1;
    }
});