package com.tianli.dispatch.controller;

import com.tianli.dispatch.api.R
import emu.grasscutter.net.proto.QueryCurrRegionHttpRspOuterClass.QueryCurrRegionHttpRsp
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping
class DispatchController {

    @GetMapping("/query_region_list")
    fun queryRegionList(): Mono<Int>{
        val rsp: QueryCurrRegionHttpRsp = QueryCurrRegionHttpRsp.newBuilder()
            .setRetcode(1).buildPartial()
        return Mono.just(rsp.retcode)
    }

    @GetMapping("/query_cur_region/{region}")
    fun queryCurrentRegion(@PathVariable region: String): Mono<Boolean>{
        return Mono.just(false)
    }

}
