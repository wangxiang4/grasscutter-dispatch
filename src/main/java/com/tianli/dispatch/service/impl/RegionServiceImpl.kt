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
import emu.grasscutter.net.proto.RegionInfoOuterClass.RegionInfo
import emu.grasscutter.net.proto.RegionSimpleInfoOuterClass.RegionSimpleInfo
import org.springframework.stereotype.Service

@Service
class RegionServiceImpl(dispatchProperties: DispatchProperties) : RegionService {


    private var regionListResponse: String? = null

    private var regionInfos: Map<String, RegionInfo>? = null

    init {
        val regionInfos = HashMap<String, RegionInfo>()
        val queryRegionListHttpRspBuilder = QueryRegionListHttpRsp.newBuilder()

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
            queryRegionListHttpRspBuilder.addRegionList(regionSimpleInfo)
        }
        this.regionInfos = regionInfos

        //TODO: more graceful
        val customConfig = CustomConfigVo(
            "2", //0 for CN and 2 for OS
            "false", //true for CN and false for OS
            "false",
            "false",
            "pm|fk|add",
            "0"
        )
        val customConfigCn: ByteArray = JSONUtil.toJsonStr(customConfig).toByteArray()
        CryptoUtil.xor(customConfigCn, CryptoUtil.DISPATCH_KEY!!)
        queryRegionListHttpRspBuilder.setClientSecretKey(ByteString.copyFrom(CryptoUtil.DISPATCH_SEED))
            .setClientCustomConfigEncrypted(ByteString.copyFrom(customConfigCn))
            .setEnableLoginPc(true)
        this.regionListResponse = Base64.encode(queryRegionListHttpRspBuilder.build().toByteString().toByteArray())
    }

    override fun queryRegionList(params: Map<String, Any>): String {
        // todo currently supporting cn
        return regionListResponse!!
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
