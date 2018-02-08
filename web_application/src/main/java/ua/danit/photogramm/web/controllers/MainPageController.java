package ua.danit.photogramm.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.danit.photogramm.imgs.model.LikeEntity;
import ua.danit.photogramm.imgs.services.internal.ImagesFeedService;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.web.model.ImageDecorator;
import ua.danit.photogramm.web.model.InternalUser;

/**
 * MVC controller for main page in Photogramm application.
 *
 * @author Andrey Minov
 */
@Controller
@RequestMapping("/")
public class MainPageController {

  private final ImagesFeedService feedService;

  /**
   * Instantiates a new Main page controller.
   *
   * @param feedService the feed service
   */
  @Autowired
  public MainPageController(ImagesFeedService feedService) {
    this.feedService = feedService;
  }

  /**
   * Draw main feed model and view.
   *
   * @param authentication the authentication
   * @return the model and view
   */
  @GetMapping
  public ModelAndView drawMainFeed(Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User user = internal.getEntity();

    List<ImageDecorator> images =
        feedService.getUserFeed(user.getNickname(), 0).stream().map(i -> {
          LikeEntity entity = new LikeEntity();
          entity.setImage(i);
          entity.setUserId(user.getNickname());
          return new ImageDecorator(i, i.getLikesEntities().contains(entity));
        }).collect(Collectors.toList());

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("main-feed-page");
    modelAndView.addObject("nickname", user.getNickname());
    modelAndView.addObject("name", user.getName());
    modelAndView.addObject("images", images);
    return modelAndView;
  }
}
