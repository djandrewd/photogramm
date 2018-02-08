package ua.danit.photogramm.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.users.data.model.User;
import ua.danit.photogramm.users.data.services.SubscriptionsManagementService;
import ua.danit.photogramm.web.model.InternalUser;

/**
 * Rest controller for user subscription manipulations.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("s")
public class SubscriptionsController {

  private final SubscriptionsManagementService service;

  @Autowired
  public SubscriptionsController(SubscriptionsManagementService service) {
    this.service = service;
  }

  @PostMapping(value = "/subscribe", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void subscribe(@RequestParam("nickname") String nickname, Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();
    service.subscribeUser(self.getNickname(), nickname);
  }

  @PostMapping(value = "/unsubscribe", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public void unsubscribe(@RequestParam("nickname") String nickname,
                          Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();
    service.unsubscribeUser(self.getNickname(), nickname);
  }
}
