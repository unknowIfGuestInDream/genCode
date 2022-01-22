/**
 * request 网络请求工具
 * 更详细的 api 文档: https://github.com/umijs/umi-request
 */
import {Context, extend} from 'umi-request';
import {notification} from 'antd';
import {uuid} from "@/utils/uuid";

/**
 * 异常处理程序
 */
const errorHandler = (error: { response: any; }) => {
    const {response} = error;
    if (response && !response.success) {
        const errorText = response.message;
        notification.error({
            message: '请求错误:',
            description: errorText,
        });
    }

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
    let id = uuid();
    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded',
        'traceId': <#noparse>`${id}`</#noparse>
    };
    return (
        {
            url: url,
            options: {...options, headers: headers},
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
        if (data && !data.success) {
            notification.error({
                message: '请求错误:',
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
