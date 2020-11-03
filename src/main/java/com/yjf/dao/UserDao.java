package com.yjf.dao;

import com.yjf.entity.Page;
import com.yjf.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @author 余俊锋
 * @date 2020/10/15 12:01
 * @Description
 */
@MapperScan
@Repository
public interface UserDao {

@Select(" select * from user where id=#{id}")
    User getUserById(Integer id);
@Select(" select count(*) as count from user where name like concat('%',#{name},'%')")
    Integer listAll(String name);
@Update("insert into user values(default,#{name},#{salary},#{age})")
    int addUser(User user);
@Update(" update user set name=#{name},salary=#{salary},age=#{age} where id=#{id}")
    int updateUser(User user);
@Update(" delete from user where id=#{id}")
    void deleteUserById(Integer id);

@Select(" select * from user where name like concat('%',#{name},'%') limit ${(page.pageCurrent-1)*page.pageSize},#{page.pageSize}")
    List<User> getUsersByName(@Param("name") String name, @Param("page") Page<List<User>> page);
}
