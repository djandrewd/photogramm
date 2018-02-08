package ua.danit.photogramm.imgs.external.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnegative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.external.model.Action;
import ua.danit.photogramm.imgs.external.model.HistoryActions;
import ua.danit.photogramm.imgs.services.internal.ImagesSocialService;

/**
 * Resource for historical social activity for account subscribers.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/api/social/history")
@Validated
public class HistoryResource {
  private static final int PAGE_SIZE = 20;
  private static final int MAX_LEN = 10;

  private final ImagesSocialService service;

  /**
   * Instantiates a new History resource.
   *
   * @param service the service for account social activity.
   */
  @Autowired
  public HistoryResource(ImagesSocialService service) {
    this.service = service;
  }

  /**
   * Gets user subscribers social activity history.
   *
   * @param nickname the nickname of the user
   * @param page     the page for selection
   * @return the social activity for user subscribers for current user.
   */
  @GetMapping(value = "{nickname}", produces = MediaType.APPLICATION_JSON_VALUE)
  public HistoryActions getUserHistory(@PathVariable("nickname") String nickname,
                                       @Nonnegative @RequestParam(value = "page",
                                           defaultValue = "0") int page) {

    List<Action> actions =
        service.getLatestHistoryActions(nickname, PAGE_SIZE, page).stream().map(v -> {
          String name = v.getImage().getName().length() > MAX_LEN
              ? v.getImage().getName().substring(0, MAX_LEN) + "..." : v.getImage().getName();
          return new Action(v.getUserId(), v.getType().getAction(), name);
        }).collect(Collectors.toList());
    HistoryActions historyActions = new HistoryActions(actions);
    historyActions
        .add(linkTo(methodOn(HistoryResource.class).getUserHistory(nickname, page)).withSelfRel());
    if (page > 0) {
      historyActions.add(linkTo(methodOn(HistoryResource.class).getUserHistory(nickname, page - 1))
          .withRel(Link.REL_PREVIOUS));
    }
    historyActions.add(linkTo(methodOn(HistoryResource.class).getUserHistory(nickname, page + 1))
        .withRel(Link.REL_NEXT));
    return historyActions;
  }
}
