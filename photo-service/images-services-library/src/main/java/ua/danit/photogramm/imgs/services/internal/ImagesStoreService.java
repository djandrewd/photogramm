package ua.danit.photogramm.imgs.services.internal;

import static org.imgscalr.Scalr.resize;

import com.google.common.io.BaseEncoding;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.danit.photogramm.imgs.model.Image;
import ua.danit.photogramm.imgs.services.configuration.ImageProperties;
import ua.danit.photogramm.imgs.services.store.ImagesStorageService;

/**
 * This is storage service used to work with external storages and notifications.
 * Used for storing images into system.
 *
 * @author Andrey Minov
 */
@Service
@Lazy
public class ImagesStoreService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImagesStoreService.class);

  private static final int FILE_NAME_LEN = 10;
  private ImagesDataService dataService;
  private ImageProperties properties;
  private ImagesStorageService imagesStorageService;

  /**
   * Instantiates a new Images store service.
   *
   * @param dataService          the data service
   * @param properties           the properties for image storage.
   * @param imagesStorageService service for storing images into file system for remove access.
   */
  @Autowired
  public ImagesStoreService(ImagesDataService dataService, ImageProperties properties,
                            ImagesStorageService imagesStorageService) {
    this.dataService = dataService;
    this.properties = properties;
    this.imagesStorageService = imagesStorageService;
  }

  /**
   * Store image file into remove files storage, insert record into data store and put
   * notification event for subscribers.
   *
   * @param nickname the nickname of the user.
   * @param name     the name of image to upload.
   * @param image    the image file entity.
   * @return the image entity stored inside data store.
   * @throws IOException in case image cannot be written or read from IO.
   */
  @Transactional
  public Image storeImage(String nickname, InputStream image, String name) throws IOException {

    BufferedImage upload = ImageIO.read(image);
    upload = resize(upload, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, properties.getWidth(),
        properties.getHeight());

    byte[] bytes = new byte[FILE_NAME_LEN];
    ThreadLocalRandom.current().nextBytes(bytes);
    String imageName = BaseEncoding.base16().encode(bytes);

    LOGGER.info("Uploading image {} with name {} from user {}", imageName, name, nickname);
    URL url = imagesStorageService.saveImage(imageName, upload);
    ua.danit.photogramm.imgs.model.Image stored =
        dataService.saveNewImage(nickname, name, url.toString());

    // TODO: notification of other subcribers.
    return stored;
  }

}
