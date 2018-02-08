package ua.danit.photogramm.imgs.services.internal;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import ua.danit.photogramm.imgs.configuration.ImagesDataConfiguration;
import ua.danit.photogramm.imgs.dao.CommentsDao;
import ua.danit.photogramm.imgs.dao.ImagesDao;

/**
 * Spring test configuration for HSQLDB.
 *
 * @author Andrey Minov
 */
@Configuration
@PropertySource("classpath:test.properties")
@Import(ImagesDataConfiguration.class)
public class TestConfiguration {

  @Value("${db.url}")
  private String url;
  @Value("${db.username}")
  private String username;
  @Value("${db.password}")
  private String password;
  @Value("${db.driver}")
  private String driverClassname;
  @Value("${db.dialect}")
  private String dialect;
  @Value("classpath:create_script.sql")
  private Resource scriptResource;

  /**
   * Data source basic data source.
   *
   * @return spring bean containing data source of application.
   */
  @Bean(destroyMethod = "close")
  public BasicDataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDriverClassName(driverClassname);
    return dataSource;
  }

  /**
   * Transaction manager jpa transaction manager.
   *
   * @param entityManagerFactory the entity manager factory for creating JPA entity managers.
   * @return the jpa transaction manager for managing transactions of application correctly.
   */
  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  @Bean
  public ImagesSocialService imagesSocialService(ImagesDao imagesDao, CommentsDao commentsDao) {
    return new ImagesSocialService(imagesDao, commentsDao);
  }

  @Bean
  public ImagesDataService imagesDataService(ImagesDao imagesDao) {
    return new ImagesDataService(imagesDao, 30);
  }

  //   This is bean responsible to population of HSQL Db on start.
  @Bean
  public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(scriptResource);

    DataSourceInitializer initializer = new DataSourceInitializer();
    initializer.setDataSource(dataSource);
    initializer.setDatabasePopulator(populator);
    return initializer;
  }
}
