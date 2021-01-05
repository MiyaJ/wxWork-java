package com.ezy.message;

import com.ezy.message.reception.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

/**
 * @author Caixiaowei
 * @ClassName RabbitTest.java
 * @Description TODO
 * @createTime 2020年07月30日 17:33:00
 */
@SpringBootTest
@Slf4j
@Test
public class RabbitTest {

    @Autowired
    private Producer producer;

    public void hello() {
        producer.send();
    }

}