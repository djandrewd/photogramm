package ua.danit.photogramm.imgs.services.internal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.danit.photogramm.imgs.dao.ImagesDao;
import ua.danit.photogramm.imgs.model.HashTag;
import ua.danit.photogramm.imgs.model.Image;
import ua.danit.photogramm.imgs.services.util.HashTags;

/**
 * Data storage service for images located in store. It will work directly with database
 * instance and you can put caching of results on top of it.
 *
 * @author Andrey Minov
 */
@Service
public class ImagesDataService {
  private static final String ERROR_MSG_TEMPLATE = "'%s' must not be empty!";

  private final ImagesDao dao;
  private final Integer itemsOnPage;

  /**
   * Instantiates a new Images data service.
   *
   * @param dao         the images data access object.
   * @param itemsOnPage the items on page.
   */
  @Autowired
  public ImagesDataService(ImagesDao dao,
                           @Value("${images.items-on-page:30}") Integer itemsOnPage) {
    this.dao = dao;
    this.itemsOnPage = itemsOnPage;
  }

  /**
   * Gets top N user images defined by property <code>images.items-on-page</code> on
   * requested page. Images are sorted in descending order by id.
   *
   * @param nickname the nickname of user to select images.
   * @param page     the page started from 0 to select images.
   * @return the user images selected and ordered by desc on requested page.
   * @throws IllegalArgumentException in case nickname is empty.
   */
  public List<Image> getUserImages(String nickname, int page) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");

    PageRequest pageRequest =
        new PageRequest(page, itemsOnPage, new Sort(Sort.Direction.DESC, "id"));
    return dao.getImagesByNickname(nickname, pageRequest);
  }

  /**
   * Save new image into data storage and return generated instance as result.
   *
   * @param nickname the nickname of the uses who store images.
   * @param name     the name of image.
   * @param url      the external URL of image to store.
   * @return new images instance in data storage.
   * @throws IllegalArgumentException in case nickname,name  or url is empty.
   */
  @Transactional
  public Image saveNewImage(String nickname, String name, String url) {
    checkArgument(!isNullOrEmpty(nickname), ERROR_MSG_TEMPLATE, "nickname");
    checkArgument(!isNullOrEmpty(name), ERROR_MSG_TEMPLATE, "name");
    checkArgument(!isNullOrEmpty(url), ERROR_MSG_TEMPLATE, "url");

    Image image = new Image();
    image.setName(name);
    image.setNickname(nickname);
    image.setUrl(url);

    Set<HashTag> tags = HashTags.parseName(name).stream().map(v -> {
      HashTag tag = new HashTag();
      tag.setImage(image);
      tag.setTag(v);
      return tag;
    }).collect(Collectors.toSet());
    image.setTags(tags);

    dao.updatePostsCount(nickname);
    return dao.saveAndFlush(image);
  }

  /**
   * Gets image instance by it's identity code.
   *
   * @param id the identity code of the image.
   * @return the image instance, null is not exists.
   */
  public Image getImage(long id) {
    return dao.findOne(id);
  }
}
