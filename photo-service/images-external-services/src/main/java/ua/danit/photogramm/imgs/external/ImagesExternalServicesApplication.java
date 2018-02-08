package ua.danit.photogramm.imgs.external;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ua.danit.photogramm.imgs.configuration.ImagesDataConfiguration;
import ua.danit.photogramm.imgs.services.configuration.ImageServicesConfiguration;

/**
 * Images services Spring boot application.
 *
 * @author Andrey Minov
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@Import({ImagesDataConfiguration.class, ImageServicesConfiguration.class})
@ComponentScan("ua.danit.photogramm.imgs.external.resources")
public class ImagesExternalServicesApplication {

  public static void main(String[] args) {
    SpringApplication.run(ImagesExternalServicesApplication.class, args);
  }
}
