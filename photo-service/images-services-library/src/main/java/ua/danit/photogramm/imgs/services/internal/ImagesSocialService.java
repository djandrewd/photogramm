package ua.danit.photogramm.imgs.services.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.danit.photogramm.imgs.dao.CommentsDao;
import ua.danit.photogramm.imgs.dao.ImagesDao;
import ua.danit.photogramm.imgs.model.Comment;
import ua.danit.photogramm.imgs.model.HistoryAction;
import ua.danit.photogramm.imgs.model.Image;
import ua.danit.photogramm.imgs.model.LikeEntity;

/**
 * This service is responsible for managing image social life: likes and comments.
 *
 * @author Andrey Minov
 */
@Service
public class ImagesSocialService {
  private static final String ERROR_MSG_TEMPLATE = "'%s' must not be empty!";

  private final ImagesDao dao;
  private final CommentsDao commentsDao;

  /**
   * Instantiates a new Images social service.
   *
   * @param dao         the images data access object instance.
   * @param commentsDao the images comments data access object instance.
   */
  public ImagesSocialService(ImagesDao dao, CommentsDao commentsDao) {
    this.dao = dao;
    this.commentsDao = commentsDao;
  }

  /**
   * Called when user like image provided by image id.
   *
   * @param id       the image identity code.
   * @param username the username of the code.
   * @return the image entity state after update.
   */
  @Transactional
  public Image likeImage(long id, String username) {
    checkArgument(!isNullOrEmpty(username), ERROR_MSG_TEMPLATE, "username");
    Image image = dao.findOne(id);
    checkArgument(image != null, "Image with id %s not found!", id);

    LikeEntity entity = new LikeEntity();
    entity.setUserId(username);
    entity.setImage(image);

    if (image.getLikesEntities().add(entity)) {
      dao.incrementLikes(id);
    }
    return dao.saveAndFlush(image);
  }

  /**
   * Called when user dislike image provided by image id.
   *
   * @param id       the image identity code.
   * @param username the username of the code.
   * @return the image entity state after update.
   */
  @Transactional
  public Image dislikeImage(long id, String username) {
    checkArgument(!isNullOrEmpty(username), ERROR_MSG_TEMPLATE, "username");
    Image image = dao.findOne(id);
    checkArgument(image != null, "Image with id %d not found!", id);

    LikeEntity entity = new LikeEntity();
    entity.setImage(image);
    entity.setUserId(username);

    if (image.getLikesEntities().remove(entity)) {
      dao.decrementLikes(id);
    }
    return dao.saveAndFlush(image);
  }

  /**
   * Add comment to provided image.
   *
   * @param imageId  the identity code of the image.
   * @param username the username who add comment.
   * @param text     the text of the comment.
   * @return newly added comment to the image.
   */
  @Transactional
  public Comment addComment(long imageId, String username, String text) {
    checkArgument(!isNullOrEmpty(username), ERROR_MSG_TEMPLATE, "username");
    Image image = dao.findOne(imageId);
    checkArgument(image != null, "Image with id %d not found!", imageId);

    Comment comment = new Comment();
    comment.setImage(image);
    comment.setUserId(username);
    comment.setText(text);

    commentsDao.saveAndFlush(comment);
    return comment;
  }

  /**
   * Update comment text by provided comment id.
   *
   * @param id   the identity code of the comment.
   * @param text the text of the comment.
   * @return newly added comment to the image.
   */
  @Transactional
  public Comment editComment(long id, String text) {
    Comment comment = commentsDao.findOne(id);
    checkArgument(comment != null, "Comment with id %d not found!", id);
    comment.setText(text);
    commentsDao.saveAndFlush(comment);
    return comment;
  }

  /**
   * Get comment instance by provided id.
   *
   * @param id the identity code of the comment.
   * @return comment to the image or null if nothing was found.
   */
  @Transactional
  public Comment getComment(long id) {
    return commentsDao.findOne(id);
  }

  /**
   * List top comments from image.
   *
   * @param id   the id of the image.
   * @param size size of page.
   * @param page number of comments page.
   * @return the list of comments made by users or empty list if nothing is found.
   */
  @Transactional(readOnly = true)
  public List<Comment> listTopComments(long id, int size, int page) {
    return commentsDao.findByImageId(id, new PageRequest(page, size, new Sort(DESC, "id")));
  }

  /**
   * Remove comment from image by provided id.
   *
   * @param id the identity code of the comment.
   */
  @Transactional
  public void removeComment(long id) {
    commentsDao.delete(id);
  }

  /**
   * Gets latest history actions made by user subscribers.
   *
   * @param nickname the nickname of the user to search.
   * @param page     the page number of the action items
   * @return the latest history actions made by user subscribers.
   */
  @Transactional
  public List<HistoryAction> getLatestHistoryActions(String nickname, int size, int page) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");

    List<HistoryAction> actions = new ArrayList<>();
    actions.addAll(
        dao.selectLikes(nickname, new PageRequest(page, size, new Sort(DESC, "createTime"))));
    actions.addAll(commentsDao
        .findByUserId(nickname, new PageRequest(page, size, new Sort(DESC, "createTime"))));
    actions.sort(Comparator.comparing(HistoryAction::getCreateTime).reversed());
    return actions;
  }
}
