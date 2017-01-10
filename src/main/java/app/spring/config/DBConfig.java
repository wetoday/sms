package app.spring.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySources({
        @PropertySource(value = "classpath:properties/application-default.properties"),
        @PropertySource(value = "classpath:properties/application-${spring.profiles.active}.properties",
                ignoreResourceNotFound = true)
})
public class DBConfig {

    private final Environment env;

    @Value("${db.init.schema}")
    private Resource INIT_SCHEMA_SCRIPT;

    @Value("${db.init.data}")
    private Resource INIT_DATA_SCRIPT;

    @Autowired
    public DBConfig(Environment env) {
        this.env = env;
    }

    // DataSource
    @Bean
    DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(env.getProperty("db.driver"));
        ds.setJdbcUrl(env.getProperty("db.url"));
        ds.setUsername(env.getProperty("db.username"));
        ds.setPassword(env.getProperty("db.password"));

        // 최대 커넥션 개수 (기본값: 10개) - 가장 중요
        ds.setMaximumPoolSize(10);
        // 커넥션 타임아웃 값 (기본값: 30초)
        ds.setConnectionTimeout(30000);
        // 커넥션 최대 생명 시간 (기본값: 30분), 30초 이상 DB 기본 타임아웃 값 이하로 설정
        ds.setMaxLifetime(1800000);
        // 유휴 커넥션 타임아웃 값 (기본값: 10분), MinimumIdle값이 MaximumPoolSize이하로 설정되어있을 때만 유효
        ds.setIdleTimeout(600000);
        // 최소 유휴 커넥션 개수 (기본값: MaximumPoolSize), 설정안하는 것을 권장
        // ds.setMinimumIdle(10);

        // Caching (MySQL Recommended)
        ds.addDataSourceProperty("cachePrepStmts", "true");
        ds.addDataSourceProperty("prepStmtCacheSize", "250");
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds.addDataSourceProperty("useServerPrepStmts", "true");
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

    // Transaction
    @Bean
    public PlatformTransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // Mybatis
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