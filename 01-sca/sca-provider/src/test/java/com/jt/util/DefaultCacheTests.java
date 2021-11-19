package com.jt.util;

import com.jt.common.cache.DefaultCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
public class DefaultCacheTests {

    @Autowired
    private DefaultCache defaultCache;

    @Test
    public void testToString(){
        System.out.println(defaultCache);
    }
}
