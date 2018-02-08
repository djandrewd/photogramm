package ua.danit.photogramm.web.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA data configuration for default Spring Boot entity manager.
 *
 * @author Andrey Minov
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"ua.danit.photogramm.imgs.dao", "ua.danit.photogramm.users.data.dao"})
@EntityScan(
    basePackages = {"ua.danit.photogramm.users.data.model", "ua.danit.photogramm.imgs.model"})
public class WebDataConfiguration {
}
