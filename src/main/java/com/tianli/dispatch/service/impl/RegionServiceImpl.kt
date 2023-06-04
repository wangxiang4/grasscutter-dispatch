package com.tianli.dispatch.service.impl

import cn.hutool.core.codec.Base64
import cn.hutool.json.JSONUtil
import com.google.protobuf.ByteString
import com.tianli.dispatch.props.DispatchProperties
import com.tianli.dispatch.service.RegionService
import com.tianli.dispatch.util.CryptoUtil
import com.tianli.dispatch.vo.CustomConfigVo
import com.tianli.dispatch.vo.QueryCurRegionRspVo
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp
import emu.grasscutter.net.proto.QueryRegionListHttpRspOuterClass.QueryRegionListHttpRsp
import emu.grasscutter.net.proto.QueryRegionListHttpRspOuterClass.QueryRegionListHttpRsp.Builder
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo
import emu.grasscutter.net.proto.RegionSimpleInfoOuterClass.RegionSimpleInfo
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class RegionServiceImpl: RegionService {

    private var dispatchProperties: DispatchProperties? = null

    private var regionListResponseGlobal: String? = null

    private var regionListResponseCn: String? = null

    private var regionInfos: Map<String, RegionInfo>? = null

    constructor(dispatchProperties: DispatchProperties) {
        this.dispatchProperties = dispatchProperties

        val regionInfos = ConcurrentHashMap<String, RegionInfo>()
        val queryRegionListHttpRsp:Builder = QueryRegionListHttpRsp.newBuilder()
        queryRegionListHttpRsp.clientSecretKey = ByteString.copyFrom(CryptoUtil.DISPATCH_SEED)
        dispatchProperties.gateServer.forEach {
            val regionSimpleInfo = RegionSimpleInfo.newBuilder()
                .setName(it.name)
                .setTitle(it.title)
                .setType("DEV_PUBLIC")
                .setDispatchUrl("http://${dispatchProperties.accessAddress}/query_cur_region/${it.name}")
                .build()
            val regionInfo = RegionInfo.newBuilder()
                .setGateserverIp(it.ip)
                .setGateserverPort(it.port)
                .setSecretKey(ByteString.copyFrom(CryptoUtil.DISPATCH_SEED))
                .build()
            regionInfos[it.name!!] = regionInfo
            queryRegionListHttpRsp.addRegionList(regionSimpleInfo)
        }
        this.regionInfos = regionInfos
        val customConfig = CustomConfigVo(
            "0",
            "true",
            "false",
            "false",
            "pm|fk|add",
            "0"
        )
        val customConfigCn:ByteArray = JSONUtil.toJsonStr(customConfig).toByteArray()
        CryptoUtil.xor(customConfigCn, CryptoUtil.DISPATCH_KEY!!)
        queryRegionListHttpRsp.clientCustomConfigEncrypted = ByteString.copyFrom(customConfigCn)
        queryRegionListHttpRsp.enableLoginPc = true
        this.regionListResponseCn = Base64.encode(queryRegionListHttpRsp.toString().toByteArray())

        customConfig.sdkenv = "2"
        customConfig.checkdevice = "false"
        val customConfigGlobal:ByteArray = JSONUtil.toJsonStr(customConfig).toByteArray()
        CryptoUtil.xor(customConfigGlobal, CryptoUtil.DISPATCH_KEY!!)
        this.regionListResponseGlobal = Base64.encode(queryRegionListHttpRsp.toString().toByteArray())
    }

    override fun queryRegionList(params: Map<String, Any>): String {
        // todo currently supporting cn
        return regionListResponseCn!!
    }

    override fun queryCurrentRegion(region: String, params: Map<String, Any>): QueryCurRegionRspVo {

        val versionName = params["version"]?.toString()
        val regionInfo = regionInfos?.get(region)
        val keyId = params["key_id"]?.toString()
        val dispatchSeed = params["dispatchSeed"]?.toString()
        val queryCurrRegionHttpRsp = QueryCurrRegionHttpRsp.newBuilder().setRegionInfo(regionInfo ?: RegionInfo.getDefaultInstance()).build();
        var regionData = Base64.encode(queryCurrRegionHttpRsp.toString().toByteArray())
        return CryptoUtil.encryptAndSignRegionData(regionData.toByteArray(), keyId)!!

    }

}
