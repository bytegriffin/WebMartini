package com.bytegriffin.webmartini.init;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@Configuration 
@EnableTransactionManagement // 该注解类似于<tx:annotation-driven />
@MapperScan(basePackages="com.webmartini.mapper")
public class MyBatisConfig {

//    @Bean(destroyMethod = "shutdown", name="dataSource")
//    public DataSource dataSource() throws SQLException{
//    	HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName(Profiles.dbDriverClassName);
//        hikariConfig.setJdbcUrl(Profiles.dbUrl);
//        hikariConfig.setUsername(Profiles.dbUsername);
//        hikariConfig.setPassword(Profiles.dbPassword);
//        hikariConfig.setMinimumIdle(Profiles.dbMinIdle);
//        hikariConfig.setMaximumPoolSize(Profiles.dbMaxPoolSize);
//        hikariConfig.setConnectionTestQuery(Profiles.dbConnectionTestQuery);
//        hikariConfig.setAutoCommit(Boolean.valueOf(Profiles.dbAutoCommit));
//        hikariConfig.setMaxLifetime(Profiles.dbMaxLifeTime);
//        hikariConfig.setIdleTimeout(Profiles.dbIdleTimeout);
//        hikariConfig.setConnectionTimeout(Profiles.dbConnectionTimeout);
//        hikariConfig.addDataSourceProperty("cachePrepStmts", Profiles.dbCachePrepStmts);
//        hikariConfig.addDataSourceProperty("prepStmtCacheSize", Profiles.dbPrepStmtCacheSize);
//        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", Profiles.dbPrepStmtCacheSqlLimit);
//        hikariConfig.addDataSourceProperty("useServerPrepStmts", Profiles.dbUseServerPrepStmts);
//        //hikariConfig.setPoolName("dataSource");
//        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
//        return dataSource;
//    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource );
        sessionFactory.setFailFast(true);
//        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
//		// 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
		sessionFactory.setTypeAliasesPackage(SiteConfig.MYBATIS_PACKAGES);// 指定基包
//		sessionFactory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SiteConfig.MYBATIS_MAPPER_LOCATION));// 指定xml文件位置

		org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
		configuration.setCacheEnabled(true);
		configuration.setLazyLoadingEnabled(true);
		configuration.setMultipleResultSetsEnabled(true);
		configuration.setUseColumnLabel(true);
		configuration.setUseGeneratedKeys(false);
		configuration.setAutoMappingBehavior(AutoMappingBehavior.PARTIAL);
		configuration.setSafeRowBoundsEnabled(true);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setLocalCacheScope(LocalCacheScope.SESSION);
		configuration.setJdbcTypeForNull(JdbcType.OTHER);
		Set<String> lazyLoadTriggerMethods=new HashSet<String>();
		lazyLoadTriggerMethods.add("equals");
		lazyLoadTriggerMethods.add("clone");
		lazyLoadTriggerMethods.add("hashCode");
		lazyLoadTriggerMethods.add("toString");
		configuration.setLazyLoadTriggerMethods(lazyLoadTriggerMethods);
		configuration.setAggressiveLazyLoading(false);
		sessionFactory.setConfiguration(configuration);

		//分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("pageSizeZero", "true");
        properties.setProperty("reasonable", "false");
        properties.setProperty("params", "pageNum=pageHelperStart;pageSize=pageHelperRows;");
        properties.setProperty("supportMethodsArguments", "false");
        properties.setProperty("returnPageInfo", "none");
        pageHelper.setProperties(properties);
        sessionFactory.setPlugins(new Interceptor[]{pageHelper});

        return sessionFactory.getObject();
    }

//    @Bean
//    public PageHelper pageHelper(DataSource dataSource) {
//        log.info("注册MyBatis分页插件PageHelper");
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//        p.setProperty("offsetAsPageNum", "true");
//        p.setProperty("rowBoundsWithCount", "true");
//        p.setProperty("reasonable", "true");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }

}