plugins {
    kotlin("jvm") version "2.0.0"
}

group = "bagguley.kapit"
version = "1.0-SNAPSHOT"

val kotestVersion = "5.9.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.25.0.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-client-apache")
    implementation("org.http4k:http4k-testing-kotest")
    implementation("io.kotest:kotest-assertions-core:$kotestVersion")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(18)
}