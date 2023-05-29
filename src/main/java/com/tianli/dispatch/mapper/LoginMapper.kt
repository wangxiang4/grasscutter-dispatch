package com.tianli.dispatch.mapper;
import com.tianli.dispatch.domain.User
import org.apache.ibatis.annotations.Select

interface LoginMapper {

    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    fun getUserById(id:Long?): User;

}
