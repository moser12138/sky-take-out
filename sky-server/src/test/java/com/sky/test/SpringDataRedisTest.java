package com.sky.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate() {
        System.out.println(redisTemplate);
        ValueOperations valueOperation = redisTemplate.opsForValue();
        HashOperations hashOperation = redisTemplate.opsForHash();
        ListOperations listOperation = redisTemplate.opsForList();
        SetOperations setOperation = redisTemplate.opsForSet();
        ZSetOperations zSetOperation = redisTemplate.opsForZSet();
    }

    /**
     * 测试操作String
     */
    @Test
    public void testString() {
        redisTemplate.opsForValue().set("city", "稻城");
        String city = (String) redisTemplate.opsForValue().get("city");
        System.out.println(city);
        redisTemplate.opsForValue().set("age", "18", 3, TimeUnit.MINUTES);
        redisTemplate.opsForValue().setIfAbsent("name", "djy");
        redisTemplate.opsForValue().setIfAbsent("name", "yjd");
    }

    /**
     * 测试操作哈希类数据
     */
    @Test
    public void testHash() {
        // hset hget hdel hkeys hvals
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("user:1", "name", "djy");
        hashOperations.put("user:1", "age", "18");

        Object name = hashOperations.get("user:1", "name");
        System.out.println(name);

        Set keys = hashOperations.keys("user:1");
        System.out.println(keys);

        List values = hashOperations.values("user:1");
        System.out.println(values);

        hashOperations.delete("user:1", "name");

    }

    /**
     * 测试操作列表类数据
     */
    @Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();

        listOperations.leftPush("list", "aaa");
        listOperations.leftPush("list", "bbb");
        listOperations.leftPush("list", "ccc");
        listOperations.leftPushAll("list", "ddd", "eee", "fff");

        List list = listOperations.range("list", 0, -1);
        System.out.println(list);

        listOperations.rightPop("list");

        Long size = listOperations.size("list");
        System.out.println(size);
    }

    /**
     * 测试操作集合类数据
     */
    @Test
    public void testSet() {
        // sadd smembers scard sinter sunion srem
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("set1", "aaa", "bbb", "ccc", "ddd", "eee");
        setOperations.add("set2", "aaa", "bbb", "fff", "ggg", "hhh");

        Set members = setOperations.members("set1");
        System.out.println(members);

        Long size = setOperations.size("set1");
        System.out.println(size);

        Set intersection = setOperations.intersect("set1", "set2");
        System.out.println(intersection);

        Set union = setOperations.union("set1", "set2");
        System.out.println(union);

        setOperations.remove("set1", "aaa");
    }

    /**
     * 测试操作有序集合类数据
     */
    @Test
    public void testZSet() {
        // zadd zrange zincrby zrem
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add("zset", "aaa", 1);
        zSetOperations.add("zset", "bbb", 2);
        zSetOperations.add("zset", "ccc", 3);

        Set zset = zSetOperations.range("zset", 0, -1);
        System.out.println(zset);

        zSetOperations.incrementScore("zset", "aaa", 10);

        zSetOperations.remove("zset", "bbb", "ccc");
    }

    /**
     * 通用命令操作
     */
    @Test
    public void testCommon() {
        // keys exists type del
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);

        Boolean name = redisTemplate.hasKey("name");
        Boolean set1 = redisTemplate.hasKey("set1");

        for (Object key : keys){
            DataType type = redisTemplate.type(key);
            System.out.println(type);
        }

        redisTemplate.delete("list");
    }
}
