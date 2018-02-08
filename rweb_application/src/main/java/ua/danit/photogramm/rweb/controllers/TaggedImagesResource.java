package ua.danit.photogramm.rweb.controllers;

import static ua.danit.photogramm.rweb.controllers.ImagesFeedController.convert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnegative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.services.internal.ImagesTaggedService;
import ua.danit.photogramm.rweb.model.ImageEntity;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.rweb.model.UserWithImagesEntity;
import ua.danit.photogramm.users.data.model.User;

/**
 * Resource for selection of tagged images.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/tagged")
@Validated
public class TaggedImagesResource {

  private final ImagesTaggedService service;

  /**
   * Instantiates a new Tagged images resource.
   *
   * @param service the service for selection of tagged images from data store.
   */
  @Autowired
  public TaggedImagesResource(ImagesTaggedService service) {
    this.service = service;
  }

  /**
   * Gets tagged images by provided tag on selected page.
   *
   * @param tag            the tag to be selected.
   * @param page           the page of the selection
   * @param authentication the authentication of current user.
   * @return the tagged images selected from data storage.
   */
  @GetMapping(value = "{tag}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserWithImagesEntity> getTaggedImages(@PathVariable("tag") String tag,
                                                    @RequestParam(name = "page",
                                                        defaultValue = "0") @Nonnegative int page,
                                                    Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();

    List<ImageEntity> entities =
        service.getTaggedImages(tag, page).stream().map(v -> convert(v, self.getNickname()))
               .collect(Collectors.toList());
    UserWithImagesEntity entity = new UserWithImagesEntity(self.getNickname(), entities);
    // Navigation
    entity.add(new Link(String.format("/images/tagged/%s?page=%d", tag, page)));
    entity.add(new Link(String.format("/images/tagged/%s/?page=0", tag), Link.REL_FIRST));
    if (page > 0) {
      entity.add(
          new Link(String.format("/images/tagged/%s/?page=%d", tag, page - 1), Link.REL_PREVIOUS));
    }
    entity.add(new Link(String.format("/images/tagged/%s?page=%d", tag, page + 1), Link.REL_NEXT));
    return Collections.singletonList(entity);

  }
}
