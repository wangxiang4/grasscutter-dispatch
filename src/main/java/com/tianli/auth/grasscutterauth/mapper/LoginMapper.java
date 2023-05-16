package com.tianli.auth.grasscutterauth.mapper;

import com.tianli.auth.grasscutterauth.domain.User;
import org.apache.ibatis.annotations.Select;

public interface LoginMapper {

    @Select("SELECT * FROM auth_user WHERE id = #{id}")
    User getUserById(Long id);

}
