package ua.danit.photogramm.rweb.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.rweb.model.ApplicationUser;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.SubscriptionsManagementService;

/**
 * Rest controller for user subscription manipulations.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/social")
public class SubscriptionsController {

  private final SubscriptionsManagementService service;

  /**
   * Instantiates a new Subscriptions controller.
   *
   * @param service the service for managing data-store subscriptions.
   */
  @Autowired
  public SubscriptionsController(SubscriptionsManagementService service) {
    this.service = service;
  }

  private static ApplicationUser convert(User user, String self, List<User> selfSubscriptions) {
    boolean subscribed = selfSubscriptions.contains(user);
    ApplicationUser entity =
        new ApplicationUser(user.getNickname(), user.getName(), user.getEmail(), user.getLanguage(),
            user.getNickname().equals(self), subscribed, -1, -1, user.getPosts());
    // Self
    entity.add(new Link(String.format("/account/%s", user.getNickname())));
    // Social
    entity.add(new Link(String.format("/social/subscribe/%s", user.getNickname()), "subscribe"));
    entity
        .add(new Link(String.format("/social/unsubscribe/%s", user.getNickname()), "ubsubscribe"));
    return entity;
  }

  /**
   * Subscribe current user on provided username.
   *
   * @param nickname       the nickname of subscription.
   * @param authentication the authentication
   */
  @PostMapping(value = "/subscribe/{nickname}")
  public void subscribe(@PathVariable("nickname") String nickname, Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();
    service.subscribeUser(self.getNickname(), nickname);
  }

  /**
   * Unsubscribe user from provided username.
   *
   * @param nickname       the nickname of subscription.
   * @param authentication the authentication
   */
  @PostMapping(value = "/unsubscribe/{nickname}")
  public void unsubscribe(@PathVariable("nickname") String nickname,
                          Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();
    service.unsubscribeUser(self.getNickname(), nickname);
  }

  /**
   * Get list of user subscribers.
   *
   * @param nickname       the nickname of the user
   * @param authentication the authentication of current user.
   * @return the list of users subscribers.
   */
  @GetMapping("/subscribers/{nickname}")
  public List<ApplicationUser> subscribers(@PathVariable("nickname") String nickname,
                                           Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();
    List<User> selfSubscriptions = service.getSubscriptions(self.getNickname());
    return service.getSubscribers(nickname).stream()
                  .filter(user -> !user.getNickname().equals(nickname))
                  .map(user -> convert(user, self.getNickname(), selfSubscriptions))
                  .collect(Collectors.toList());
  }

  /**
   * Subscriptions list of provided user.
   *
   * @param nickname       the nickname
   * @param authentication the authentication
   * @return the list of users subscriptions
   */
  @GetMapping("/subscriptions/{nickname}")
  public List<ApplicationUser> subscriptions(@PathVariable("nickname") String nickname,
                                             Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();
    List<User> selfSubscriptions = service.getSubscriptions(self.getNickname());
    return service.getSubscriptions(nickname).stream()
                  .filter(user -> !user.getNickname().equals(nickname))
                  .map(user -> convert(user, self.getNickname(), selfSubscriptions))
                  .collect(Collectors.toList());
  }
}
