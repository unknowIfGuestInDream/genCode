/**
 * request 网络请求工具
 * 更详细的 api 文档: https://github.com/umijs/umi-request
 */
import {Context, extend} from 'umi-request';
import {notification} from 'antd';
import {uuid} from "@/utils/uuid";
import Qs from "qs";

/**
 * 异常处理程序
 * 使用中发现参数全为undefined，当前errorHandler不能起到错误处理的作用
 */
const errorHandler = (error: any) => {
    const {response} = error;
    // if (response && !response.success) {
    //   const errorText = response.message;
    //   notification.error({
    //     message: '请求错误:',
    //     description: errorText,
    //   });
    // }

    return response;
};

const request = extend({
    errorHandler,
    // 默认错误处理
    credentials: 'include', // 默认请求是否带上cookie
});

// request拦截器, 改变url 或 options.
// @ts-ignore
request.interceptors.request.use(async (url, options) => {
    //拦截request全局加入V_PERCODE值
    const person = {V_PERCODE: 'admin'};
    if (options.method === 'get') {
      options.params = {...options.params, ...person};
    } else {
      const newFields = {...Qs.parse(options.data), ...person};
      options.data = Qs.stringify(newFields);
    }
    let id = uuid();
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
        'traceId': <#noparse>`${id}`</#noparse>
    };
    return (
        {
            url: url,
            options: {...options, headers: headers}
        }
    );
}, {global: false});

// response拦截器, 处理response
// request.interceptors.response.use((response, options) => {
//   return response;
// });

//全局处理请求结果异常
// @ts-ignore
request.interceptors.response.use(async (response) => {
    try {
        const data = await response.clone().json();
        if (data && data.status === 404) {
            notification.error({
                message: '未找到当前服务',
                description: data.path
            });
            return
        } else if (data && data.status === 403) {
            notification.error({
                message: '请求失败, 访问被禁止',
                description: data.error
            });
            return
        } else if (data && data.status === 500) {
            notification.error({
                message: '服务器发生错误，请检查服务器。',
                description: data.error
            });
            return
        } else if (data && data.status === 502) {
            notification.error({
                message: '网关错误。',
                description: data.error
            });
            return
        } else if (data && data.status === 503) {
            notification.error({
                message: '服务不可用，服务器暂时过载或维护。',
                description: data.error
            });
            return
        } else if (data && data.status === 504) {
            notification.error({
                message: '网关超时, 请稍后再试或者联系系统管理员',
                description: data.error
            });
            return
        } else if (data && !data.success) {
            notification.error({
                message: '请求失败',
                description: data.message,
            });
            return
        }
    } catch (error) {

    }
    return response
});

// 日志输出(开发环境使用，生产环境推荐关闭)
const loggerMiddleware = async (ctx: Context, next: () => void) => {
    // 输出请求信息
    const {req} = ctx;
    console.log('request：', req.url, ' ', req.options);
    await next();
    // eslint-disable-next-line eqeqeq
    const success = (ctx.res.success ? ctx.res.success : true);
    // 映射
    ctx.res = {
        success,
        message: success ? '' : ctx.res.message,
        ...ctx.res
    };
    // 输出响应信息
    console.log('response：', ctx.res);
};

request.use(loggerMiddleware);

export default request;
