package bagguley.kapit

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.github.tomakehurst.wiremock.matching.EqualToPattern
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.extensions.wiremock.ListenerMode
import io.kotest.extensions.wiremock.WireMockListener
import io.kotest.matchers.be
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.NO_CONTENT
import org.http4k.core.Status.Companion.OK

@WireMockTest
class ApiRequestBuilderTest: ShouldSpec ({
    val wireMockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())
    val runtimeInfo = WireMockRuntimeInfo(wireMockServer)
    listener(WireMockListener(wireMockServer, ListenerMode.PER_SPEC))

    should("Make a GET request") {
        wireMockServer.stubFor(
            get(urlPathEqualTo("/api"))
                .withQueryParam("name-1", equalTo("value-1"))
                .withQueryParam("name-2", equalTo("value-2"))
                .withHeader("Accept", equalTo("application/json"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json; charset=utf-8")))

        get("${runtimeInfo.httpBaseUrl}/api") {
            query("name-1", "value-1")
            query("name-2", "value-2")
            header("Accept", "application/json")
        } should {
            haveStatus(OK)
            haveHeader("Content-Type", be("application/json; charset=utf-8"))
        }
    }

    should("Make a PATCH request") {
        wireMockServer.stubFor(
            patch(urlPathEqualTo("/api"))
                .withQueryParam("name-1", equalTo("value-1"))
                .withQueryParam("name-2", equalTo("value-2"))
                .withHeader("Accept", EqualToPattern("application/json"))
                .withRequestBody(EqualToPattern("""{ "hello": "world" }"""))
                .willReturn(aResponse()
                    .withStatus(204)
                    .withHeader("Content-Type", "application/json; charset=utf-8")))

        patch("${runtimeInfo.httpBaseUrl}/api") {
            query("name-1", "value-1")
            query("name-2", "value-2")
            header("Accept", "application/json")
            body("""{ "hello": "world" }""")
        } should {
            haveStatus(NO_CONTENT)
            haveHeader("Content-Type", be("application/json; charset=utf-8"))
        }
    }

    should("Make a POST request") {
        wireMockServer.stubFor(
            post(urlPathEqualTo("/api"))
                .withQueryParam("name-1", equalTo("value-1"))
                .withQueryParam("name-2", equalTo("value-2"))
                .withHeader("Accept", EqualToPattern("application/json"))
                .withRequestBody(EqualToPattern("""{ "hello": "world" }"""))
                .willReturn(aResponse()
                    .withStatus(201)
                    .withHeader("Content-Type", "application/json; charset=utf-8")))

        post("${runtimeInfo.httpBaseUrl}/api") {
            query("name-1", "value-1")
            query("name-2", "value-2")
            header("Accept", "application/json")
            body("""{ "hello": "world" }""")
        } should {
            haveStatus(CREATED)
            haveHeader("Content-Type", be("application/json; charset=utf-8"))
        }
    }

    should("Make a PUT request") {
        wireMockServer.stubFor(
            put(urlPathEqualTo("/api"))
                .withQueryParam("name-1", equalTo("value-1"))
                .withQueryParam("name-2", equalTo("value-2"))
                .withHeader("Accept", EqualToPattern("application/json"))
                .withRequestBody(EqualToPattern("""{ "hello": "world" }"""))
                .willReturn(aResponse()
                    .withStatus(204)
                    .withHeader("Content-Type", "application/json; charset=utf-8")))

        put("${runtimeInfo.httpBaseUrl}/api") {
            query("name-1", "value-1")
            query("name-2", "value-2")
            header("Accept", "application/json")
            body("""{ "hello": "world" }""")
        } should {
            haveStatus(NO_CONTENT)
            haveHeader("Content-Type", be("application/json; charset=utf-8"))
        }
    }

    should("Make a DELETE request") {
        wireMockServer.stubFor(
            delete(urlPathEqualTo("/api"))
                .withQueryParam("name-1", equalTo("value-1"))
                .withQueryParam("name-2", equalTo("value-2"))
                .withHeader("Accept", EqualToPattern("application/json"))
                .willReturn(aResponse()
                    .withStatus(204)
                    .withHeader("Content-Type", "application/json; charset=utf-8")))

        delete("${runtimeInfo.httpBaseUrl}/api") {
            query("name-1", "value-1")
            query("name-2", "value-2")
            header("Accept", "application/json")
        } should {
            haveStatus(NO_CONTENT)
            haveHeader("Content-Type", be("application/json; charset=utf-8"))
        }
    }

    should("Make a HEAD request") {
        wireMockServer.stubFor(
            head(urlPathEqualTo("/api"))
                .withQueryParam("name-1", equalTo("value-1"))
                .withQueryParam("name-2", equalTo("value-2"))
                .withHeader("Accept", EqualToPattern("application/json"))
                .willReturn(aResponse()
                    .withStatus(204)
                    .withHeader("Content-Type", "application/json; charset=utf-8")))

        head("${runtimeInfo.httpBaseUrl}/api") {
            query("name-1", "value-1")
            query("name-2", "value-2")
            header("Accept", "application/json")
        } should {
            haveStatus(NO_CONTENT)
            haveHeader("Content-Type", be("application/json; charset=utf-8"))
        }
    }

    should("Make an OPTIONS request") {
        wireMockServer.stubFor(
            options(urlPathEqualTo("/api"))
                .withQueryParam("name-1", equalTo("value-1"))
                .withQueryParam("name-2", equalTo("value-2"))
                .withHeader("Accept", EqualToPattern("application/json"))
                .willReturn(aResponse()
                    .withStatus(204)
                    .withHeader("Content-Type", "application/json; charset=utf-8")))

        options("${runtimeInfo.httpBaseUrl}/api") {
            query("name-1", "value-1")
            query("name-2", "value-2")
            header("Accept", "application/json")
        } should {
            haveStatus(NO_CONTENT)
            haveHeader("Content-Type", be("application/json; charset=utf-8"))
        }
    }

    should("Make a TRACE request") {
        wireMockServer.stubFor(
            trace(urlPathEqualTo("/api"))
                .withQueryParam("name-1", equalTo("value-1"))
                .withQueryParam("name-2", equalTo("value-2"))
                .withHeader("Accept", EqualToPattern("application/json"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json; charset=utf-8")
                    .withBody("""{ "hello": "world" }""")))

        trace("${runtimeInfo.httpBaseUrl}/api") {
            query("name-1", "value-1")
            query("name-2", "value-2")
            header("Accept", "application/json")
        } should {
            haveStatus(OK)
            haveHeader("Content-Type", be("application/json; charset=utf-8"))
            haveBody("""{ "hello": "world" }""")
        }
    }
})