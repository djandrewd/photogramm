package ua.danit.photogramm.imgs.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

/**
 * This is database record for user image like.
 *
 * @author Andrey Minov.
 */
@Entity
@Table(name = "LIKES")
public class LikeEntity implements Serializable, HistoryAction {

  @Id
  @Column(name = "USER_ID")
  private String userId;

  @Id
  @ManyToOne
  @JoinColumn(name = "IMAGE_ID")
  private Image image;

  @CreationTimestamp
  @Column(name = "CREATE_TIME", insertable = false)
  private Instant createTime;

  @Override
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Instant createTime) {
    this.createTime = createTime;
  }

  @Override
  public SocialActionType getType() {
    return SocialActionType.LIKE;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LikeEntity that = (LikeEntity) o;

    if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
      return false;
    }
    return image != null ? image.equals(that.image) : that.image == null;
  }

  @Override
  public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (image != null ? image.hashCode() : 0);
    return result;
  }
}
