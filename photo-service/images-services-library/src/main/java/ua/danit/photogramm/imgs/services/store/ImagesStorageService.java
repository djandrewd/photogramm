package ua.danit.photogramm.imgs.services.store;

import java.awt.image.BufferedImage;

import java.net.URL;

/**
 * Service for storing images into remove or file system.
 *
 * @author Andrey Minov
 */
public interface ImagesStorageService {
  /**
   * Save image and return URL for accessing image.
   *
   * @param name  the name of the image.
   * @param image the image content to be saved.
   * @return the url for external access for this image.
   */
  URL saveImage(String name, BufferedImage image);
}
