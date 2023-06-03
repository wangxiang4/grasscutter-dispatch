package com.tianli.dispatch.vo

/**
 *<p>
 * Extend Result Vo
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/6/3
 */
class ResultVo<T> {

    /** searchResult */
    var result: T? = null

    /** extendedInfo */
    var extend: T? = null

}
