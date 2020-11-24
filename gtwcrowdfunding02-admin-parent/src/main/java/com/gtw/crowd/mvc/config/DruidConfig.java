package com.gtw.crowd.mvc.config;

/**
 * @author
 * @create 2020-10-23-0:40
 */
//@Configuration
public class DruidConfig {

//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    public DataSource druid(){
//        return new DruidDataSource();
//    }

    //配置监控
//    @Bean
//    public ServletRegistrationBean<Servlet> druidServlet() {
//        // 进行 druid 监控的配置处理
//        ServletRegistrationBean<Servlet> srb = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/**");
//        // 白名单
//        srb.addInitParameter("allow", "127.0.0.1");
//        // 黑名单
//        srb.addInitParameter("deny", "192.168.31.253");
//        // 用户名
//        srb.addInitParameter("loginUsername", "root");
//        // 密码
//        srb.addInitParameter("loginPassword", "root");
//        // 是否可以重置数据源
//        srb.addInitParameter("resetEnable", "false");
//        return srb;
//    }
//
//    @Bean
//    public FilterRegistrationBean<Filter> filterRegistrationBean() {
//        FilterRegistrationBean<Filter> frb = new FilterRegistrationBean<>();
//        frb.setFilter(new WebStatFilter());
//        // 所有请求进行监控处理
//        frb.addUrlPatterns("/*");
//        // 排除名单
//        frb.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");
//        return frb;
//    }
}
