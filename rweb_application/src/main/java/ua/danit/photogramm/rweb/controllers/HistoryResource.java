package ua.danit.photogramm.rweb.controllers;

import static ua.danit.photogramm.rweb.controllers.ImagesFeedController.convert;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnegative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.services.internal.ImagesSocialService;
import ua.danit.photogramm.rweb.model.HistoryActions;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.rweb.model.SocialAction;
import ua.danit.photogramm.users.data.model.User;

/**
 * History actions resource for current user.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/social/history")
@Validated
public class HistoryResource {
  private static final int PAGE_SIZE = 20;

  private final ImagesSocialService service;

  /**
   * Instantiates a new History resource.
   *
   * @param service the service for providing history events from data store.
   */
  @Autowired
  public HistoryResource(ImagesSocialService service) {
    this.service = service;
  }

  /**
   * Gets user history list of actions.
   *
   * @param page           the page of the selection
   * @param authentication the authentication of the current user
   * @return the user history with list of his subscribers actions.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public HistoryActions getUserHistory(
      @Nonnegative @RequestParam(value = "page", defaultValue = "0") int page,
      Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();
    List<SocialAction> actions =
        service.getLatestHistoryActions(self.getNickname(), PAGE_SIZE, page).stream().map(
            v -> new SocialAction(v.getUserId(), v.getType().getAction(),
                convert(v.getImage(), self.getNickname()))).collect(Collectors.toList());
    return new HistoryActions(actions);
  }
}
