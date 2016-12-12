package sms.springconfig;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@PropertySources({
        @PropertySource(value = "classpath:properties/application-common.properties"),
        @PropertySource(value = "classpath:properties/application-${spring.profiles.active}.properties",
                ignoreResourceNotFound = true)
})
public class DBConfig {

    @Autowired
    private Environment env;

    @Value("${db.init.schema}")
    private Resource INIT_SCHEMA_SCRIPT;

    @Value("${db.init.data}")
    private Resource INIT_DATA_SCRIPT;

    @Bean
    DataSource prodDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty("db.driver"));
        ds.setUrl(env.getProperty("db.url"));
        ds.setUsername(env.getProperty("db.username"));
        ds.setPassword(env.getProperty("db.password"));
        ds.setInitialSize(2);
        ds.setMaxActive(30);
        ds.setMaxIdle(10);
        ds.setMinIdle(3);
        ds.setMaxWait(30000);
        ds.setRemoveAbandoned(true);
        ds.setRemoveAbandonedTimeout(30);
        ds.setValidationQuery("SELECT 1");
        return ds;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {

        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
//        initializer.setDatabaseCleaner(databaseCleaner());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(INIT_SCHEMA_SCRIPT);
        populator.addScript(INIT_DATA_SCRIPT);
        return populator;
    }

    // Mybatis beans
    @Bean
    public SqlSessionFactory sqlSessionFactory(final DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources(env.getProperty("db.mapper.location")));
        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}