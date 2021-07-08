package com.newangels.gen.schedule;

import com.newangels.gen.factory.DataSourceUtilFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 定时清除数据源工厂数据
 *
 * @author: TangLiang
 * @date: 2021/7/6 8:49
 * @since: 1.0
 */
@Slf4j
@Component
public class ReleaseDataSourceUtilSchedule {

    //每周五23点执行
    @Scheduled(cron = "0 0 23 ? * FRI")
    public void dataSourceUtilScheduled() {
        DataSourceUtilFactory.removeAll();
        log.info("[{}]清除数据源工厂数据", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒")));
    }
}
