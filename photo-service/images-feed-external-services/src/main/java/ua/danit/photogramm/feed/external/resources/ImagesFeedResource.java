package ua.danit.photogramm.feed.external.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.feed.external.model.Image;
import ua.danit.photogramm.imgs.services.internal.ImagesFeedService;

/**
 * External and internal services for image feed processing.
 * Service use caching of image feed per some user.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/api/feed")
@Validated
public class ImagesFeedResource {

  private final ImagesFeedService feedService;

  /**
   * Instantiates a new Images feed resource.
   *
   * @param feedService the data store feed service
   */
  @Autowired
  public ImagesFeedResource(ImagesFeedService feedService) {
    this.feedService = feedService;
  }

  /**
   * Gets actual top user images feed. Results are cached to be reused upon next selection.
   *
   * @param nickname the nickname of the user.
   * @return the list of images containing user feed and his subscriptions.
   */
  @Cacheable(cacheNames = "feed", key = "#nickname")
  @GetMapping(value = "/{nickname}/images", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Image> getUserFeed(@PathVariable("nickname") String nickname) {
    // For cache integration force to select user only first page.
    return feedService.getUserFeed(nickname, 0).stream().map(Image::create)
                      .collect(Collectors.toList());
  }

  /**
   * Update cache records by evicting records by nickname.
   *
   * @param nickname the nickname of the user.
   */
  @CacheEvict(cacheNames = "feed", key = "#nickname")
  @PostMapping(value = "/{nickname}/update")
  public void updateCache(@PathVariable("nickname") String nickname) {

  }

}
