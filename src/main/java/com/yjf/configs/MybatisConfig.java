package com.yjf.configs;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 余俊锋
 * @date 2020/10/15 15:33
 * @Description
 */
/*1.配置类
2.扫描Mapper包
3.导入spring配置类
*/
@Configuration
@MapperScan("com.yjf.dao")
@Import(SpringConfig.class)
public class MybatisConfig {
    //使用DruidDataSource
    @Bean
    public DruidDataSource getDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        InputStream is = SpringConfig.class.getClassLoader().getResourceAsStream("durid.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);//加载配置文件
            dataSource.configFromPropety(properties);//设置属性
            return dataSource;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("配置文件不存在，检查配置文件。。。。");
        }
        return null;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //设置驼峰命名转换
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        //开启懒加载
       // configuration.setLazyLoadingEnabled(true);
        //开缓存
        //configuration.setCacheEnabled(true);
        //设置使用配置对象
        factoryBean.setConfiguration(configuration);
        return factoryBean;
    }
}
