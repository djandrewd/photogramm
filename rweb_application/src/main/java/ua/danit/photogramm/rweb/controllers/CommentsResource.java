package ua.danit.photogramm.rweb.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.model.Comment;
import ua.danit.photogramm.imgs.services.internal.ImagesSocialService;
import ua.danit.photogramm.rweb.model.CommentEntity;
import ua.danit.photogramm.rweb.model.InternalUser;
import ua.danit.photogramm.users.data.model.User;

/**
 * Rest resource for social comments actions.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/social/comment")
public class CommentsResource {
  private static final int PAGE_SIZE = 5;

  private final ImagesSocialService socialService;

  /**
   * Instantiates a new Comments resource.
   *
   * @param socialService the social service for data storage.
   */
  @Autowired
  public CommentsResource(ImagesSocialService socialService) {
    this.socialService = socialService;
  }

  /**
   * List comments for provided image id.
   *
   * @param imageId the image id
   * @return the list of the comment for image.
   */
  @GetMapping(value = "/list/{image-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<CommentEntity> listComments(@PathVariable("image-id") long imageId) {

    return socialService.listTopComments(imageId, PAGE_SIZE, 0).stream()
                        .sorted(Comparator.comparing(Comment::getId)).map(this::mapToEntity)
                        .collect(Collectors.toList());
  }

  /**
   * Add comment comment entity.
   *
   * @param imageId        the image id
   * @param text           the text of the comment, max 200 chars!
   * @param authentication the authentication of the current user.
   * @return the comment entity stored into data store.
   */
  @PostMapping(value = "/add/{image-id}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public CommentEntity addComment(@PathVariable("image-id") long imageId,
                                  @Length(max = 200) @RequestParam("text") String text,
                                  Authentication authentication) {
    InternalUser internal = (InternalUser) authentication.getPrincipal();
    User self = internal.getEntity();
    return mapToEntity(socialService.addComment(imageId, self.getNickname(), text));
  }

  /**
   * Gets comment by selected id.
   *
   * @param commentId the comment id
   * @return the comment of the image by identity code.
   */
  @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CommentEntity getComment(@PathVariable("id") long commentId) {
    return mapToEntity(socialService.getComment(commentId));
  }

  /**
   * Edit comment comment entity with new text.
   *
   * @param commentId the comment id
   * @param text      the new text value for comment.
   * @return the comment entity holding newly added text.
   */
  @GetMapping(value = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CommentEntity editComment(@PathVariable("id") long commentId,
                                   @Length(max = 200) @RequestParam("text") String text) {
    return mapToEntity(socialService.editComment(commentId, text));
  }

  /**
   * Remove comment from image by comment identity code.
   *
   * @param commentId the comment identity code.
   */
  @GetMapping(value = "/remove/{id}")
  public void removeComment(@PathVariable("id") long commentId) {
    socialService.removeComment(commentId);
  }


  private CommentEntity mapToEntity(Comment c) {
    CommentEntity commentEntity = new CommentEntity(c.getId(), c.getUserId(), c.getText());
    // Self
    commentEntity.add(new Link(String.format("/social/comment/get/%d", c.getId()), "self"));
    // Edit
    commentEntity.add(new Link(String.format("/social/comment/edit/%d", c.getId()), "edit"));
    // Remove
    commentEntity.add(new Link(String.format("/social/comment/remove/%d", c.getId()), "remove"));
    return commentEntity;
  }
}
