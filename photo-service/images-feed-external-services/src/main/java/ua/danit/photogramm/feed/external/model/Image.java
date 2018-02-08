package ua.danit.photogramm.feed.external.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * External protocol image entity.
 *
 * @author Andrey Minov
 */
@JsonInclude(NON_NULL)
public class Image {
  private String name;
  private String author;
  private String url;
  private int likes;
  private ZonedDateTime createTime;

  private Image(String name, String author, String url, int likes, ZonedDateTime createTime) {
    this.name = name;
    this.author = author;
    this.url = url;
    this.likes = likes;
    this.createTime = createTime;
  }

  /**
   * Create new model image from entity stored in database.
   *
   * @param image the image
   * @return the external model image instance.
   */
  public static Image create(ua.danit.photogramm.imgs.model.Image image) {
    return new Image(image.getName(), image.getNickname(), image.getUrl(), image.getLikes(),
        image.getCreateTime().atZone(ZoneOffset.UTC));
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public int getLikes() {
    return likes;
  }

  public String getAuthor() {
    return author;
  }

  public ZonedDateTime getCreateTime() {
    return createTime;
  }
}
