package ua.danit.photogramm.imgs.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HASH_TAG")
public class HashTag implements Serializable {

  @Id
  @Column(name = "TAG")
  private String tag;

  @Id
  @ManyToOne
  @JoinColumn(name = "IMAGE_ID")
  private Image image;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    HashTag hashTag = (HashTag) o;

    if (tag != null ? !tag.equals(hashTag.tag) : hashTag.tag != null) {
      return false;
    }
    return image != null ? image.equals(hashTag.image) : hashTag.image == null;
  }

  @Override
  public int hashCode() {
    int result = tag != null ? tag.hashCode() : 0;
    result = 31 * result + (image != null ? image.hashCode() : 0);
    return result;
  }
}
