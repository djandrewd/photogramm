package ua.danit.photogramm.rweb.controllers;

import static ua.danit.photogramm.rweb.controllers.ImagesFeedController.convert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnegative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.services.internal.ImagesDataService;
import ua.danit.photogramm.rweb.model.ImageEntity;
import ua.danit.photogramm.rweb.model.UserImages;

/**
 * Resource used to get user uploaded image list.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/images")
@Validated
public class ImagesResource {
  private final ImagesDataService imagesDataService;

  /**
   * Instantiates a new FeedImages resource.
   *
   * @param imagesDataService the images data service
   */
  @Autowired
  public ImagesResource(ImagesDataService imagesDataService) {
    this.imagesDataService = imagesDataService;
  }


  /**
   * Gets user images on defined user page.
   *
   * @param nickname the nickname
   * @param page     the page
   * @return the images
   */
  @GetMapping(value = "{nickname}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserImages> getImages(@PathVariable("nickname") String nickname,
                                    @Nonnegative @RequestParam(value = "page",
                                        defaultValue = "0") int page) {
    List<ImageEntity> entities =
        imagesDataService.getUserImages(nickname, page).stream().map(v -> convert(v, nickname))
                         .collect(Collectors.toList());
    UserImages userImages = new UserImages(entities);
    // Navigation
    userImages.add(new Link(String.format("/images/%s?page=%d", nickname, page)));
    userImages.add(new Link(String.format("/images/%s/?page=0", nickname), Link.REL_FIRST));
    if (page > 0) {
      userImages.add(
          new Link(String.format("/images/%s/?page=%d", nickname, page - 1), Link.REL_PREVIOUS));
    }
    userImages
        .add(new Link(String.format("/images/%s/?page=%d", nickname, page + 1), Link.REL_NEXT));
    return Collections.singletonList(userImages);
  }
}
