package com.louie.learning.springboot;

import com.louie.learning.springboot.model.User;
import com.louie.learning.springboot.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class SpringbootEhcacheApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(SpringbootEhcacheApplicationTests.class);

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        logger.debug("进行Encache缓存测试");
        logger.debug("====生成第一个用户====");
        User user1 = new User();
        //生成第一个用户的唯一标识符 UUID
        String uuid1 = UUID.randomUUID().toString().replace("-", "");
        user1.setName("张三");
        user1.setAge(18);
        user1.setUuid(uuid1);
        int result = userService.save(user1);
        Assert.assertEquals("用户对象插入数据库失败", 1, result);

        //第一次查询
        user1 = userService.findByUuid(uuid1);
        logger.debug("第一次查询:{}", user1);
        //修改存储的值
        User user2 = new User();
        user2.setName("李四");
        user2.setAge(22);
        user2.setId(user1.getId());
        user2.setUuid(uuid1);
        UserService.mapUser.put(uuid1, user2);
        //通过缓存查询
        user2 = userService.findByUuid(uuid1);
        logger.debug("通过缓存查询:{}", user2);
        Assert.assertEquals(user1.getName(), user2.getName());
    }

}
