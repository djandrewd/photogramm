package ua.danit.photogramm.imgs.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

/**
 * This is entity holds single comment on some defined image.
 *
 * @author Andrey Minov
 */
@Entity
@Table(name = "COMMENTS")
public class Comment implements HistoryAction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "USER_ID")
  private String userId;

  @ManyToOne
  @JoinColumn(name = "IMAGE_ID")
  private Image image;

  @Column(name = "TEXT")
  private String text;

  @CreationTimestamp
  @Column(name = "CREATE_TIME", insertable = false)
  private Instant createTime;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

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

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
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
    return SocialActionType.COMMENT;
  }
}
