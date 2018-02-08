package ua.danit.photogramm.users.external;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ua.danit.photogramm.users.data.configuration.UserServicesConfiguration;
import ua.danit.photogramm.users.external.configuration.ExternalServicesConfiguration;

/**
 * Uses external services String configuration.
 *
 * @author Andrey Minov
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("ua.danit.photogramm.users.external.resources")
@Import({ExternalServicesConfiguration.class})
public class UsersExternalServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UsersExternalServiceApplication.class, args);
  }
}
