package ua.danit.photogramm.imgs.services.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.danit.photogramm.imgs.dao.ImagesDao;
import ua.danit.photogramm.imgs.model.Image;

/**
 * This data service for construction user images feed.
 *
 * @author Andrey Minov
 */
@Service
public class ImagesFeedService {
  private static final String ERROR_MSG_TEMPLATE = "'%s' must not be empty!";

  private final ImagesDao dao;
  private final Integer itemsOnPage;

  /**
   * Instantiates a new Images feed service.
   *
   * @param dao         the images data access object.
   * @param itemsOnPage the items on page.
   */
  @Autowired
  public ImagesFeedService(ImagesDao dao,
                           @Value("${images.items-on-page:30}") Integer itemsOnPage) {
    this.dao = dao;
    this.itemsOnPage = itemsOnPage;
  }

  /**
   * Gets top N feed images defined by property <code>images.items-on-page</code> on
   * requested page. Images are sorted in descending order by id.
   *
   * @param nickname the nickname of user to select images.
   * @param page     the page started from 0 to select images.
   * @return the user images selected and ordered by desc on requested page.
   * @throws IllegalArgumentException in case nickname is empty.
   */
  public List<Image> getUserFeed(String nickname, int page) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");

    PageRequest pageRequest =
        new PageRequest(page, itemsOnPage, new Sort(Sort.Direction.DESC, "id"));
    return dao.getImagesFeed(nickname, pageRequest);
  }
}
