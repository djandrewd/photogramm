package ua.danit.photogramm.users.external.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.SubscriptionsManagementService;

/**
 * REST external services resource managing subscriptions of users in the system.
 * <p/>
 * TODO : add authorization process.
 *
 * @author Andrey Minov
 */
@RestController
@Validated
@RequestMapping("/api/subscriptions")
public class SubscriptionsResource {
  private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionsResource.class);

  private final SubscriptionsManagementService service;

  /**
   * Instantiates a new Subscriptions resource.
   *
   * @param service the service
   */
  @Autowired
  public SubscriptionsResource(SubscriptionsManagementService service) {
    this.service = service;
  }

  /**
   * Subscribe user with <code>subscriber</code> on user with <code>subscription</code> nickname.
   *
   * @param subscriberNickname   the subscriber nickname who will add entry.
   * @param subscriptionNickname the subscription nickname on which user is subscribed.
   */
  @PostMapping(value = "/subscribe", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void subscribeUser(@RequestParam("nickname") String subscriberNickname,
                            @RequestParam("subscription") String subscriptionNickname) {
    LOGGER.info("Receive subscription request from {} on {}", subscriberNickname,
        subscriptionNickname);
    service.subscribeUser(subscriberNickname, subscriptionNickname);
    LOGGER.info("Subscription successfully applied.");
  }

  /**
   * Unsubscribe user with <code>subscriber</code>
   * from user with <code>subscription</code> nickname.
   *
   * @param subscriberNickname   the subscriber nickname who will add entry.
   * @param subscriptionNickname the subscription nickname on which user is subscribed.
   */
  @PostMapping(value = "/unsubscribe", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void unsubscribeUser(@RequestParam("nickname") String subscriberNickname,
                              @RequestParam("subscription") String subscriptionNickname) {
    LOGGER.info("Receive unsubscription request from {} to {}", subscriberNickname,
        subscriptionNickname);
    service.unsubscribeUser(subscriberNickname, subscriptionNickname);
    LOGGER.info("User successfully un-subscripted from {}.", subscriptionNickname);
  }

  /**
   * Gets all user subscriptions by provided user nickname.
   *
   * @param nickname the nickname of user.
   * @return the list of nicknames on whom this user subscribed empty list if nothing found.
   */
  @GetMapping(value = "/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<String> getSubscriptions(@RequestParam("nickname") String nickname) {
    return service.getSubscriptions(nickname).stream().map(User::getNickname)
                  .collect(Collectors.toList());
  }

  /**
   * Gets all subscribers nicknames subscribed on provided user.
   *
   * @param nickname the nickname of user to check.
   * @return the subscribers nicknames of current user or empty list if nothing found.
   */
  @GetMapping(value = "/subscribers", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<String> getSubscribers(@RequestParam("nickname") String nickname) {
    return service.getSubscribers(nickname).stream().map(User::getNickname)
                  .collect(Collectors.toList());
  }
}
