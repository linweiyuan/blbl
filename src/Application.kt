package com.linweiyuan

import com.linweiyuan.blbl.blbl
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.request.path
import io.ktor.response.respondText
import io.ktor.routing.routing
import io.ktor.thymeleaf.Thymeleaf
import org.slf4j.event.Level
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024)
        }
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/"
            suffix = ".html"
            characterEncoding = "UTF-8"
        })
    }

    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText("系统异常 -> ${e.localizedMessage}")
        }
    }

    routing {
        blbl(com.linweiyuan.blbl.Service())
    }
}
