package com.yjf.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author 余俊锋
 * @date 2020/10/15 15:32
 * @Description
 */

/*1.配置类
2.组件扫描
3.开启事务
4.配置事务Bean
*/
@Configuration
@ComponentScan("com.yjf.services")
@EnableTransactionManagement
//引入redis的配置类start
@Import(SpringCacheConfig.class)
//end---。
public class SpringConfig {

    //配置一个事务Bean
    public DataSourceTransactionManager getTranscation(DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
