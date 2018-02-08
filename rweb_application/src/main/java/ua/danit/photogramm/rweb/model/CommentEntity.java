package ua.danit.photogramm.rweb.model;

import com.google.common.base.MoreObjects;

import org.springframework.hateoas.ResourceSupport;

/**
 * Comment entity stored in data store.
 *
 * @author Andrey Minov
 */
public class CommentEntity extends ResourceSupport {
  private long id;
  private String nickname;
  private String text;

  /**
   * Instantiates a new Comment entity.
   *
   * @param id       the identity code of the comment.
   * @param nickname the nickname of the user.
   * @param text     the text of the comment.
   */
  public CommentEntity(long id, String nickname, String text) {
    this.id = id;
    this.nickname = nickname;
    this.text = text;
  }

  public String getNickname() {
    return nickname;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("id", id).add("nickname", nickname)
                      .add("text", text).toString();
  }
}
