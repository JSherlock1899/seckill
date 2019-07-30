package com.sherlock.miaosha.dao;

import com.sherlock.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@PathVariable("id") int id);

    @Insert("inset into user (id,name) values (#{id},#{name})")
    public int insert(User user);
}
