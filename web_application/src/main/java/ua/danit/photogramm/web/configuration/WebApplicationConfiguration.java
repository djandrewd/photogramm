package ua.danit.photogramm.web.configuration;

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
@ComponentScan("ua.danit.photogramm.web.controllers")
public class WebApplicationConfiguration extends WebMvcConfigurerAdapter {

  private final ImageProperties imageProperties;

  @Autowired
  public WebApplicationConfiguration(ImageProperties imageProperties) {
    this.imageProperties = imageProperties;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/upload").setViewName("upload-page");
    registry.addViewController("/signin").setViewName("signin-page");
    registry.addViewController("/signup").setViewName("signup-page");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/styles/*.css").addResourceLocations("/WEB-INF/styles/");
    if (imageProperties.getFs().isEnabled()) {
      registry.addResourceHandler("/imgs/*.JPG", "/imgs/*.jpg")
              .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
              .addResourceLocations("file:" + imageProperties.getFs().getFilesDirectory());
    }
  }
}
