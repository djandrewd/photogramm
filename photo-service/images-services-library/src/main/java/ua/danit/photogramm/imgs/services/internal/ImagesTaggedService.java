package ua.danit.photogramm.imgs.services.internal;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Strings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.danit.photogramm.imgs.dao.ImagesDao;
import ua.danit.photogramm.imgs.model.Image;

/**
 * Service for selection of images for defined tags from data storage.
 *
 * @author Andrey Minov
 */
@Service
public class ImagesTaggedService {
  private static final String ERROR_MSG_TEMPLATE = "'%s' must not be empty!";

  private final ImagesDao dao;
  private final Integer itemsOnPage;

  /**
   * Instantiates a new Images tags service.
   *
   * @param dao         the images data access object.
   * @param itemsOnPage the items on page.
   */
  @Autowired
  public ImagesTaggedService(ImagesDao dao,
                             @Value("${images.items-on-page:30}") Integer itemsOnPage) {
    this.dao = dao;
    this.itemsOnPage = itemsOnPage;
  }


  /**
   * Gets top tagged images on defined page ordered by submit time.
   *
   * @param tag  the tag to be selected.
   * @param page the page to be selected on.
   * @return the list of images with provided tag on page, ordered by time of submit.
   */
  public List<Image> getTaggedImages(String tag, int page) {
    checkArgument(!Strings.isNullOrEmpty(tag), ERROR_MSG_TEMPLATE, "tag");
    checkArgument(page >= 0, "Page %s must not be negative!", page);

    PageRequest pageRequest =
        new PageRequest(page, itemsOnPage, new Sort(Sort.Direction.DESC, "id"));
    return dao.getTaggedImages(tag, pageRequest);
  }
}
