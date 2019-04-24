package wsz.redis.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wsz.redis.dao.UserRepository;
import wsz.redis.entity.User;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * @description: 任务服务
 * @author: WSZ
 * @create: 2019-04-18 16:22
 **/
@Slf4j
@Service
public class TaskService implements ApplicationRunner {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    UserRepository userRepository;

    String KEY = "userList";
    String KEY_ZSET = "zsetUser";

    /**
     * 项目启动后，执行消费者方法
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumerList();
    }

    /**
     * 生产者将数据存到列表中
     */
//    @Scheduled(fixedRate=1000*3, initialDelay = 0)
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
//    @Scheduled(fixedRate=1000*2, initialDelay = 3000)
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

    /**
     * 生产者将数据存到ZSest有序集合中
     * 1.score延后5秒
     */
    @Scheduled(fixedRate= 500, initialDelay = 0)
    public void produceList() {
        User user = new User();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        user.setUserName(uuid.substring(0,8));
        user.setPassword(uuid.substring(8));
        redisTemplate.opsForZSet().add(KEY_ZSET, user.toString(), System.currentTimeMillis() + 5000);
    }

    /**
     * 消费者取出ZSest有序集合中的数据
     * 1.根据score取出到达当前时间的数据
     * 2.不存在，线程休眠
     * 3.存在，则获取并删除后，然后处理数据
     */
    public void consumerList() {
        while(!Thread.interrupted()){
            Set<String> dataSet = redisTemplate.opsForZSet().rangeByScore(KEY_ZSET, 0, System.currentTimeMillis(), 0, 1);
            if (dataSet.isEmpty()) {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    log.info(e.getMessage());
                    break;
                }
                continue;
            }
            // 获取到遍历数据并成功删除即将处理的数据
            Iterator<String> iterator =dataSet.iterator();
            while(iterator.hasNext()){
                String next = iterator.next();
                if (redisTemplate.opsForZSet().remove(KEY_ZSET, next) > 0) {
                    JSONObject userObj = JSONObject.parseObject(next);
                    User user = new User();
                    user.setUserName(userObj.getString("userName"));
                    user.setPassword(userObj.getString("password"));
                    userRepository.save(user);
                    log.info(user.toString());
                }
            }
        }
    }
}