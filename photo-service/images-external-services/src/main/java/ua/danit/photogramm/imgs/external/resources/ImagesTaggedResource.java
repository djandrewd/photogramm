package ua.danit.photogramm.imgs.external.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.external.model.Image;
import ua.danit.photogramm.imgs.external.model.Images;
import ua.danit.photogramm.imgs.services.internal.ImagesTaggedService;

/**
 * Resource for selection of images with defined tags from data storage.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/api/images/tagged")
public class ImagesTaggedResource {

  private final ImagesTaggedService service;

  /**
   * Instantiates a new Images tagged resource.
   *
   * @param service the tagged images data service
   */
  @Autowired
  public ImagesTaggedResource(ImagesTaggedService service) {
    this.service = service;
  }

  /**
   * Gets top N tagged images defined by property <code>images.items-on-page</code> on
   * requested page. Images are sorted in descending order by id.
   *
   * @param tag  the image tag.
   * @param page the page started from 0 to select images.
   * @return the tagged images selected and ordered by desc on requested page.
   */
  @GetMapping(value = "/{tag}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Images getUserImages(@PathVariable("tag") String tag,
                              @PositiveOrZero @RequestParam(value = "page",
                                  defaultValue = "0") Integer page) {

    List<Image> images = service.getTaggedImages(tag, page).stream().map(v -> {
      Image image = Image.create(v);
      //
      // Social services
      image.add(linkTo(methodOn(LikesResource.class).like(v.getId(), null)).withRel("like"));
      image.add(linkTo(methodOn(LikesResource.class).like(v.getId(), null)).withRel("dislike"));
      image.add(linkTo(methodOn(CommentsResource.class).addComment(v.getId(), null, null))
          .withRel("comment"));
      image.add(linkTo(methodOn(CommentsResource.class).removeComment(v.getId()))
          .withRel("removeComment"));

      return image;
    }).collect(Collectors.toList());

    Images entity = new Images(images);
    // Self, next and prev pages.
    entity.add(linkTo(methodOn(ImagesReadResource.class).getUserImages(tag, page)).withSelfRel());
    if (page > 0) {
      entity.add(
          linkTo(methodOn(ImagesReadResource.class).getUserImages(tag, page - 1)).withRel("prev"));
    }
    entity.add(
        linkTo(methodOn(ImagesReadResource.class).getUserImages(tag, page + 1)).withRel("next"));
    return entity;
  }
}
