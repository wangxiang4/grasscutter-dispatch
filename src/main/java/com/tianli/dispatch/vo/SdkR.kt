package com.tianli.dispatch.vo

/**
 *<p>
 * api result
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/5/30
 */
class SdkR<T>(var data: T?, message: String? = null, code: Int = SdkRspCode.OK.code) {

    companion object {
        fun <T> ret(retcode: SdkRspCode = SdkRspCode.OK, data: T?): SdkR<T?> {
            return SdkR(data, retcode.message, retcode.code)
        }

        fun <T> ok(data: T?): SdkR<T?> {
            return SdkR(data, SdkRspCode.OK.message, SdkRspCode.OK.code)
        }

        fun <T> error(retcode: SdkRspCode): SdkR<T?> {
            return SdkR(null, retcode.message, retcode.code)
        }
        fun <T> error(retcode: SdkRspCode, message: String): SdkR<T?> {
            return SdkR(null, message, retcode.code)
        }
    }


}
