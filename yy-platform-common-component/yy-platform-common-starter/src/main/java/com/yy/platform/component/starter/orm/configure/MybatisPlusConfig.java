package com.yy.platform.component.starter.orm.configure;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Auther: zhanqingqi
 * @Date: 2019/12/12 10:42
 * @Description:
 */
@Configuration
@MapperScan({"com.yy.platform.**.dao"})
@AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
public class MybatisPlusConfig {

    /**
     * 配置mybatis plus 分页插件
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置响应分页属性
        return paginationInterceptor;
    }

    /**
     * 配置mybatis plus 乐观锁插件
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    @Primary
    public MybatisPlusProperties initMybatisPlusProperties(MybatisPlusProperties mybatisPlusProperties){
        /*MybatisPlusProperties mybatisPlusProperties = new MybatisPlusProperties();
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(new GlobalConfig.DbConfig());
        globalConfig.setBanner(false);
        mybatisPlusProperties.setGlobalConfig(globalConfig);*/
        mybatisPlusProperties.getGlobalConfig().setBanner(false);
        return mybatisPlusProperties;
    }

    /**
     * 自定义主键
     * @return
     */
    /*@Bean
    public IdentifierGenerator idGenerator() {
        return new IdentifierGenerator() {
            @Override
            public Long nextId(Object entity) {
                return System.currentTimeMillis();
            }
        };
    }*/


    /**
     * 自动填充字段定义
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MyMetaObjectHandler();
    }

    /*public IKeyGenerator keyGenerator(){
        return new IKeyGenerator() {
            @Override
            public String executeSql(String incrementerName) {
                return null;
            }
        }
    }*/

//
//    @Bean
//    public MapperScannerConfigurer initMapperScannerConfigurer(){
//        MapperScannerConfigurer mapperScanner = new MapperScannerConfigurer();
//        mapperScanner.setBasePackage("com.yuyin.jlx.mall.**.dao");
//        mapperScanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        return mapperScanner;
//    }
//
//
//    @Bean(name = "sqlSessionFactory")
//    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSource dataSource) throws IOException {
//        MybatisSqlSessionFactoryBean result = new MybatisSqlSessionFactoryBean();
//        result.setDataSource(dataSource);
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        result.setMapperLocations(resolver.getResources("classpath*:/mapper/*Mapper.xml"));
//        result.setTypeAliasesPackage("com.yuyin.jlx.mall.entity");
//        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setDbConfig(new GlobalConfig.DbConfig());
//        result.setGlobalConfig(globalConfig);
//        return result;
//    }
}
