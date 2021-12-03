package ${package}.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ${moduleDesc}
 *
 * @author: ${author}
 * @date: ${date}
 * @since: 1.0
 */
public interface ${module}Service {

    /**
     * 加载${moduleDesc}
<#if loadNote! != ''>
     *
     * ${loadNote}
</#if>
     */
    Map<String, Object> load${module}(${loadInParams});

    /**
     * 查询${moduleDesc}
<#if selNote! != ''>
     *
     * ${selNote}
</#if>
     * @param current  当前页数
     * @param pageSize  每次显示数量
     */
    List<Map<String, Object>> select${module}(${selInParams}, Integer current, Integer pageSize);

    /**
     * 查询数量
<#if selNote! != ''>
     *
     * ${selNote}
</#if>
     */
    int count${module}(${selInParams});

    /**
     * 新增${moduleDesc}
<#if insNote! != ''>
     *
     * ${insNote}
</#if>
     */
    int insert${module}(${insInParams});

    /**
     * 修改${moduleDesc}
<#if updNote! != ''>
     *
     * ${updNote}
</#if>
     */
    int update${module}(${updInParams});

    /**
     * 删除${moduleDesc}
<#if delNote! != ''>
     *
     * ${delNote}
</#if>
     */
    int delete${module}(${delInParams});

<#if hasDelBatch>
    /**
    * 批量删除${moduleDesc}
    <#if delBatchNote! != ''>
    *
    * ${delBatchNote}
    </#if>
    */
    int delete${module}Batch(${delBatchInParams});

</#if>
}