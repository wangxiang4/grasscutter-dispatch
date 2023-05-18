package com.tianli.auth.grasscutterauth.mapper;
import com.tianli.auth.grasscutterauth.domain.User
import org.apache.ibatis.annotations.Select

interface LoginMapper {

    @Select("SELECT * FROM auth_user WHERE id = #{id}")
    fun getUserById(id:Long?): User;

}
