package ua.danit.photogramm.imgs.external.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.external.model.Image;
import ua.danit.photogramm.imgs.external.model.Images;
import ua.danit.photogramm.imgs.services.internal.ImagesDataService;

/**
 * REST resource for managing user images in the system.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/api/images")
@Validated
public class ImagesReadResource {

  private final ImagesDataService service;

  /**
   * Instantiates a new Images resource.
   *
   * @param service the service for managing images.
   */
  @Autowired
  public ImagesReadResource(ImagesDataService service) {
    this.service = service;
  }

  /**
   * Gets top N user images defined by property <code>images.items-on-page</code> on
   * requested page. Images are sorted in descending order by id.
   *
   * @param nickname the nickname of user to select images.
   * @param page     the page started from 0 to select images.
   * @return the user images selected and ordered by desc on requested page.
   */
  @GetMapping(value = "/{nickname}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Images getUserImages(@PathVariable("nickname") String nickname,
                              @PositiveOrZero @RequestParam(value = "page",
                                  defaultValue = "0") Integer page) {

    List<Image> images = service.getUserImages(nickname, page).stream().map(v -> {
      Image image = Image.create(v);
      //
      // Social services
      image.add(linkTo(methodOn(LikesResource.class).like(v.getId(), nickname)).withRel("like"));
      image.add(linkTo(methodOn(LikesResource.class).like(v.getId(), nickname)).withRel("dislike"));
      image.add(linkTo(methodOn(CommentsResource.class).addComment(v.getId(), nickname, null))
          .withRel("comment"));

      return image;
    }).collect(Collectors.toList());

    Images entity = new Images(images);
    // Self, next and prev pages.
    entity.add(
        linkTo(methodOn(ImagesReadResource.class).getUserImages(nickname, page)).withSelfRel());
    if (page > 0) {
      entity.add(linkTo(methodOn(ImagesReadResource.class).getUserImages(nickname, page - 1))
          .withRel("prev"));
    }
    entity.add(linkTo(methodOn(ImagesReadResource.class).getUserImages(nickname, page + 1))
        .withRel("next"));
    return entity;
  }
}
