package ua.danit.photogramm.users.data.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring configuration for users services data module.
 *
 * @author Andrey Minov.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ua.danit.photogramm.users.data.dao",
    entityManagerFactoryRef = "usersEntityManagerFactory")
public class UsersDataConfiguration {
  @Value("${application.data.dialect:-}")
  private String dialect;
  @Value("${application.data.show_queries:false}")
  private String showQueries;

  /**
   * Entity manager factory bean.
   *
   * @param dataSource the data source of the application
   * @return the local container entity manager factory bean for managing JPA transactions.
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(DataSource dataSource) {
    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setDatabasePlatform(!dialect.equals("-") ? dialect : null);
    jpaVendorAdapter.setShowSql("true".equals(showQueries));

    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
    bean.setJpaVendorAdapter(jpaVendorAdapter);
    bean.setDataSource(dataSource);
    bean.setPersistenceUnitName("users");
    bean.setPackagesToScan("ua.danit.photogramm.users.data.model");
    return bean;
  }
}
