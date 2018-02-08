package ua.danit.photogramm.users.external.resources;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.users.data.services.UserManagementService;
import ua.danit.photogramm.users.external.model.User;

/**
 * REST external services resource for create, update, remove and select operations
 * with users.
 *
 * @author Andrey Minov
 */
@RestController
@Validated
@RequestMapping("/api/users")
public class UserResource {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

  private final UserManagementService service;
  private final PasswordEncoder passwordEncoder;

  /**
   * Instantiates a new user REST resource for CRUD operations.
   *
   * @param service         the user management service connected to data storage.
   * @param passwordEncoder user passwords encoder
   */
  @Autowired
  public UserResource(UserManagementService service, PasswordEncoder passwordEncoder) {
    this.service = service;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Create new user in the system.
   * Nickname must be unique amount all of users.
   *
   * @param nickname the nickname of the user, required
   * @param email    the email of the user, required
   * @param password the password of the user, required
   * @param name     the name of the user, optional
   * @param language the language, optional
   */
  //@Secured("ROLE_ADMIN")
  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void createUser(@RequestParam("nickname") String nickname,
                         @Email @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "language", required = false) String language) {
    LOGGER.info("Receive create user request with nickname : {} with values {}, {}, {}", nickname,
        email, name, language);
    service.saveUser(nickname, email, name, password, language);
    LOGGER.info("Finished create user request with nickname : {}", nickname);
  }

  /**
   * Update selected user details in the system.
   *
   * @param nickname the nickname of the user, required
   * @param email    the email of the user, required
   * @param name     the name of the user, optional
   * @param language the language, optional
   */
  //@Secured("ROLE_ADMIN")
  @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void update(@RequestParam("nickname") String nickname,
                     @Email @RequestParam("email") String email,
                     @RequestParam(value = "name", required = false) String name,
                     @RequestParam(value = "language", required = false) String language) {
    LOGGER.info("Receive update user request with nickname : {} with values {}, {}, {}", nickname,
        email, name, language);
    service.updateUser(nickname, email, name, language);
    LOGGER.info("Finished create user request with nickname : {}", nickname);
  }


  /**
   * Search user by provided nickname by exact match.
   *
   * @param nickname the nickname of user to search
   * @return the user entity in data storage or null if nothing was found.
   */
  @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
  public User searchUser(@RequestParam("nickname") String nickname) {
    return User.from(service.findByNickname(nickname));
  }

  /**
   * Search users by provided nickname by containing match
   *
   * @param nickname the nickname of user to search
   * @return the user entities in data storage or empty if nothing was found.
   */
  @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> searchUsers(@RequestParam("nickname") String nickname) {
    return service.findByNicknameAnyMatch(nickname).stream().map(User::from)
                  .collect(Collectors.toList());
  }

  /**
   * Remove user by provided nickname from the system
   *
   * @param nickname the nickname of user to delete from system.
   */
  //@Secured("ROLE_ADMIN")
  @PostMapping(value = "/remove", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void removeUser(@RequestParam("nickname") String nickname) {
    service.removeUser(nickname);
  }
}
