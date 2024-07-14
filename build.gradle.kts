plugins {
    kotlin("jvm") version "2.0.20"
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
}

group = "bagguley.kapit"
version = "1.0-SNAPSHOT"

val http4kVersion = "5.30.0.0"
val kotestVersion = "5.9.1"
val kotestWireMockVersion = "3.1.0"
val wiremockVersion = "3.9.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-client-apache")
    implementation("org.http4k:http4k-testing-kotest")
    implementation("io.kotest:kotest-assertions-core:$kotestVersion")
    implementation("io.kotest:kotest-assertions-json:$kotestVersion")

    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-wiremock:$kotestWireMockVersion")
    testImplementation("org.wiremock:wiremock:$wiremockVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(18)
}

kover {
    reports {
        verify {
            rule {
                minBound(95)
            }
        }
    }
}