package sms.springconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DBConfig {
    @Value("classpath:sql/initial/schema.sql")
    private Resource H2_SCHEMA_SCRIPT;

    @Value("classpath:sql/initial/data.sql")
    private Resource H2_DATA_SCRIPT;

    @Bean
    DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        ds.setJdbcUrl("jdbc:log4jdbc:h2:~/test;AUTO_SERVER=TRUE");
        ds.setUsername("sa");
        ds.setPassword("123");
        return ds;
    }

    @Autowired
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
        populator.addScript(H2_SCHEMA_SCRIPT);
        populator.addScript(H2_DATA_SCRIPT);
        return populator;
    }

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(final DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:sql/mapper/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
