package ua.danit.photogramm.imgs.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.danit.photogramm.imgs.model.Comment;


/**
 * Data access object for comments.
 *
 * @author Andrey Minov
 */
public interface CommentsDao extends JpaRepository<Comment, Long> {

  List<Comment> findByImageId(long imageId, Pageable pageable);

  @Query("select c from Comment c join Subscription s on s.subscriber = c.userId "
         + " where s.subscription = :nickname AND not (s.subscriber = :nickname) ")
  List<Comment> findByUserId(@Param("nickname") String nickname, Pageable pageable);
}
