package ua.danit.photogramm.imgs.services.configuration;

import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.file.Files.isDirectory;

import java.nio.file.Paths;
import javax.annotation.PostConstruct;

public class FileSystemStoreProperties {
  private boolean enabled;
  private String filesDirectory;
  private String storageUrl;
  private String format;

  /**
   * Validate the source.
   * */
  @PostConstruct
  public void validate() {
    if (enabled) {
      checkArgument(isDirectory(Paths.get(filesDirectory)),
          "Provided files directory [%s] incorrect!", filesDirectory);
    }
  }

  public String getFilesDirectory() {
    return filesDirectory;
  }

  public void setFilesDirectory(String filesDirectory) {
    this.filesDirectory = filesDirectory;
  }

  public String getStorageUrl() {
    return storageUrl;
  }

  public void setStorageUrl(String storageUrl) {
    this.storageUrl = storageUrl;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }
}
