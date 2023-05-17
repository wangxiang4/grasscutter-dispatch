package com.tianli.auth.grasscutterauth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User {

    private String id;

    private String userName;

    private String nickName;

    private String password;

    private String email;

    private String phone;

    private String sex;

    private String loginIp;

    private LocalDateTime loginTime;

    private String status;

}
