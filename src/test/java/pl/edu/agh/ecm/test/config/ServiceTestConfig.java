package pl.edu.agh.ecm.test.config;

import org.dbunit.DataSourceDatabaseTester;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 07.10.12
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */

@Configuration
@ImportResource("classpath:datasource-tx-jpa.xml")
@ComponentScan(basePackages = {"pl.edu.agh.ecm.service.jpa"})
@Profile("test")
public class ServiceTestConfig {

    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").build();

    }

    @Bean(name = "dataBaseTester")
    public DataSourceDatabaseTester dataSourceDatabaseTester(){
        DataSourceDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource());
        return databaseTester;
    }
}
