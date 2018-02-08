package ua.danit.photogramm.web.controllers;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.UserManagementService;
import ua.danit.photogramm.web.model.InternalUser;
import ua.danit.photogramm.web.model.SignupUser;

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

  @Autowired
  public UserSignupController(UserManagementService userService) {
    this.userService = userService;
  }

  @PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ModelAndView handleSubmit(@Valid SignupUser user, BindingResult result,
                                   HttpServletRequest request) {
    // TODO: check stuff with an errors!
    if (result.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("error", Optional.ofNullable(result.getFieldError().toString()));
      modelAndView.setViewName("redirect:/signup?error");
      return modelAndView;
    }

    User createdUser = userService
        .saveUser(user.getNickname(), user.getEmail(), user.getName(), user.getPassword(), null);

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
