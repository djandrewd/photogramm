package ua.danit.photogramm.rweb.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.rweb.model.ApplicationUser;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.UserManagementService;

/**
 * This is not controller for web view, but return list
 * of responses on search of users.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("search")
public class UsersSearchController {
  private final UserManagementService service;

  /**
   * Instantiates a new Users search controller.
   *
   * @param service the service
   */
  @Autowired
  public UsersSearchController(UserManagementService service) {
    this.service = service;
  }

  /**
   * Search users by nicknames.
   *
   * @param query the query to search.
   * @return the list of found nickname.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationUser> searchUsers(@RequestParam("q") String query) {
    List<User> result = service.findByNicknameAnyMatch(query);
    return result.stream().map(user -> {
      ApplicationUser entity =
          new ApplicationUser(user.getNickname(), user.getName(), user.getEmail(),
              user.getLanguage(), false, false, 0, 0, user.getPosts());
      // Self
      entity.add(new Link(String.format("/account/%s", user.getNickname())));
      return entity;
    }).collect(Collectors.toList());
  }

  /**
   * Search user by nickname and return true is user exists in the system.
   *
   * @param nickname of the user to search.
   * @return true is user exists in the system
   */
  @GetMapping(value = "/exists/{nickname}", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean searchUser(@PathVariable("nickname") String nickname) {
    return service.findByNickname(nickname) != null;
  }
}
