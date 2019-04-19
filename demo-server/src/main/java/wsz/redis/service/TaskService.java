package wsz.redis.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wsz.redis.dao.UserRepository;
import wsz.redis.entity.User;

import java.util.UUID;

/**
 * @description: 任务服务
 * @author: WSZ
 * @create: 2019-04-18 16:22
 **/
@Slf4j
@Service
//@EnableScheduling
public class TaskService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserRepository userRepository;

    String KEY = "userList";

    /**
     * 生产者
     */
    @Scheduled(fixedRate=1000*3, initialDelay = 0)
    public void produce(){
        User user = new User();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setUserName(uuid.substring(0,8));
        user.setPassword(uuid.substring(8));
        redisTemplate.opsForList().leftPush(KEY, user.toString());
        log.info(user.toString());
    }

    /**
     * 消费者
     */
    @Scheduled(fixedRate=1000*2, initialDelay = 3000)
    public void consumer(){
        JSONObject obj = JSONObject.parseObject(redisTemplate.opsForList().rightPop(KEY));
        if(!StringUtils.isEmpty(obj)){
            User user = new User();
            user.setUserName(obj.getString("userName"));
            user.setPassword(obj.getString("password"));
            userRepository.save(user);
            log.info(user.toString());
        }
    }

}