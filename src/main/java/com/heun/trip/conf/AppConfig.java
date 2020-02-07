package com.heun.trip.conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
 
@ComponentScan(basePackages="com.heun.trip", excludeFilters= @Filter(type = FilterType.REGEX, pattern="com.heun.trip.web"))
@ImportResource("classpath:/com/heun/trip/conf/tx-context.xml")
@EnableScheduling
public class AppConfig {

  final static Logger logger = LogManager.getLogger(AppConfig.class);

  public AppConfig() {
    logger.debug("AppConfig 객체 생성...");
  }
}
