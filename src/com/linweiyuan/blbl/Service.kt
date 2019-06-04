package com.linweiyuan.blbl

import com.linweiyuan.utils.util.NetworkUtil

class Service {
    fun getPicUrlByParam(param: String): Blbl {
        val url = if (param.contains("av")) {
            if (param.startsWith("https")) {
                param
            } else {
                "${Constant.URL_API_BLBL}$param"
            }
        } else {
            "${Constant.URL_API_BLBL}av$param"
        }
        val doc = NetworkUtil.get(url)
        val title = doc.select("div.video-info h1 span").text()
        var picUrl = doc.select("meta[itemprop=image]").attr("content")
        if (picUrl.isBlank()) {
            // 番剧动态
            picUrl = doc.select("meta[property=og:image]").attr("content")
        }
        return Blbl(id = url.substring(url.indexOf("av")), title = title, picUrl = picUrl)
    }
}
