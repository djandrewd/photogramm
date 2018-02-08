package ua.danit.photogramm.imgs.external.resources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.danit.photogramm.imgs.external.model.Image;
import ua.danit.photogramm.imgs.services.internal.ImagesStoreService;

/**
 * Internal REST resource used for storing files for concrete user.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/api/images")
@Validated
public class ImagesStoreResource {

  private ImagesStoreService storeService;

  /**
   * Instantiates a new Images store resource.
   *
   * @param storeService the data service used for image processing.
   */
  @Autowired
  public ImagesStoreResource(ImagesStoreService storeService) {
    this.storeService = storeService;
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
  @PostMapping(value = "/store", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Image storeImage(@RequestParam("nickname") String nickname,
                          @RequestParam("name") String name,
                          @RequestParam("image") MultipartFile image) throws IOException {
    ua.danit.photogramm.imgs.model.Image storeImage =
        storeService.storeImage(nickname, image.getInputStream(), name);
    return Image.create(storeImage);
  }
}
