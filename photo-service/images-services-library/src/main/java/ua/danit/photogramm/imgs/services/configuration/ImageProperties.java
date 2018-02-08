package ua.danit.photogramm.imgs.services.configuration;

import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Holds properties used for image processing, storing and loading.
 */
@ConfigurationProperties(prefix = "application.images")
@Validated
public class ImageProperties {
  private static final int MINIMUM_IMAGE_SIZE = 100;

  @Min(MINIMUM_IMAGE_SIZE)
  private int width;
  @Min(MINIMUM_IMAGE_SIZE)
  private int height;

  private FileSystemStoreProperties fs = new FileSystemStoreProperties();
  private AwsConfiguration s3 = new AwsConfiguration();

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public FileSystemStoreProperties getFs() {
    return fs;
  }

  public void setFs(FileSystemStoreProperties fs) {
    this.fs = fs;
  }

  public AwsConfiguration getS3() {
    return s3;
  }

  public void setS3(AwsConfiguration s3) {
    this.s3 = s3;
  }
}
