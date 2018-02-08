package ua.danit.photogramm.imgs.external.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.services.internal.ImagesSocialService;

/**
 * This is REST resource for likes management from user.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/api/social")
@Validated
public class LikesResource {

  private final ImagesSocialService service;

  /**
   * Instantiates a new Likes resource.
   *
   * @param service the service for managing player social actions.
   */
  @Autowired
  public LikesResource(ImagesSocialService service) {
    this.service = service;
  }

  /**
   * Resource for creation of 'like' entity action on image for user.
   *
   * @param imageId  the image identity code.
   * @param username the username of user.
   */
  @PostMapping(value = "/like/{image}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Void> like(@PathVariable("image") long imageId,
                                   @RequestParam("username") String username) {
    service.likeImage(imageId, username);
    return ResponseEntity.ok().build();
  }

  /**
   * Resource for creation of 'dislike' entity action on image for user.
   *
   * @param imageId  the image identity code.
   * @param username the username of user.
   */
  @PostMapping(value = "/dislike/{image}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Void> dislike(@PathVariable("image") long imageId,
                                      @RequestParam("username") String username) {
    service.dislikeImage(imageId, username);
    return ResponseEntity.ok().build();
  }
}
