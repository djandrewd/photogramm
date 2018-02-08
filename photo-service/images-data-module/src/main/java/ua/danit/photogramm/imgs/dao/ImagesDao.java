package ua.danit.photogramm.imgs.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.danit.photogramm.imgs.model.Image;
import ua.danit.photogramm.imgs.model.LikeEntity;

/**
 * Data access object for images stored in data store.
 *
 * @author Andrey Minov
 */
public interface ImagesDao extends JpaRepository<Image, Long> {

  List<Image> getImagesByNickname(String nickname, Pageable pageable);

  @Query("select i from Image i join Subscription s"
         + " on i.nickname = s.subscription where s.subscriber=:nickname")
  List<Image> getImagesFeed(@Param("nickname") String nickname, Pageable pageable);

  @Modifying
  @Query("update Image u set u.likes = u.likes + 1 where u.id = :id")
  void incrementLikes(@Param("id") long id);

  @Modifying
  @Query("update Image u set u.likes = u.likes - 1 where u.id = :id")
  void decrementLikes(@Param("id") long id);

  @Modifying
  @Query(value = "UPDATE USERS SET posts = posts + 1 WHERE nickname = :nickname",
      nativeQuery = true)
  void updatePostsCount(@Param("nickname") String nickname);

  @Query(value = "select i from Image i join HashTag ht on ht.image = i where ht.tag = :tag")
  List<Image> getTaggedImages(@Param("tag") String tag, Pageable pageable);

  @Query(
      "select l from LikeEntity l join Subscription s on s.subscriber = l.userId "
      + " where s.subscription = :nickname AND not (s.subscriber = :nickname)")
  List<LikeEntity> selectLikes(@Param("nickname") String username, Pageable pageable);
}
