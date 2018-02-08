package ua.danit.photogramm.rweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Account view controller for processing direct user page requests.
 *
 * @author Andrey Minov
 */
@Controller
@RequestMapping("/{username}")
public class AccountViewController {

  /**
   * Gets link to single page index view.
   *
   * @param username the username
   * @return the user page view.
   */
  @GetMapping
  public String getUserPage(@PathVariable("username") String username) {
    return "index";
  }
}
