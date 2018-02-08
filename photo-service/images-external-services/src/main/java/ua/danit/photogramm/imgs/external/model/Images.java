package ua.danit.photogramm.imgs.external.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * Images entity used to wrap images list into HATEOAS response.
 *
 * @author Andrey Minov
 */
public class Images extends ResourceSupport {

  private List<Image> images;

  /**
   * Instantiates a new Images.
   *
   * @param images the images
   */
  public Images(List<Image> images) {
    this.images = images;
  }

  public List<Image> getImages() {
    return images;
  }
}
