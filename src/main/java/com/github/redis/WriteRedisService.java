package com.github.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/redis")
public class WriteRedisService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Qualifier(value = "specialTemplate")
    @Autowired
    RedisTemplate<String, Object> specialRedisTemplate;


    @Qualifier(value = "bytesTemplate")
    @Autowired
    RedisTemplate<byte[], Object> bytesRedisTemplate;


    @GetMapping
    public Set<String> write() {
        Set<String> keys = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            keys.add("prefix" + i + "_end");
        }
        keys.forEach(key -> {
            redisTemplate.opsForValue().set(key, key + "1");
        });
        return keys;
    }
    @GetMapping
    @RequestMapping("/s")
    public Set<String> writeSpecial() {
        Set<String> keys = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            keys.add("prefix" + i + "_end");
        }
        keys.forEach(key -> {
            specialRedisTemplate.opsForValue().set(key, key + "1");
        });
        return keys;
    }

    @GetMapping
    @RequestMapping("/sr")
    public Set<String> readSpecial() {
        Set<String> keys = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            keys.add("prefix" + i + "_end");
        }
        Set<String> values  = new HashSet<>();
        keys.forEach(key -> {
            values.add((String)specialRedisTemplate.opsForValue().get(key));
        });
        return values;
    }

    @GetMapping
    @RequestMapping("/sd")
    public Set<Boolean> deleteSpecial() {
        Set<String> keys = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            keys.add("prefix" + i + "_end");
        }
        Set<Boolean> values  = new HashSet<>();
        keys.forEach(key -> {
            values.add(specialRedisTemplate.delete(key));
        });
        return values;
    }



    @GetMapping
    @RequestMapping("/bw")
    public Set<byte[]> bytesWrite() {
        Set<byte[]> keys = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            keys.add(("prefix" + i + "_end").getBytes(StandardCharsets.UTF_8));
        }
        keys.forEach(key -> {
            bytesRedisTemplate.opsForValue().set(key, key + "1");
        });
        return keys;
    }

    @GetMapping
    @RequestMapping("/br")
    public Set<String> readBytes() {
        Set<byte[]> keys = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            keys.add(("prefix" + i + "_end").getBytes(StandardCharsets.UTF_8));
        }
        Set<String> values  = new HashSet<>();
        keys.forEach(key -> {
            values.add((String)bytesRedisTemplate.opsForValue().get(key));
        });
        return values;
    }

    @GetMapping
    @RequestMapping("/bd")
    public Set<Boolean> deleteBytes() {
        Set<byte[]> keys = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            keys.add(("prefix" + i + "_end").getBytes(StandardCharsets.UTF_8));
        }
        Set<Boolean> values  = new HashSet<>();
        keys.forEach(key -> {
            values.add(bytesRedisTemplate.delete(key));
        });
        return values;
    }
}
