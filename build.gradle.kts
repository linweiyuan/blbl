import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val utils_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.31"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "blbl"
version = "0.0.1"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    maven("https://mvn.linweiyuan.com/repository/maven-public/")
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-gson:$ktor_version")
    compile("io.ktor:ktor-thymeleaf:$ktor_version")
    compile("com.linweiyuan:utils:$utils_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")

sourceSets["main"].resources.srcDirs("resources")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Main-Class" to "io.ktor.server.netty.EngineMain"
            )
        )
    }
}
