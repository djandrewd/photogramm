package ua.danit.photogramm.users.external.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This is REST model user entity which transform from
 * {@link ua.danit.photogramm.users.data.model.User} to avoid data leak.
 *
 * @author Andrey Minov
 */
@JsonInclude(NON_NULL)
public class User {
  private String nickname;
  private String email;
  private String name;
  private String language;

  private User(String nickname, String email, String name, String language) {
    this.nickname = nickname;
    this.email = email;
    this.name = name;
    this.language = language;
  }

  /**
   * Create new external model user from database user entity.
   *
   * @param user data storage user entity, might be null.
   * @return newly created external model user entity or null of provided parameter is null.
   */
  public static User from(ua.danit.photogramm.users.data.model.User user) {
    if (user == null) {
      return null;
    }
    return new User(user.getNickname(), user.getEmail(), user.getName(), user.getLanguage());
  }

  public String getNickname() {
    return nickname;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getLanguage() {
    return language;
  }
}
