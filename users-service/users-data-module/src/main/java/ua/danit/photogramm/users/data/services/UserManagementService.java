package ua.danit.photogramm.users.data.services;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.danit.photogramm.users.data.dao.UsersDao;
import ua.danit.photogramm.users.data.model.User;

/**
 * Data service responsible for managing user objects in some defined data storage with
 * help of {@link ua.danit.photogramm.users.data.dao.UsersDao}.
 *
 * @author Andrey Minov
 */
@Service
public class UserManagementService {
  private static final String ERROR_MSG_TEMPLATE = "'%s' must not be empty!";

  private final UsersDao usersDao;

  /**
   * Instantiates a new User management service.
   *
   * @param usersDao the users dao
   */
  @Autowired
  public UserManagementService(UsersDao usersDao) {
    this.usersDao = usersDao;
  }

  /**
   * Find and return all users that are existed in the photogramm system.
   *
   * @return the users existed in the system.
   */
  @Nonnull
  public List<User> getUsers() {
    return usersDao.findAll();
  }

  /**
   * Find user by his nickname value.
   *
   * @param nickname the nickname of the user.
   * @return the user found or null if user not existed.
   * @throws IllegalArgumentException in case nickname is empty.
   */
  @Nullable
  public User findByNickname(String nickname) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    return usersDao.findOne(nickname);
  }

  /**
   * Find all users with nickname matches provided nickname by contains (like '%nickname%')
   * constraint.
   *
   * @param nickname part of nickname used for searching.
   * @return list of users matched needed part of nickname or empty if nothing found.
   */
  @Nonnull
  public List<User> findByNicknameAnyMatch(String nickname) {
    User user = new User();
    user.setNickname(nickname);
    Example<User> search = Example.of(user,
        ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    return usersDao.findAll(search);
  }

  /**
   * Saves new user into persistent content or update existed one with new values
   * and return result object created.
   *
   * @param nickname the nickname of the user, required to be set
   * @param email    the email of the user, required to be set
   * @param name     the name of user, can be empty
   * @param password the password of the user, required to be set.
   * @param language the language of the user.
   * @return the user entity as result of execution.
   * @throws IllegalArgumentException in case nickname, email or password is empty.
   * @throws IllegalStateException    when user with this nickname is existed already!
   */
  @Transactional
  @Nonnull
  public User saveUser(String nickname, String email, String name, String password,
                       String language) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    checkArgument(!isNullOrEmpty(email), ERROR_MSG_TEMPLATE, "email");
    checkArgument(!isNullOrEmpty(password), ERROR_MSG_TEMPLATE, "password");

    if (findByNickname(nickname) != null) {
      throw new IllegalStateException("User with nickname '" + nickname + "' is already existed!");
    }

    User user = new User();
    user.setNickname(nickname);
    user.setEmail(email);
    user.setName(name);
    user.setPassword(password);
    user.setLanguage(language);
    user.setSubscriptions(Collections.singleton(user));

    return usersDao.saveAndFlush(user);
  }

  /**
   * Update existed user with new details
   * and return result object created.
   *
   * @param nickname the nickname of the user, required to be set
   * @param email    the email of the user, required to be set
   * @param name     the name of user, can be empty
   * @param language the language of the user.
   * @return the user entity as result of execution.
   * @throws IllegalArgumentException in case nickname, email or password is empty.
   * @throws IllegalStateException    when user with this nickname is not existed.
   */
  @Transactional
  @Nonnull
  public User updateUser(String nickname, String email, String name, String language) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    checkArgument(!isNullOrEmpty(email), ERROR_MSG_TEMPLATE, "email");

    User user = findByNickname(nickname);
    checkState(user != null, "User with nickname '%s' is not existed!", nickname);

    user.setNickname(nickname);
    user.setEmail(email);
    user.setName(name);
    user.setLanguage(language);
    return usersDao.saveAndFlush(user);
  }

  /**
   * Saves new password for user and return result object.
   *
   * @param nickname the nickname of the user, required to be set
   * @param password the password of the user, required to be set.
   * @return the user entity as result of execution or null if user is not existed
   * @throws IllegalArgumentException in case nickname or password is null
   * @throws IllegalStateException    when user with this nickname is not existed.
   */
  @Transactional
  @Nonnull
  public User updatePassword(String nickname, String password) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    checkArgument(!isNullOrEmpty(password), ERROR_MSG_TEMPLATE, "password");

    User user = findByNickname(nickname);
    checkState(user != null, "User with nickname '%s' is not existed!", nickname);
    user.setPassword(password);
    return usersDao.saveAndFlush(user);
  }

  /**
   * Remove user from system by his nickname.
   *
   * @param nickname the nickname of the user, required not to be empty.
   * @throws IllegalArgumentException in case nickname is empty.
   * @throws IllegalStateException    when user with this nickname is not existed.
   */
  @Transactional
  public void removeUser(String nickname) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    checkState(findByNickname(nickname) != null, "User with nickname '%s' is not existed!",
        nickname);
    usersDao.delete(nickname);
  }

  /**
   * Update user last activity time made.
   *
   * @param nickname user nickname.
   * @throws IllegalArgumentException in case nickname is empty.
   * @throws IllegalStateException    when user with this nickname is not existed.
   */
  @Transactional
  public void updateActivityTime(String nickname) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    User user = findByNickname(nickname);
    checkState(user != null, "User with nickname '%s' is not existed!", nickname);
    user.setLastActivityTime(Instant.now(Clock.systemUTC()));
    usersDao.saveAndFlush(user);
  }

}
