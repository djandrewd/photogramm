package ua.danit.photogramm.imgs.model;

import java.time.Instant;

/**
 * History social action made by user: like, comment, share etc.
 *
 * @author Andrey Minov
 */
public interface HistoryAction {
  /**
   * Gets user id as author of event.
   *
   * @return the user nickname, author of action.
   */
  String getUserId();

  /**
   * Gets source image of the action.
   *
   * @return the source image of the action.
   */
  Image getImage();

  /**
   * Gets create time of the action.
   *
   * @return the create time of the action
   */
  Instant getCreateTime();

  /**
   * Gets social event action type.
   *
   * @return the type of the social activity.
   */
  SocialActionType getType();
}
