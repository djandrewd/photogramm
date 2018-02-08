package ua.danit.photogramm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import ua.danit.photogramm.web.configuration.WebApplicationConfiguration;
import ua.danit.photogramm.web.configuration.WebSecurityConfiguration;

/**
 * WEB application entry point of Photogramm system.
 *
 * @author Andrey Minov
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@Import({WebApplicationConfiguration.class, WebSecurityConfiguration.class})
public class PhotogrammFTLWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhotogrammFTLWebApplication.class, args);
  }
}
