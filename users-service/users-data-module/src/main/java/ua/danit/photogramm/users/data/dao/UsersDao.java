package ua.danit.photogramm.users.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.danit.photogramm.users.data.model.User;

/**
 * Data access object for {@link ua.danit.photogramm.users.data.model.User} instances
 * and its subscriptions.
 *
 * @author Andrey Minov
 */
public interface UsersDao extends JpaRepository<User, String> {
  @Query("select u.subscribers from User u where u.nickname = :nickname")
  List<User> findAllSubscribers(@Param("nickname") String nickname);

  @Query("select u.subscriptions from User u where u.nickname = :nickname")
  List<User> findAllSubscriptions(@Param("nickname") String nickname);
}
