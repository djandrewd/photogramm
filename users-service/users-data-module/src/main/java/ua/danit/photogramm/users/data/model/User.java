package ua.danit.photogramm.users.data.model;

import java.time.Instant;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Hold information about user in Photogramm application. Users are identified
 * by unique nickname and unique email. User might also have real name
 * but it is optional. Country and language are also optional but might be used for localization.
 *
 * @author Andrey Minov
 */
@Entity
@Table(name = "USERS")
public class User {
  @Id
  @Column(name = "NICKNAME")
  private String nickname;
  @Column(name = "EMAIL")
  private String email;
  @Column(name = "NAME")
  private String name;
  @Column(name = "PASSWORD")
  private String password;
  @Column(name = "LANGUAGE")
  private String language;
  @Column(name = "LAST_ACTIVITY_TIME")
  private Instant lastActivityTime;
  @Column(name = "POSTS", insertable = false)
  private Integer posts;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
  @JoinTable(name = "SUBSCRIPTIONS", joinColumns = @JoinColumn(name = "SUBSCRIBER_USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "SUBSCRIPTION_USER_ID"))
  private Set<User> subscriptions;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
  @JoinTable(name = "SUBSCRIPTIONS", joinColumns = @JoinColumn(name = "SUBSCRIPTION_USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "SUBSCRIBER_USER_ID"))
  private Set<User> subscribers;


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Set<User> getSubscriptions() {
    return subscriptions;
  }

  public void setSubscriptions(Set<User> subscriptions) {
    this.subscriptions = subscriptions;
  }

  public Set<User> getSubscribers() {
    return subscribers;
  }

  public void setSubscribers(Set<User> subscribers) {
    this.subscribers = subscribers;
  }

  public Instant getLastActivityTime() {
    return lastActivityTime;
  }

  public void setLastActivityTime(Instant lastActivityTime) {
    this.lastActivityTime = lastActivityTime;
  }

  public Integer getPosts() {
    return posts;
  }

  public void setPosts(Integer posts) {
    this.posts = posts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) {
      return false;
    }
    if (email != null ? !email.equals(user.email) : user.email != null) {
      return false;
    }
    return name != null ? name.equals(user.name) : user.name == null;
  }

  @Override
  public int hashCode() {
    int result = nickname != null ? nickname.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
