package com.tianli.dispatch.enums

enum class SdkRspCodeEnum(val code: Int, val message: String) {
    OK(0, "OK"),
    SYSTEM_ERROR(-101, "系统错误"),
    PASSWORD_FORMAT_ERROR(-102, "密码格式错误，密码格式为8-30位，并且由数字、大小写字母、英文特殊符号两种以上组合"),
    PARAMETER_ERROR(-103, "参数错误"),
    CONFIG_MISSING(-104, "缺少配置"),
    PROTOCOL_LOAD_FAIL(-106, "协议加载失败"),
    CHANNEL_ERROR(-107, "渠道错误"),
    UNKNOWN(-111, "未知错误"),
    CLIENT_UPDATE_REQUIRED(-115, "请前往官网/商店下载最新版本"),
    ACCOUNT_OR_PASSWORD_ERROR(-202, "账号或密码错误"),
    USERNAME_ABNORMAL(-203, "用户名异常"),
    LOGIN_ABNORMAL(-204, "登录异常"),
    USERNAME_EMPTY(-205, "用户名为空"),
    IP_BLACKLISTED(-206, "您的ip或ip段在黑名单"),
    ILLEGAL_CHARACTERS_IN_ACCOUNT(-207, "账号内含有非法字符"),
    RELOGIN_REQUIRED(-210, "为了您的账号安全，请重新登录。"),
    SDK_DATABASE_READ_FAIL(-301, "SDK数据库读取获取用户信息失败"),
    GM_REQUEST_PARAMETER_EMPTY(-701, "GM请求参数不能为空"),
    WRONG_REQUEST(-702, "错误的请求"),
    GM_PARTITION_MUIP_MISSING_OR_INVALID(-703, "GM分区MUIP未配置或不存在"),
    PAY_PARTITION_OA_MISSING_OR_INVALID(-713, "PAY分区OA不存在或未配置"),

    CREATE_GUEST_ACCOUNT_FAIL(-2001, "创建游客账号失败"),
    CREATE_ACCOUNT_USER_FAIL(-2002, "创建账号用户失败"),
    CHANGE_PASSWORD_FAIL(-2003, "修改密码失败"),
    CREATE_HASH_FAIL(-2100, "创建hash失败"),
    VERIFICATION_EMAIL_SEND_ERROR(-3000, "验证码邮件发送错误"),
    EMAIL_VERIFICATION_CODE_ERROR(-3001, "邮箱验证码错误"),
    UNKNOWN_ACTION(-3101, "未知的ACTION");
}
