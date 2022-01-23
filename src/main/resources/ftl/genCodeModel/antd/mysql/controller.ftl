package ${package}.controller;

import ${package}.annotation.Log;
import ${package}.base.BaseUtils;
import ${package}.service.${module}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${moduleDesc}
 *
 * @author: ${author}
 * @date: ${date}
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
public class ${module}Controller {
    private final ${module}Service ${module?uncap_first}Service;

    /**
     * 加载${moduleDesc}
     */
    @GetMapping("load${module}")
    @Log
    public Map<String, Object> load${module}(${loadInParams}) {
        return BaseUtils.loadSuccess(${module?uncap_first}Service.load${module}(${loadSqlParams}));
    }

    /**
     * 查询${moduleDesc}
     */
    @GetMapping("select${module}")
    @Log
    public Map<String, Object> select${module}(${selConInParams}<#if selConInParams?length gt 1>, </#if>Integer current, Integer pageSize) {
        List<Map<String, Object>> list = ${module?uncap_first}Service.select${module}(${selSqlParams}<#if selSqlParams?length gt 1>, </#if>current, pageSize);
        int total = 0;
        if (pageSize != null && pageSize > 0) {
            total = ${module?uncap_first}Service.count${module}(${selSqlParams});
        }
        return BaseUtils.success(list, total);
    }

    /**
     * 新增${moduleDesc}
     */
    @PostMapping("insert${module}")
    @Log
    public Map<String, Object> insert${module}(${insConInParams}) {
        if (${module?uncap_first}Service.insert${module}(${insConSqlParams}) == 0) {
            return BaseUtils.failed("新增${moduleDesc}失败");
        }
        return BaseUtils.success();
    }

    /**
     * 修改${moduleDesc}
     */
    @PostMapping("update${module}")
    @Log
    public Map<String, Object> update${module}(${updInParams}) {
        if (${module?uncap_first}Service.update${module}(${updSqlParams}) == 0) {
            return BaseUtils.failed("修改${moduleDesc}失败");
        }
        return BaseUtils.success();
    }

    /**
     * 删除${moduleDesc}
     */
    @PostMapping("delete${module}")
    @Log
    public Map<String, Object> delete${module}(${delInParams}) {
        if (${module?uncap_first}Service.delete${module}(${delSqlParams}) == 0) {
            return BaseUtils.failed("删除${moduleDesc}失败");
        }
        return BaseUtils.success();
    }
<#if hasDelBatch>

    /**
     * 批量删除${moduleDesc}
     */
    @PostMapping("delete${module}Batch")
    @Log
    public Map<String, Object> delete${module}Batch(${delBatchControllerParams}) {
        if (${module?uncap_first}Service.delete${module}Batch(${delBatchSqlParams}) == 0) {
            return BaseUtils.failed("批量删除${moduleDesc}失败");
        }
        return BaseUtils.success();
    }
</#if>
<#if hasExport>

    /**
     * 导出${moduleDesc}
     */
    @GetMapping("export${module}")
    @Log
    public void export${module}(${selConInParams}<#if selConInParams?length gt 1>, </#if>HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = ${module?uncap_first}Service.select${module}(${selSqlParams}<#if selSqlParams?length gt 1>, </#if>null, null);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(${mapSize});
        ${exportLinkHashMap}
        BaseUtils.dealCommonExcel(wb, sheet, list, linkedHashMap);
        BaseUtils.download(wb, "${moduleDesc}.xls", request, response);
    }
</#if>

}
