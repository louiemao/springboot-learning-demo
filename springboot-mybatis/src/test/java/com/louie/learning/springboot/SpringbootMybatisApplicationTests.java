package com.louie.learning.springboot;

import com.louie.learning.springboot.model.User;
import com.louie.learning.springboot.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        //create
        User user = new User();
        user.setId(UUID.randomUUID().toString().replace("-", ""));
        user.setCreateTime(new Date());
        user.setUserCd("admin");
        user.setUserName("管理员");
        int result = userService.insertSelective(user);
        Assert.assertEquals(1,result);

        //read
        user=userService.selectByPrimaryKey("1");
        Assert.assertNotNull(user);

        //update
        user.setModifyTime(new Date());
        result=userService.updateByPrimaryKeySelective(user);
        Assert.assertEquals(1,result);

        //delete
        result=userService.deleteByPrimaryKey(user.getId());
        Assert.assertEquals(1,result);

    }
}
