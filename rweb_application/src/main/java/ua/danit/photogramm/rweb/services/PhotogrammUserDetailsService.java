package ua.danit.photogramm.rweb.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.UserManagementService;

/**
 * Database user details service which gets user information from database store
 * and then verify password.
 *
 * @author Andrey Minov
 */
public class PhotogrammUserDetailsService implements UserDetailsService {

  private UserManagementService service;

  public PhotogrammUserDetailsService(UserManagementService service) {
    this.service = service;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = service.findByNickname(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new InternalUser(user);
  }
}
