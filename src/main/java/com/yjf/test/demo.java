package com.yjf.test;

import com.yjf.configs.MybatisConfig;
import com.yjf.configs.SpringMvcConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 余俊锋
 * @date 2020/10/20 10:17
 * @Description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MybatisConfig.class, SpringMvcConfig.class})
@WebAppConfiguration //模拟web环境  测试web启动后的功能
public class demo {
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void string(){
        String key="aaa";
        ValueOperations<Object, Object> string = redisTemplate.opsForValue();
        //1.设值
        string.set(key,"admin");
        System.out.println("----------");
        System.out.println(string.get("aaa"));
        //2.设值值，并且设值过期时间
        string.set(key,"admin2",10, TimeUnit.SECONDS);
        //3.覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
        string.set("key","hello world");
        string.set("key","redis",6);
        System.out.println(string.get("key"));
        //4.设置键的字符串值并返回其旧值
        Object oldValue = string.getAndSet("key", "new value");
        System.out.println(oldValue);
        //5.append追加字符串，如果key不存在就创建
        string.append("key","xxxx");
        //6.返回value的长度
        string.size("key");
    }

    public void list(){
        ListOperations<Object, Object> list = redisTemplate.opsForList();
        //1.返回list的偿付
        list.size("key");
        //2.添加,集合或者，数组都可以
        list.leftPush("key","value");
        list.rightPush("key","value");
        //批量
        list.leftPushAll("key","a","b","c");
        //3.遍历查询
        list.range("key",0,-1);
        //4.修改指定下标的值
        list.set("key",0,"value");
        //5.从存储在键中的列表中删除等于值的元素的第一个计数事件。
        //计数参数以下列方式影响操作：
        //count> 0：删除等于从头到尾移动的值的元素。
        //count <0：删除等于从尾到头移动的值的元素。
        //count = 0：删除等于value的所有元素。
        list.remove("key",0,"value");
        //6.根据下标获取列表中的值
        Object value = list.index("key", 0);
        //7.从左边弹出元素
        list.leftPop("key");
        list.rightPop("key");

    }

    public void hash(){
        HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
        //1.添加值
        hash.put("user","id","1");
        //2.删除
        hash.delete("user","id","name","password");
        //3.小key是否存在
         hash.hasKey("user","id");
         //4.取值（小key）
        hash.get("user","id");
        //5获取存在的keys
        Set<Object> keys = hash.keys("user");
        //6.获取key所对应的散列表的大小个数
        Long size = hash.size("user");
        //7.通过map集合存值
        hash.putAll("user",new HashMap<>());
        //8.查询键值对
        Map<Object, Object> user = hash.entries("user");
        //9.查询values
        List<Object> user1 = hash.values("user");
        //迭代器scan...

    }

    public void set(){
        SetOperations<Object, Object> set = redisTemplate.opsForSet();
        //1.添加,数组形式
        set.add("name","jack","rose");
        //2.删除
        set.remove("name","jack");
        //3.弹出一个随机元素
        set.pop("name");
        //4.查询
        set.members("name");
        //5.获取长度
        set.size("user");
        //6.遍历
        Cursor<Object> curosr = set.scan("user", ScanOptions.NONE);
        while(curosr.hasNext()){
            System.out.println(curosr.next());
        }

    }

    public void zset(){
        ZSetOperations<Object, Object> zset = redisTemplate.opsForZSet();
        //1.添加
        zset.add("name","jack",98);
        //.2.添加有序集合
        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<>("rose5",96d);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<>("jack",99d);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        zset.add("name",tuples);
        //3.删除一个元素或者多个
        zset.remove("name","rose","jack");
        //4.(排序)返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
        zset.rank("name",tuples);
        //5.(排序)通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        zset.range("name",0,-1);
        //6.通过分数返回有序集合指定区间内的成员个数
        zset.count("name",96d,100d);
        //7.获取有序集合的成员数，内部调用的就是zCard方法
        zset.size("name"); //zcard
        //8.查询指定成员分数
        zset.score("name","jack");
        //9.(删除)移除指定索引位置的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        zset.removeRange("name",0,-2);
        //10遍历
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = zset.scan("zzset1", ScanOptions.NONE);
        while (cursor.hasNext()){
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            System.out.println(item.getValue() + ":" + item.getScore());
        }
    }


}
