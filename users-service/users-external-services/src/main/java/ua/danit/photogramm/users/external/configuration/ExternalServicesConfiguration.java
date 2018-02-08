package ua.danit.photogramm.users.external.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.danit.photogramm.users.data.configuration.UserServicesConfiguration;
import ua.danit.photogramm.users.data.configuration.UsersDataConfiguration;

/**
 * External configuration for Spring user services.
 *
 * @author Andrey Minov
 */
@Configuration
@Import(
    {UsersDataConfiguration.class, UserServicesConfiguration.class, SecurityConfiguration.class})
public class ExternalServicesConfiguration {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
