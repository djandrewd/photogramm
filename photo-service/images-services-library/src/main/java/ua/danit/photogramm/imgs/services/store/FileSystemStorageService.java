package ua.danit.photogramm.imgs.services.store;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.danit.photogramm.imgs.services.configuration.FileSystemStoreProperties;

/**
 * Storage system for storing files into local file system.
 *
 * @author Andrey Minov
 */
public class FileSystemStorageService implements ImagesStorageService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemStorageService.class);

  private FileSystemStoreProperties properties;

  /**
   * Instantiates a new File system storage service.
   *
   * @param properties the local file system properties.
   */
  public FileSystemStorageService(FileSystemStoreProperties properties) {
    this.properties = properties;
  }

  @Override
  public URL saveImage(String name, BufferedImage image) {
    try {
      LOGGER.info("Try to store image [{}] into remote AWS.", image);
      String imageName = name + "." + properties.getFormat();
      ImageIO.write(image, properties.getFormat(),
          new File(properties.getFilesDirectory() + imageName));
      URL external = new URL(properties.getStorageUrl() + imageName);
      LOGGER.info("Image stored and will be accessed via : {}", external);
      return external;
    } catch (Exception e) {
      throw new RuntimeException(String.format("Image saving error: %s", name), e);
    }
  }
}
