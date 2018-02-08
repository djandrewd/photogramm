package ua.danit.photogramm.imgs.model;

import com.google.common.base.MoreObjects;

import java.time.Instant;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Defines stored image with all information related to it.
 *
 * @author Andrey Minov
 */
@Entity
@Table(name = "IMAGES")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  @Column(name = "LIKES", insertable = false)
  private int likes;
  private String url;
  @Column(name = "USER_ID")
  private String nickname;
  @CreationTimestamp
  @Column(name = "CREATE_TIME", insertable = false)
  private Instant createTime;

  // Social lazy loaded information.
  @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL,
      mappedBy = "image")
  private Set<LikeEntity> likesEntities;

  @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL,
      mappedBy = "image")
  private Set<Comment> comments;

  @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL,
      mappedBy = "image")
  private Set<HashTag> tags;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public Instant getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Instant createTime) {
    this.createTime = createTime;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Set<LikeEntity> getLikesEntities() {
    return likesEntities;
  }

  public void setLikesEntities(Set<LikeEntity> likesEntities) {
    this.likesEntities = likesEntities;
  }

  public Set<Comment> getComments() {
    return comments;
  }

  public void setComments(Set<Comment> comments) {
    this.comments = comments;
  }

  public Set<HashTag> getTags() {
    return tags;
  }

  public void setTags(Set<HashTag> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("likes", likes)
                      .add("url", url).add("nickname", nickname).add("createTime", createTime)
                      .toString();
  }
}
