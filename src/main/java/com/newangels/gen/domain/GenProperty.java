package com.newangels.gen.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目自定义配置
 *
 * @author: TangLiang
 * @date: 2021/12/13 17:16
 * @since: 1.0
 */
@Component
@ConfigurationProperties("gen")
@Data
public class GenProperty {
    private Boolean isdb;
    private Boolean rmi;
    private String version;
    private String author;
    private Boolean schedule;
    private Boolean async;
}
