package ua.danit.photogramm.rweb.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * Image entity presented single image in image feed.
 *
 * @author Andrey Minov
 */
public class ImageEntity extends ResourceSupport {
  private List<TextPart> name;
  private String url;
  private Integer likes;
  private String nickname;
  private boolean liked;

  /**
   * Instantiates a new Image entity.
   *
   * @param name     the name
   * @param url      the url
   * @param likes    the likes
   * @param nickname the nickname
   * @param liked    the liked
   */
  public ImageEntity(List<TextPart> name, String url, Integer likes, String nickname,
                     boolean liked) {
    this.name = name;
    this.url = url;
    this.likes = likes;
    this.nickname = nickname;
    this.liked = liked;
  }

  public List<TextPart> getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public Integer getLikes() {
    return likes;
  }

  public String getNickname() {
    return nickname;
  }

  public boolean isLiked() {
    return liked;
  }
}
