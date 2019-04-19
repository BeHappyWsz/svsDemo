package wsz.redis.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @description: 用户实体类
 * @author: WSZ
 * @create: 2019-04-18 14:48
 **/
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="userName")
    private String userName;
    @Column(name="password")
    private String password;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"userName\":\"" + userName + "\"" +
                ", \"password\":\"" + password + "\"" +
                '}';
    }
}