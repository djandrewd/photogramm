package ua.danit.photogramm.rweb.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.services.internal.ImagesSocialService;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.users.data.model.User;

/**
 * Controller for like social services.
 * Resources are called when user like or dislike some image.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/social")
public class LikesController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LikesController.class);

  private final ImagesSocialService service;

  /**
   * Instantiates a new Likes controller.
   *
   * @param service the image service responsible for social activity.
   */
  @Autowired
  public LikesController(ImagesSocialService service) {
    this.service = service;
  }

  /**
   * Called when user like the image.
   *
   * @param id the identity code of the image.
   */
  @PostMapping("/like/{image-id}")
  public void like(@PathVariable("image-id") long id, Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User user = internal.getEntity();

    LOGGER.debug("Like received on picture {} from user", id, user.getNickname());
    service.likeImage(id, user.getNickname());
  }

  /**
   * Called when user dislike the image.
   *
   * @param id the identity code of the image.
   */
  @PostMapping("/dislike/{image-id}")
  public void dislike(@PathVariable("image-id") long id, Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User user = internal.getEntity();

    LOGGER.debug("Dislike received on picture {} from user", id, user.getNickname());
    service.dislikeImage(id, user.getNickname());
  }
}
