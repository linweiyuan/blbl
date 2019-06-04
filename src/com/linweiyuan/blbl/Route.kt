package com.linweiyuan.blbl

import io.ktor.application.call
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.request.receiveParameters
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.response.respondBytes
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.thymeleaf.ThymeleafContent
import java.net.URL
import java.net.URLEncoder

fun Route.blbl(service: Service) {
    route("/") {
        get {
            call.respond(ThymeleafContent("blbl", emptyMap()))
        }

        post {
            val blbl = service.getPicUrlByParam(call.receiveParameters()["param"]!!)
            val fileName = "【${blbl.title}】封面.jpg"

            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.FileName,
                    URLEncoder.encode(fileName, Charsets.UTF_8.name())
                ).toString()
            )
            call.respondBytes((URL(blbl.picUrl).readBytes()))
        }
    }
}
