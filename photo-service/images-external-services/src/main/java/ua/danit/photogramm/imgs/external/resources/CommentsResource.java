package ua.danit.photogramm.imgs.external.resources;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.danit.photogramm.imgs.services.internal.ImagesSocialService;

/**
 * This is REST resource for comments management from users.
 *
 * @author Andrey Minov
 */
@RestController
@RequestMapping("/api/social/comment/")
@Validated
public class CommentsResource {
  private static final int MAX_COMMENT_LENGTH = 200;

  private final ImagesSocialService service;

  /**
   * Instantiates a new CommentsResource.
   *
   * @param service the service for managing player social actions.
   */
  @Autowired
  public CommentsResource(ImagesSocialService service) {
    this.service = service;
  }

  /**
   * Resource for add comment to selected user image.
   *
   * @param imageId  the image identity code.
   * @param username the username of user.
   * @param text     comment text, max 200 elements.
   */
  @PostMapping(value = "/add/{image}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Void> addComment(@PathVariable("image") long imageId,
                                         @NotEmpty @RequestParam("username") String username,
                                         @Size(max = MAX_COMMENT_LENGTH) @RequestParam(
                                             "text") String text) {
    service.addComment(imageId, username, text);
    return ResponseEntity.ok().build();
  }

  /**
   * Resource for remove comment to selected user image by identity
   *
   * @param commentId the comment identity code.
   */
  @PostMapping(value = "/remove/{commentId}",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Void> removeComment(@RequestParam("commentId") long commentId) {
    service.removeComment(commentId);
    return ResponseEntity.ok().build();
  }
}
