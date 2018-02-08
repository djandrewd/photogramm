package ua.danit.photogramm.rweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import ua.danit.photogramm.rweb.configuration.WebApplicationConfiguration;
import ua.danit.photogramm.rweb.configuration.WebSecurityConfiguration;

/**
 * WEB application entry point of Photogramm system.
 *
 * @author Andrey Minov
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@Import({WebApplicationConfiguration.class, WebSecurityConfiguration.class})
public class PhotogrammWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhotogrammWebApplication.class, args);
  }
}
