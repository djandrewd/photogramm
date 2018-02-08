package ua.danit.photogramm.rweb.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * Entity which holds current user information with images list.
 * Created mostly to reduce number of requests for server and
 * requested when user and images must be updated as single entity.
 *
 * @author Andrey Minov
 */
public class UserWithImagesEntity extends ResourceSupport {
  private String nickname;
  private List<ImageEntity> images;

  /**
   * Instantiates a new Feed entity.
   *
   * @param nickname the nickname of current logged in user.
   * @param images   the images of the user.
   */
  public UserWithImagesEntity(String nickname, List<ImageEntity> images) {
    this.nickname = nickname;
    this.images = images;
  }

  public String getNickname() {
    return nickname;
  }

  public List<ImageEntity> getImages() {
    return images;
  }
}
