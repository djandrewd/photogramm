package ua.danit.photogramm.rweb.model;

import org.springframework.hateoas.ResourceSupport;

/**
 * This is REST model user entity which transform from
 * {@link ua.danit.photogramm.users.data.model.User} to avoid data leak.
 *
 * @author Andrey Minov
 */
public class ApplicationUser extends ResourceSupport {
  private String nickname;
  private String username;
  private String email;
  private String language;
  private boolean self;
  private boolean subscribed;
  private int subscribers;
  private int subscriptions;
  private int posts;


  /**
   * Instantiates a new Application user.
   *
   * @param nickname      the nickname
   * @param username      the username
   * @param email         the email
   * @param language      the language
   * @param self          the self
   * @param subscribed    the subscribed
   * @param subscribers   the subscribers
   * @param subscriptions the subscriptions
   * @param posts         number of posted images.
   */
  public ApplicationUser(String nickname, String username, String email, String language,
                         boolean self, boolean subscribed, int subscribers, int subscriptions,
                         int posts) {
    this.nickname = nickname;
    this.username = username;
    this.email = email;
    this.language = language;
    this.self = self;
    this.subscribed = subscribed;
    this.subscribers = subscribers;
    this.subscriptions = subscriptions;
    this.posts = posts;
  }

  public String getNickname() {
    return nickname;
  }

  public String getEmail() {
    return email;
  }

  public String getLanguage() {
    return language;
  }

  public String getUsername() {
    return username;
  }

  public boolean isSelf() {
    return self;
  }

  public boolean isSubscribed() {
    return subscribed;
  }

  public int getSubscribers() {
    return subscribers;
  }

  public int getSubscriptions() {
    return subscriptions;
  }

  public int getPosts() {
    return posts;
  }
}
