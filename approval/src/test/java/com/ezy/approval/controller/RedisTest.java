package com.ezy.approval.controller;

import cn.hutool.core.util.StrUtil;
import com.ezy.approval.service.RedisService;
import com.ezy.common.constants.RedisConstans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Caixiaowei
 * @ClassName RedisTest
 * @Description
 * @createTime 2020/9/23$ 10:41$
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void test() {
        String spNo = "202009230001";
        redisService.incr(RedisConstans.APPROVAL_CALLBACK_RETRY + StrUtil.COLON + spNo, 1L);
        System.out.println(redisService.get(RedisConstans.APPROVAL_CALLBACK_RETRY + StrUtil.COLON + spNo));

    }

    @Test
    public void test_del() {
        String spNo = "202009230003";
        redisService.delete(RedisConstans.APPROVAL_CALLBACK_RETRY + StrUtil.COLON + spNo);
    }
}
