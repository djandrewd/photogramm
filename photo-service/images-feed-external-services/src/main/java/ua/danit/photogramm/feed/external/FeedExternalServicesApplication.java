package ua.danit.photogramm.feed.external;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ua.danit.photogramm.imgs.configuration.ImagesDataConfiguration;
import ua.danit.photogramm.imgs.services.configuration.ImageServicesConfiguration;

/**
 * Spring Boot application for external and internal usage with
 * image feed.
 *
 * @author Andrey Minov
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@EnableCaching
@Import({ImagesDataConfiguration.class, ImageServicesConfiguration.class})
@ComponentScan("ua.danit.photogramm.feed.external.resources")
public class FeedExternalServicesApplication {
  public static void main(String[] args) {
    SpringApplication.run(FeedExternalServicesApplication.class, args);
  }
}
