package ua.danit.photogramm.imgs.model;

/**
 * Social action type of the entity.
 *
 * @author Andrey Minov
 */
public enum SocialActionType {
  LIKE(0), COMMENT(1);

  int action;

  SocialActionType(int action) {
    this.action = action;
  }

  public int getAction() {
    return action;
  }
}
