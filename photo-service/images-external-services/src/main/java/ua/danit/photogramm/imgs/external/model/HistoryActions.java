package ua.danit.photogramm.imgs.external.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class HistoryActions extends ResourceSupport {
  private List<Action> actions;

  public HistoryActions(List<Action> actions) {
    this.actions = actions;
  }

  public List<Action> getActions() {
    return actions;
  }
}
