package ua.danit.photogramm.imgs.services.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.danit.photogramm.imgs.model.Image;

/**
 * Test for social services.
 *
 * @author Andrey Minov
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
public class ImagesSocialServiceTest {

  @Autowired
  private ImagesSocialService service;

  @Autowired
  private ImagesDataService storeService;

  private Image image;

  @Before
  public void setUp() throws Exception {
    image = storeService.saveNewImage("cooler", "test", "local");
  }

  @Test
  public void testLikeImage() {
    service.likeImage(image.getId(), "cooler");
    image = storeService.getImage(image.getId());
    assertEquals(1, image.getLikes());
  }

  @Test
  public void dislikeImage() {
    service.dislikeImage(image.getId(), "cooler");
    image = storeService.getImage(image.getId());
    assertEquals(0, image.getLikes());
  }

  @Test
  public void likeAndDislikeImage() {
    service.likeImage(image.getId(), "cooler");
    service.dislikeImage(image.getId(), "cooler");

    image = storeService.getImage(image.getId());
    assertEquals(0, image.getLikes());
  }

  @Test
  public void addComment() {
    service.addComment(image.getId(), "cooler", "This is amazing image!");
  }
}