package ua.danit.photogramm.web.model;

import java.time.Instant;
import java.util.Set;

import ua.danit.photogramm.imgs.model.Comment;
import ua.danit.photogramm.imgs.model.Image;
import ua.danit.photogramm.imgs.model.LikeEntity;

/**
 * Holds information about image together with additional information.
 *
 * @author Andrey Minov
 */
public class ImageDecorator {
  private Image image;
  private boolean liked;

  public ImageDecorator(Image image, boolean liked) {
    this.image = image;
    this.liked = liked;
  }

  public long getId() {
    return image.getId();
  }

  public String getName() {
    return image.getName();
  }

  public int getLikes() {
    return image.getLikes();
  }

  public Instant getCreateTime() {
    return image.getCreateTime();
  }

  public String getNickname() {
    return image.getNickname();
  }

  public String getUrl() {
    return image.getUrl();
  }

  public Set<LikeEntity> getLikesEntities() {
    return image.getLikesEntities();
  }

  public Set<Comment> getComments() {
    return image.getComments();
  }

  public boolean isLiked() {
    return liked;
  }
}
