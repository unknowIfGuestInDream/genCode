import Qs from "qs";
import request from "@/utils/request";

//加载${moduleDesc}
export async function load${module}(params: any) {
  const res = request('/${package?substring(package?last_index_of(".")+1)?lower_case}/load${module}', {
    method: 'GET',
    params: {...params}
  });
  return res;
}

//查询${moduleDesc}
export async function select${module}(params: any) {
  /*
  关于组件读取数据-请求后端成功返回的json串，默认data，不叫data，页面看不到数据
  如果是 GET 请求，请将 data 修改成 params
   */
  const res = request('/${package?substring(package?last_index_of(".")+1)?lower_case}/select${module}', {
    method: 'GET',
    params: {...params}
  });
  return res;
}

//新增${moduleDesc}
export async function insert${module}(params: any) {
  const res = request('/${package?substring(package?last_index_of(".")+1)?lower_case}/insert${module}', {
    method: 'POST',
    data: Qs.stringify(params),
  });
  return res;
}

//修改${moduleDesc}
export async function update${module}(params: any) {
  const res = request('/${package?substring(package?last_index_of(".")+1)?lower_case}/update${module}', {
    method: 'POST',
    data: Qs.stringify(params),
  });
  return res;
}

//删除${moduleDesc}
export async function delete${module}(params: any) {
  const res = request('/${package?substring(package?last_index_of(".")+1)?lower_case}/delete${module}', {
    method: 'POST',
    data: Qs.stringify(params)
  });
  return res;
}
<#if hasDelBatch>

//批量删除${moduleDesc}
export async function delete${module}Batch(params: any) {
  const res = request('/${package?substring(package?last_index_of(".")+1)?lower_case}/delete${module}Batch', {
    method: 'POST',
    data: Qs.stringify(params)
  });
  return res;
}
</#if>





