package ua.danit.photogramm.imgs.services.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.danit.photogramm.imgs.services.store.FileSystemStorageService;
import ua.danit.photogramm.imgs.services.store.ImagesStorageService;
import ua.danit.photogramm.imgs.services.store.S3StorageService;

/**
 * Spring configuration for files internal services.
 *
 * @author Andrey Minov
 */
@Configuration
@EnableConfigurationProperties(ImageProperties.class)
@ComponentScan("ua.danit.photogramm.imgs.services.internal")
public class ImageServicesConfiguration {

  private final ImageProperties properties;

  /**
   * Instantiates a new Image services configuration.
   *
   * @param properties the properties
   */
  @Autowired
  public ImageServicesConfiguration(ImageProperties properties) {
    this.properties = properties;
  }

  /**
   * Create new images storage service.
   *
   * @return the images storage service
   */
  @Bean
  public ImagesStorageService imagesStorageService() {
    if (properties.getFs().isEnabled()) {
      return new FileSystemStorageService(properties.getFs());
    }
    if (properties.getS3().isEnabled()) {
      return new S3StorageService(properties.getS3());
    }
    throw new IllegalArgumentException("There must be at least one storage service enabled!");
  }
}
