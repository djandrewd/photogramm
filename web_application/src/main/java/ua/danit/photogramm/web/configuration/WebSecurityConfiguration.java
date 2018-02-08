package ua.danit.photogramm.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ua.danit.photogramm.users.data.services.UserManagementService;
import ua.danit.photogramm.web.services.PhotogrammUserDetailsService;

/**
 * Spring WEB security configuration.
 *
 * @author Andrey Minov
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserManagementService service;

  @Autowired
  public WebSecurityConfiguration(UserManagementService service) {
    this.service = service;
  }


  @Bean
  public PhotogrammUserDetailsService userDetailsService() {
    return new PhotogrammUserDetailsService(service);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .antMatchers("/styles/*.css").permitAll()
            .antMatchers("/signup", "/signup/save").anonymous()
            .anyRequest().authenticated()
        .and()
            .formLogin()
                .permitAll()
                .loginPage("/signin")
        .and()
           .logout()
           .logoutUrl("/signout")
           .invalidateHttpSession(true)
           .clearAuthentication(true)
           .permitAll();
  }
}
