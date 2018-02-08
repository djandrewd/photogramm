package ua.danit.photogramm.imgs.services.configuration;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import javax.annotation.PostConstruct;

public class AwsConfiguration {
  private boolean enabled;
  private String accessKey;
  private String secretKey;
  private String bucketName;
  private String region;
  private String format;

  /**
   * Validate properties content.
   * */
  @PostConstruct
  public void validate() {
    if (enabled) {
      checkArgument(!isNullOrEmpty(accessKey));
      checkArgument(!isNullOrEmpty(secretKey));
      checkArgument(!isNullOrEmpty(bucketName));
      checkArgument(!isNullOrEmpty(region));
      checkArgument(!isNullOrEmpty(format));
    }
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }
}
