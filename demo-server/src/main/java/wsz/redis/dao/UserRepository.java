package wsz.redis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wsz.redis.entity.User;

import java.util.List;

/**
 * @description: 用户访问接口
 * @author: WSZ
 * @create: 2019-04-18 15:25
 **/
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.userName =:userName and u.password =:password")
    User login(@Param("userName") String userName, @Param("password") String password);

    @Query(value = "select u from User u where u.userName =:userName")
    User findByUserName(@Param("userName") String userName);

    @Query(value = "select u from User u where u.id in (:ids) order by u.id asc")
    List<User> findByIds(@Param("ids") Long[] ids);
}