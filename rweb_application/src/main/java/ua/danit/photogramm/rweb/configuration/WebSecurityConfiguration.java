package ua.danit.photogramm.rweb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import ua.danit.photogramm.rweb.services.PhotogrammUserDetailsService;
import ua.danit.photogramm.users.data.services.UserManagementService;

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
    http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authorizeRequests()
            .antMatchers("/dist/**").permitAll()
            .antMatchers("/signup", "/signup/save").anonymous()
            .antMatchers("/search/exists/**").anonymous()
            .antMatchers("/images/tagged/**").fullyAuthenticated()
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

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
