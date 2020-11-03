package com.yjf.services;

import com.yjf.dao.UserDao;
import com.yjf.entity.Page;
import com.yjf.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;


/**
 * @author 余俊锋
 * @date 2020/10/15 12:04
 * @Description
 */
@Service
//事务只针对添加，修改，删除
//@CacheConfig(cacheNames = "books")
public class UserService {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    UserDao userDao;
    /**
     *@Description TODO
     *@author 余俊锋
     *@date 2020/10/20 16:16
     *@params id
     *@return com.yjf.entity.User
     * 1.默认
     * 2.#id
     * 3.#p0
     * 4.'redis:user:'+#user.id @@@@@
     * 5.#user.id+''
     * 6.'UserService.getUserById'+#user.id
     */

    //condition条件，true查询缓存，没有查数据库，然后把结果放入缓存
                    //false查询数据库，不会作缓存
   // HandlerInterceptor
    @Cacheable(cacheNames = "officeCache",key = "'redis:user:'+#id",condition = "#id!=null")
    public User getUser(Integer id){
        /*String key="redis:user:id:"+id;
        ValueOperations<Object, Object> string = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)){
            Object o = string.get(key);
        }*/
        return   userDao.getUserById(id);
    }
    public Integer getCount(String name){
        return   userDao.listAll(name);
    }

    //指定多个缓存key
    @Caching(put = {
            @CachePut(key = ""),@CachePut(key = "")
    })
    public int addUser(User user){
       return userDao.addUser(user);
    }
    //@CachePut(value="accountCache",key="#account.getName()")// 更新
    @CacheEvict(cacheNames = "officeCache",key = "'redis:user:'+#id",allEntries = true)
    public int updateUser(User user){
      return   userDao.updateUser(user);
    }


    public void deleteUser(Integer id){
        System.out.println("redis:--."+redisTemplate);
        userDao.deleteUserById(id);
    }

    public List<User> getUsersByName(String name, Page<List<User>> page){
       return userDao.getUsersByName(name,page);
    }
}
