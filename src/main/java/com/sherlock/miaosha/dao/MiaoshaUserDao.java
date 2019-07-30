package com.sherlock.miaosha.dao;

import com.sherlock.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;
@Mapper
public interface MiaoshaUserDao {

    @Select("select * from user where id = #{id}")
    public MiaoshaUser getById(@PathVariable("id") Long id);
}

