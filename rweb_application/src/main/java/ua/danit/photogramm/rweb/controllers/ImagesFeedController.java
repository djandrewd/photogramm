package ua.danit.photogramm.rweb.controllers;

import static ua.danit.photogramm.rweb.util.HashTags.parseIntoParts;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.model.Image;
import ua.danit.photogramm.imgs.model.LikeEntity;
import ua.danit.photogramm.imgs.services.internal.ImagesDataService;
import ua.danit.photogramm.imgs.services.internal.ImagesFeedService;
import ua.danit.photogramm.rweb.model.ImageEntity;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.rweb.model.UserWithImagesEntity;
import ua.danit.photogramm.users.data.model.User;

/**
 * Controller for user images feed.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/feed")
public class ImagesFeedController {

  private final ImagesFeedService feedService;
  private final ImagesDataService imagesDataService;

  /**
   * Instantiates a new FeedImages feed controller.
   *
   * @param feedService       the feed service
   * @param imagesDataService data service for accessing images data store.
   */
  @Autowired
  public ImagesFeedController(ImagesFeedService feedService, ImagesDataService imagesDataService) {
    this.feedService = feedService;
    this.imagesDataService = imagesDataService;
  }

  /**
   * Convert image entity from data presentation to entity with HATEOAS linkes.
   *
   * @param i        the image from data store.
   * @param nickname the nickname of the user.
   * @return the image entity to convert.
   */
  public static ImageEntity convert(Image i, String nickname) {
    LikeEntity entity = new LikeEntity();
    entity.setImage(i);
    entity.setUserId(nickname);

    boolean liked = i.getLikesEntities().contains(entity);
    ImageEntity imageEntity =
        new ImageEntity(parseIntoParts(i.getName()), i.getUrl(), i.getLikes(), i.getNickname(),
            liked);
    imageEntity.add(new Link(String.format("/feed/image/%d", i.getId()), "self"));
    // Social actions.
    imageEntity.add(new Link(String.format("/social/like/%d", i.getId()), "like"));
    imageEntity.add(new Link(String.format("/social/dislike/%d", i.getId()), "dislike"));
    // Comments
    imageEntity.add(new Link(String.format("/social/comment/add/%d", i.getId()), "comment"));
    imageEntity.add(new Link(String.format("/social/comment/list/%d", i.getId()), "listComments"));
    return imageEntity;
  }

  /**
   * Gets feed of images.
   *
   * @param authentication the authentication of current user.
   * @return the feed of the images sorted by id.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserWithImagesEntity> getFeed(Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User user = internal.getEntity();

    return Collections.singletonList(new UserWithImagesEntity(user.getNickname(),
        feedService.getUserFeed(user.getNickname(), 0).stream()
                   .map(i -> convert(i, user.getNickname())).collect(Collectors.toList())));
  }

  /**
   * Gets image information.
   *
   * @param id             the identity code if the image
   * @param authentication the authentication of current image.
   * @return the image entity with current data.
   */
  @GetMapping(value = "/image/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ImageEntity getImage(@PathVariable("id") long id, Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User user = internal.getEntity();

    return convert(imagesDataService.getImage(id), user.getNickname());
  }
}
