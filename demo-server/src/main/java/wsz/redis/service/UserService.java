package wsz.redis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import wsz.redis.dao.UserRepository;
import wsz.redis.entity.User;
import wsz.redis.vo.UserVo;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 用户
 * @author: WSZ
 * @create: 2019-04-18 14:42
 **/
@Service
public class UserService {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserRepository userRepository;

    String KEY = "user:";

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    public User login(String userName, String password) throws Exception {
        User user = userRepository.login(userName, password);
        if (StringUtils.isEmpty(user)){
            throw new Exception("账号不存在");
        }
        redisTemplate.opsForValue().set(KEY + user.getId(), user.toString(), 60, TimeUnit.SECONDS);
        return user;
    }

    /**
     * 用户注册
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public User registerUser(String userName, String password) throws Exception{
        User user = userRepository.findByUserName(userName);
        if(!StringUtils.isEmpty(user)){
            throw new Exception("已存在同名用户");
        }
        user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        return userRepository.save(user);
    }

    /**
     * 事务测试
     * 1.当前类中没事务的方法直接调用事务方法，则事务方法不起作用：Spring事务为代理实现
     * 2.事务异常不能在方法内部try-catch，必须throws，否则事务失效：rollbackFor
     * @param userName
     * @param password
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void tran(String userName, String password){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        userRepository.save(user);
        System.out.println(1/0);
        User user2 = new User();
        user2.setUserName(userName);
        user2.setPassword(password);
        userRepository.save(user2);
    }

    /**
     * 更新密码
     * @param userName
     * @param password
     * @return
     */
    public User update(String userName, String password) throws Exception {
        User user = userRepository.findByUserName(userName);
        if(user == null){
            throw new Exception("账户不存在");
        }
        user.setPassword(password);
        return userRepository.save(user);
    }

    /**
     * 删除
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    public User deleteUser(String userName, String password) throws Exception {
        User user = userRepository.findByUserName(userName);
        if(user == null){
            throw new Exception("账户不存在");
        }
        userRepository.delete(user);
        return user;
    }

    /**
     * 删除
     * @param ids
     * @return
     * @throws Exception
     */
    public int deleteUser(Long[] ids) throws Exception {
        return userRepository.deleteByIds(ids);
    }

    /**
     * 搜索用户
     * @param ids
     * @return
     */
    public List<User> findUser(Long[] ids) {
        if(ids!= null && ids.length > 0){
            return userRepository.findByIds(ids);
        }
        return userRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    /**
     *
     * @param vo
     * @return
     */
    public Page<User> findByPage(UserVo vo){
        Pageable pageable = PageRequest.of(vo.getPage(), vo.getSize());
        if (StringUtils.isEmpty(vo.getUserName())) {
            return userRepository.findAll(pageable);
        }
        return userRepository.findByPage(vo.getUserName(), pageable);
    }
}