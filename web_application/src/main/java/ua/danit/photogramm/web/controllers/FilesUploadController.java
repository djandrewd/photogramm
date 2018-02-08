package ua.danit.photogramm.web.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.danit.photogramm.imgs.services.internal.ImagesStoreService;

/**
 * Model controller for image files upload and store.
 *
 * @author Andrey Minov
 */
@Controller
@RequestMapping("/upload")
public class FilesUploadController {

  private final ImagesStoreService imagesStoreService;

  /**
   * Instantiates a new Files upload controller.
   *
   * @param imagesStoreService the images store service
   */
  @Autowired
  public FilesUploadController(ImagesStoreService imagesStoreService) {
    this.imagesStoreService = imagesStoreService;
  }

  /**
   * Save image string.
   *
   * @param name           the name
   * @param file           the file
   * @param authentication the authentication
   * @return the string
   * @throws IOException the io exception
   */
  @Transactional()
  @PostMapping(value = "/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public String saveImage(@RequestParam("name") String name,
                          @RequestParam("file") MultipartFile file,
                          Authentication authentication) throws IOException {
    UserDetails user = (UserDetails) authentication.getPrincipal();
    imagesStoreService.storeImage(user.getUsername(), file.getInputStream(), name);
    return "redirect:/";
  }
}
