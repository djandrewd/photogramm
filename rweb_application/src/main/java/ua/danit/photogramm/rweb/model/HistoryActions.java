package ua.danit.photogramm.rweb.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * History social actions made by requested user subscribers.
 *
 * @author Andrey Minov
 */
public class HistoryActions extends ResourceSupport {
  private List<SocialAction> actions;

  /**
   * Instantiates a new History actions.
   *
   * @param actions the actions made by users.
   */
  public HistoryActions(List<SocialAction> actions) {
    this.actions = actions;
  }

  public List<SocialAction> getActions() {
    return actions;
  }
}
