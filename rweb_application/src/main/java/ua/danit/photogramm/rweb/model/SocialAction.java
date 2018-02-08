package ua.danit.photogramm.rweb.model;

/**
 * Social action entity made by some user of image.
 *
 * @author Andrey Minov
 */
public class SocialAction {

  private String nickname;
  private int action;
  private ImageEntity image;

  /**
   * Instantiates a new Social action.
   *
   * @param nickname the nickname of user, made the action
   * @param action   the action of the image: 0 stands for like and 1 for comment.
   * @param image    the target image.
   */
  public SocialAction(String nickname, int action, ImageEntity image) {
    this.nickname = nickname;
    this.action = action;
    this.image = image;
  }

  public String getNickname() {
    return nickname;
  }

  public int getAction() {
    return action;
  }

  public ImageEntity getImage() {
    return image;
  }
}
