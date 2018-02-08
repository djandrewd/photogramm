package ua.danit.photogramm.rweb.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ua.danit.photogramm.imgs.services.configuration.ImageProperties;
import ua.danit.photogramm.imgs.services.configuration.ImageServicesConfiguration;
import ua.danit.photogramm.users.data.configuration.UserServicesConfiguration;

/**
 * Spring WEB specific application configuration.
 *
 * @author Andrey Minov
 */
@Configuration
@Import(
    {WebDataConfiguration.class, UserServicesConfiguration.class, ImageServicesConfiguration.class})
@EnableJpaRepositories(basePackages = {"ua.danit.photogramm.imgs.dao",})
@ComponentScan("ua.danit.photogramm.rweb.controllers")
public class WebApplicationConfiguration extends WebMvcConfigurerAdapter {

  @Autowired
  private ImageProperties imageProperties;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/signin").setViewName("index");
    registry.addViewController("/signup").setViewName("index");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (imageProperties.getFs().isEnabled()) {
      registry.addResourceHandler("/imgs/*.JPG", "/imgs/*.jpg")
              .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
              .addResourceLocations("file:" + imageProperties.getFs().getFilesDirectory());
    }
  }
}
