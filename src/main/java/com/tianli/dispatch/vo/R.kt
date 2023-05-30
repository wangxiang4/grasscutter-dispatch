package com.tianli.dispatch.vo

/**
 *<p>
 * api result
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/5/30
 */
class R<T> {

    companion object {

        const val SUCCESS:Int = 200

        const val FAIL:Int = 500

        const val UNAUTH:Int = 401

        fun <T> ok(): R<T?> {
            return restResult(null, SUCCESS, "成功")
        }

        fun <T> ok(data: T): R<T> {
            return restResult(data, SUCCESS, "成功")
        }

        fun <T> ok(data: T, msg: String?): R<T> {
            return restResult(data, SUCCESS, msg)
        }

        fun <T> ok(data: T, total: Long): R<T> {
            return restData(data, SUCCESS, null, total)
        }

        fun <T> error(): R<T?> {
            return restResult(null, FAIL, "失败")
        }

        fun <T> error(msg: String?): R<T?> {
            return restResult(null, FAIL, msg)
        }

        fun <T> error(data: T): R<T> {
            return restResult(data, FAIL, null)
        }

        fun <T> error(data: T, msg: String?): R<T> {
            return restResult(data, FAIL, msg)
        }

        fun <T> unAuth(msg: String?): R<T?> {
            return restResult(null, UNAUTH, msg)
        }


        private fun <T> restResult(data: T, code: Int, msg: String?): R<T> {
            val apiResult: R<T> = R()
            apiResult.code = code
            apiResult.data = data
            apiResult.msg = msg
            return apiResult
        }

        private fun <T> restData(data: T, code: Int, msg: String?, total: Long): R<T> {
            val apiData: R<T> = R()
            apiData.code = code
            apiData.data = data
            apiData.total = total
            apiData.msg = msg
            return apiData
        }
    }

    var code: Int = 0

    var msg: String? = null

    var total: Long = 0

    var data: T? = null

}
