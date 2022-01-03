package com.newangels.gen.controller;

import com.newangels.gen.base.BaseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 项目关闭接口
 *
 * @author: 唐 亮
 * @date: 2021/12/22 18:54
 * @since: 1.0
 */
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "gen.shutdown", name = "enabled", havingValue = "true")
public class ShutDownController implements ApplicationContextAware {
    private ApplicationContext context;

    /**
     * 关闭接口密钥
     */
    @Value("${gen.shutdown.password}")
    private String password;

    /**
     * 关闭项目
     */
    @GetMapping({"shutdown/{passwd}", "shutdown"})
    public Map<String, Object> shutDownContext(@PathVariable(required = false) String passwd) {
        if (passwd == null) {
            return BaseUtils.failed("请携带密钥");
        }
        if (!password.equals(passwd)) {
            return BaseUtils.failed("密钥错误");
        }
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) context;
        ctx.close();
        return BaseUtils.success();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
