package main.java.com.heun.trip.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import com.heun.trip.web.interceptor.LogInterceptor;

@ComponentScan("com.heun.trip.web")
@EnableWebMvc
public class DefaultWebConfig implements WebMvcConfigurer {
  
  @Autowired
  LogInterceptor logInterceptor;
  
  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    UrlPathHelper urlPathHelper = new UrlPathHelper();
    urlPathHelper.setRemoveSemicolonContent(false);
    configurer.setUrlPathHelper(urlPathHelper);
  }
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(logInterceptor).addPathPatterns("/json/auth/*").excludePathPatterns("/json/auth/authCheck");
  }
  
}









