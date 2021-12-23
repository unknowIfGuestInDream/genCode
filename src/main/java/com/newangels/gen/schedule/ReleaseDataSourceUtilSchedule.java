package com.newangels.gen.schedule;

import com.newangels.gen.domain.GenProperty;
import com.newangels.gen.factory.DataSourceUtilFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 定时清除数据源工厂数据
 * <p>
 * 使用GenProperty获取gen.schedul而非@Value获取
 * 主要是gen.schedul属性可以通过远程调用方式修改
 * 保证定时可以随时开关
 *
 * @author: TangLiang
 * @date: 2021/7/6 8:49
 * @since: 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class ReleaseDataSourceUtilSchedule {
    private final GenProperty genProperty;

    //每周五23点执行
    @Scheduled(cron = "0 0 23 ? * FRI")
    public void dataSourceUtilScheduled() {
        if (!genProperty.getSchedule()) return;
        DataSourceUtilFactory.removeAll();
        log.info("[{}]清除数据源工厂数据", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒")));
    }
}
