package ua.danit.photogramm.rweb.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * User images containing links to image pages.
 *
 * @author Andrey Minov
 */
public class UserImages extends ResourceSupport {
  private List<ImageEntity> images;

  public UserImages(List<ImageEntity> images) {
    this.images = images;
  }

  public List<ImageEntity> getImages() {
    return images;
  }
}


