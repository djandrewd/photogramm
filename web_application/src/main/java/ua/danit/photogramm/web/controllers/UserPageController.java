package ua.danit.photogramm.web.controllers;

import static java.lang.Math.max;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.danit.photogramm.imgs.model.Image;
import ua.danit.photogramm.imgs.services.internal.ImagesDataService;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.SubscriptionsManagementService;
import ua.danit.photogramm.users.data.services.UserManagementService;
import ua.danit.photogramm.web.model.InternalUser;

/**
 * Controller for page information about user and his images.
 *
 * @author Andrey Minov
 */
@Controller
@RequestMapping("u")
public class UserPageController {

  private final ImagesDataService imagesDataService;
  private final SubscriptionsManagementService subscriptionsManagementService;
  private final UserManagementService userManagementService;

  @Autowired
  public UserPageController(ImagesDataService imagesDataService,
                            SubscriptionsManagementService subscriptionsManagementService,
                            UserManagementService userManagementService) {
    this.imagesDataService = imagesDataService;
    this.subscriptionsManagementService = subscriptionsManagementService;
    this.userManagementService = userManagementService;
  }

  @GetMapping(value = "{nickname}")
  public ModelAndView drawUserPage(@PathVariable("nickname") String nickname,
                                   Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();

    List<Image> images = imagesDataService.getUserImages(nickname, 0);
    User user = userManagementService.findByNickname(nickname);
    if (user == null) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("redirect:/");
      return modelAndView;
    }

    List<User> subscribers = subscriptionsManagementService.getSubscribers(nickname);
    List<User> subscriptions = subscriptionsManagementService.getSubscriptions(nickname);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("user-page");
    modelAndView.addObject("nickname", user.getNickname());
    modelAndView.addObject("self", nickname.equals(self.getNickname()));
    modelAndView.addObject("subscribed", subscribers.contains(self));
    modelAndView.addObject("images", images);
    modelAndView.addObject("name", user.getName());
    modelAndView.addObject("subscribers", max(0, subscribers.size() - 1));
    modelAndView.addObject("subscriptions", max(0, subscriptions.size() - 1));
    return modelAndView;
  }
}
