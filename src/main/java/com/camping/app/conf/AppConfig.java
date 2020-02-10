package com.camping.app.conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
 
@ComponentScan(basePackages="com.camping.app", excludeFilters= @Filter(type = FilterType.REGEX, pattern="com.camping.app.web"))
@ImportResource("classpath:/com/camping/app/conf/tx-context.xml")
@EnableScheduling
public class AppConfig {

  final static Logger logger = LogManager.getLogger(AppConfig.class);

  public AppConfig() {
    logger.debug("AppConfig 객체 생성...");
  }
}
