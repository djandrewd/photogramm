package ua.danit.photogramm.rweb.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.rweb.model.SignupUser;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.UserManagementService;

/**
 * Page controller for user sign-up. Handle submit request, create new user and them redirect to
 * main page.
 *
 * @author Andrey Minov
 */
@Controller
@RequestMapping("/signup")
@Validated
public class UserSignupController {

  private final UserManagementService userService;
  private final PasswordEncoder passwordEncoder;

  /**
   * Instantiates a new User signup controller.
   *
   * @param userService     the user service
   * @param passwordEncoder the password encoder
   */
  @Autowired
  public UserSignupController(UserManagementService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Handle create new user request.
   *
   * @param user    the user to be saved.
   * @param request the original HTTP response.
   * @return redirect view for main page.
   */
  @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ModelAndView handleCreateUser(@Valid SignupUser user, HttpServletRequest request) {
    User createdUser = userService.saveUser(user.getNickname(), user.getEmail(), user.getName(),
        passwordEncoder.encode(user.getPassword()), null);

    authenticateUser(createdUser, request);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("redirect:/");
    return modelAndView;
  }

  private void authenticateUser(User user, HttpServletRequest request) {
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(new InternalUser(user), user.getPassword());
    // generate session if one doesn't exist
    request.getSession();
    token.setDetails(new WebAuthenticationDetails(request));
    SecurityContextHolder.getContext().setAuthentication(token);
  }
}
