package com.tianli.dispatch.api

import com.tianli.dispatch.enums.SdkRspCodeEnum

/**
 *<p>
 * api result
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/5/30
 */
class SdkR<T>(data: T? = null,
              message: String? = null,
              code: Int? = SdkRspCodeEnum.OK.code
) : R<T>(code, null, null, data) {

    companion object {

        fun <T> ret(retCode: SdkRspCodeEnum?, data: T?): SdkR<T?> {
            return SdkR(data, retCode?.message, retCode?.code)
        }

        fun <T> ok(data: T?): SdkR<T?> {
            return SdkR(data, SdkRspCodeEnum.OK.message, SdkRspCodeEnum.OK.code)
        }

        fun <T> error(retCode: SdkRspCodeEnum): SdkR<T?> {
            return SdkR(null, retCode.message, retCode.code)
        }

        fun <T> error(retCode: SdkRspCodeEnum, message: String): SdkR<T?> {
            return SdkR(null, message, retCode.code)
        }

    }

    var message: String? = message

}
