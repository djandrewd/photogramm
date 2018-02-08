package ua.danit.photogramm.users.data.services;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.danit.photogramm.users.data.dao.UsersDao;
import ua.danit.photogramm.users.data.model.User;

/**
 * Data service responsible for managing user subscriptions in some defined data storage with
 * help of {@link ua.danit.photogramm.users.data.dao.UsersDao}.
 *
 * @author Andrey Minov
 */
@Service
public class SubscriptionsManagementService {
  private static final String ERROR_MSG_TEMPLATE = "'%s' must not be empty!";
  private static final String NOT_EXISTED_ERROR_MSG_TEMPLATE =
      "User '%s' is not existed in the system!";

  private final UsersDao usersDao;

  /**
   * Instantiates a new Subscriptions management service.
   *
   * @param usersDao the users data access object.
   */
  @Autowired
  public SubscriptionsManagementService(UsersDao usersDao) {
    this.usersDao = usersDao;
  }

  /**
   * Gets users subscribed on provided user.
   *
   * @param nickname nickname of the user.
   * @return the users subscribed on current user updates.
   */
  public List<User> getSubscribers(String nickname) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    return usersDao.findAllSubscribers(nickname);
  }

  /**
   * Gets all users on which provided user is subscribed.
   *
   * @param nickname nickname of the user.
   * @return the users subscribed on current user updates.
   */
  public List<User> getSubscriptions(String nickname) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    return usersDao.findAllSubscriptions(nickname);
  }


  /**
   * Subscribe user on updates from another user.
   *
   * @param subscriberNickname   the subscriber nickname owned the subscription.
   * @param subscriptionNickname the subscription user nickname for updates.
   * @throws IllegalArgumentException in case some or user is not exists.
   * @throws IllegalStateException    when user is already subscribed on defined user.
   */
  @Transactional
  public void subscribeUser(String subscriberNickname, String subscriptionNickname) {
    checkArgument(!isNullOrEmpty(subscriberNickname), ERROR_MSG_TEMPLATE, "subscriber");
    checkArgument(!isNullOrEmpty(subscriptionNickname), ERROR_MSG_TEMPLATE, "subscription");

    User subscriber = usersDao.findOne(subscriberNickname);
    checkArgument(subscriber != null, NOT_EXISTED_ERROR_MSG_TEMPLATE, subscriberNickname);

    User subscription = usersDao.findOne(subscriptionNickname);
    checkArgument(subscription != null, NOT_EXISTED_ERROR_MSG_TEMPLATE, subscriptionNickname);

    if (subscriber.getSubscriptions() == null) {
      subscriber.setSubscriptions(new HashSet<>());
    }
    if (!subscriber.getSubscriptions().add(subscription)) {
      throw new IllegalStateException(String
          .format("User '%s' has already subscribed on '%s'!", subscriberNickname,
              subscriptionNickname));
    }
    usersDao.save(subscription);
  }

  /**
   * Unsubscribe user on updates from another user.
   *
   * @param subscriberNickname   the subscriber nickname owned the subscription.
   * @param subscriptionNickname the subscription user nickname for updates.
   * @throws IllegalArgumentException in case some or user is not exists.
   * @throws IllegalStateException    when user is already unsubscribed on defined user.
   */
  @Transactional
  public void unsubscribeUser(String subscriberNickname, String subscriptionNickname) {
    checkArgument(!isNullOrEmpty(subscriberNickname), ERROR_MSG_TEMPLATE, "subscriber");
    checkArgument(!isNullOrEmpty(subscriptionNickname), ERROR_MSG_TEMPLATE, "subscription");

    User subscriber = usersDao.findOne(subscriberNickname);
    checkArgument(subscriber != null, NOT_EXISTED_ERROR_MSG_TEMPLATE, subscriberNickname);

    User subscription = usersDao.findOne(subscriptionNickname);
    checkArgument(subscription != null, NOT_EXISTED_ERROR_MSG_TEMPLATE, subscriptionNickname);

    if (!subscriber.getSubscriptions().remove(subscription)) {
      throw new IllegalStateException(String
          .format("User '%s' is not subscribed on '%s'!", subscriberNickname,
              subscriptionNickname));
    }
    usersDao.save(subscription);
  }
}
