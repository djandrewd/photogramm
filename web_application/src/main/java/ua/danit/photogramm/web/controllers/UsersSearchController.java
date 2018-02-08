package ua.danit.photogramm.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.UserManagementService;
import ua.danit.photogramm.web.model.ApplicationUser;

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

  @Autowired
  public UsersSearchController(UserManagementService service) {
    this.service = service;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ApplicationUser> searchUsers(@RequestParam("q") String query) {
    List<User> result = service.findByNicknameAnyMatch(query);
    return result.stream().map(ApplicationUser::from).collect(Collectors.toList());
  }
}
