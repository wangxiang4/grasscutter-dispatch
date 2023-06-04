package com.tianli.dispatch.controller;

import com.tianli.dispatch.service.RegionService
import com.tianli.dispatch.vo.QueryCurRegionRspVo
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

/**
 *<p>
 * Region Handler Controller
 *</p>
 *
 * @Author: wangxiang4
 * @since: 2023/6/4
 */
@RestController
@RequestMapping
class RegionController(private val regionService: RegionService) {

    @GetMapping("/query_region_list")
    fun queryRegionList(@RequestParam params: Map<String, Any>): Mono<String> {
        return Mono.just(regionService.queryRegionList(params))
    }

    @GetMapping("/query_cur_region/{region}")
    fun queryCurrentRegion(@PathVariable region: String, @RequestParam params: Map<String, Any>): Mono<QueryCurRegionRspVo> {
        return Mono.just(regionService.queryCurrentRegion(region, params))
    }

}
