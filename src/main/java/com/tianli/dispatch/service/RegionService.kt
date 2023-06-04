package com.tianli.dispatch.service

import com.tianli.dispatch.vo.QueryCurRegionRspVo

interface RegionService {

    fun queryRegionList(params: Map<String, Any>): String

    fun queryCurrentRegion(region: String, params: Map<String, Any>): QueryCurRegionRspVo

}
