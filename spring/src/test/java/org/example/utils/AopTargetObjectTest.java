package org.example.utils;

import org.example.module.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author nurhier
 * @date 2020/2/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AopTargetObjectTest {
    @Resource
    private UserService userService;

    @Test
    public void testTargetObject() throws Exception {
        Object object = AopTargetObjectUtils.getTargetObject(userService);
        Assert.assertNotEquals(userService, object);
        Assert.assertNotNull(object);
    }
}
