package ua.danit.photogramm.imgs.external.model;

/**
 * Social historical action item.
 *
 * @author Andrey Minov
 */
public class Action {
  private String nickname;
  private int action;
  private String imageName;

  /**
   * Instantiates a new Action.
   *
   * @param nickname  the nickname of the user.
   * @param action    the action that was name.
   * @param imageName the image name.
   */
  public Action(String nickname, int action, String imageName) {
    this.nickname = nickname;
    this.action = action;
    this.imageName = imageName;
  }

  public String getNickname() {
    return nickname;
  }

  public int getAction() {
    return action;
  }

  public String getImageName() {
    return imageName;
  }
}
