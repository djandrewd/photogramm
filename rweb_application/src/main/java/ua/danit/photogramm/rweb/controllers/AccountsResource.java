package ua.danit.photogramm.rweb.controllers;

import static java.lang.Math.max;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.rweb.model.ApplicationUser;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.SubscriptionsManagementService;
import ua.danit.photogramm.users.data.services.UserManagementService;

/**
 * Controller for page information about user and his images.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/account/")
public class AccountsResource {

  private final SubscriptionsManagementService subscriptionsManagementService;
  private final UserManagementService userManagementService;

  /**
   * Instantiates a new Accounts resource.
   *
   * @param subscriptionsManagementService the subscriptions management service
   * @param userManagementService          the user management service
   */
  @Autowired
  public AccountsResource(SubscriptionsManagementService subscriptionsManagementService,
                          UserManagementService userManagementService) {
    this.subscriptionsManagementService = subscriptionsManagementService;
    this.userManagementService = userManagementService;
  }

  /**
   * Gets user information by nickname.
   *
   * @param nickname       the nickname
   * @param authentication the authentication
   * @return the user instance in data store or null if nothing was found.
   */
  @GetMapping(value = "/u/{nickname}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationUser> getUser(@PathVariable("nickname") String nickname,
                                       Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User user = userManagementService.findByNickname(nickname);
    if (user == null) {
      return null;
    }

    User self = internal.getEntity();
    List<User> subscribers = subscriptionsManagementService.getSubscribers(nickname);
    List<User> subscriptions = subscriptionsManagementService.getSubscriptions(nickname);
    boolean currentUser = user.getNickname().equals(self.getNickname());
    boolean subscribed = false;
    if (!currentUser) {
      subscribed = subscribers.contains(self);
    }

    ApplicationUser entity =
        new ApplicationUser(user.getNickname(), user.getName(), user.getEmail(), user.getLanguage(),
            currentUser, subscribed, max(0, subscribers.size() - 1),
            max(0, subscriptions.size() - 1), user.getPosts());
    // Self
    entity.add(new Link(String.format("/account/%s", nickname)));
    // Social
    entity.add(new Link(String.format("/social/subscribe/%s", nickname), "subscribe"));
    entity.add(new Link(String.format("/social/unsubscribe/%s", nickname), "ubsubscribe"));
    return Collections.singletonList(entity);
  }

  /**
   * Gets general information about currently logged in user.
   *
   * @param authentication the authentication of the current user.
   * @return the general information about currently logged in user.
   */
  @GetMapping(value = "self", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApplicationUser getSelf(Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User user = internal.getEntity();

    return new ApplicationUser(user.getNickname(), user.getName(), user.getEmail(),
        user.getLanguage(), true, false, 0, 0, user.getPosts());
  }

}
