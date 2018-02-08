package ua.danit.photogramm.web.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import ua.danit.photogramm.users.data.model.User;

/**
 * This is user interface for internal usage only,
 * implemented {@link org.springframework.security.core.userdetails.UserDetails}
 * and delegate all functionality to {@link ua.danit.photogramm.users.data.model.User}
 *
 * @author Andrey Minov
 */
public class InternalUser implements org.springframework.security.core.userdetails.UserDetails {
  private User entity;

  /**
   * Instantiates a new Internal user.
   *
   * @param entity the entity from data storage.
   */
  public InternalUser(User entity) {
    this.entity = entity;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return entity.getPassword();
  }

  @Override
  public String getUsername() {
    return entity.getNickname();
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public User getEntity() {
    return entity;
  }
}
