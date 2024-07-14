package bagguley.kapit

import io.kotest.assertions.json.*
import io.kotest.matchers.Matcher
import io.kotest.matchers.resource.shouldMatchResource
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.Cookie
import org.http4k.kotest.*

class ShouldInvoker(private val response: Response) {
    fun haveStatus(status: Status) {
        response.shouldHaveStatus(status)
    }

    fun haveHeader(key: String) {
        response.shouldHaveHeader(key)
    }

    fun haveHeader(key: String, value: String) {
        response.shouldHaveHeader(key, value)
    }

    fun haveHeader(key: String, regex: Regex) {
        response.shouldHaveHeader(key, regex)
    }

    fun haveHeader(key: String, matcher: Matcher<String>) {
        response.shouldHaveHeader(key, matcher)
    }

    fun haveHeader(key: String, values: List<String?>) {
        response.shouldHaveHeader(key, values)
    }

    fun haveContentType(contentType: ContentType) {
        response.shouldHaveContentType(contentType)
    }

    fun haveCookie(cookie: Cookie) {
        response.shouldHaveSetCookie(cookie)
    }

    fun haveBody(matcher: Matcher<Body>) {
        response.shouldHaveBody(matcher)
    }

    fun haveBody(body: String) {
        response.shouldHaveBody(body)
    }

    fun beValidJson() {
        response.bodyString().shouldBeValidJson()
    }

    fun beJsonObject() {
        response.bodyString().shouldBeJsonObject()
    }

    fun beJsonArray() {
        response.bodyString().shouldBeJsonArray()
    }

    fun equalJson(expected: String) {
        response.bodyString().shouldEqualJson(expected)
    }

    fun equalSpecifiedJson(expected: String) {
        response.bodyString().shouldEqualSpecifiedJson(expected)
    }

    fun containJsonKey(key: String) {
        response.bodyString().shouldContainJsonKey(key)
    }

    fun containsJsonKeyValue(key: String, value: String) {
        response.bodyString().shouldContainJsonKeyValue(key, value)
    }

    fun matchJsonResource(resource: String) {
        response.bodyString().shouldMatchJsonResource(resource)
    }

    fun matchResource(path: String) {
        response.bodyString().shouldMatchResource(path)
    }
}

infix fun Response.should(shouldBlock: ShouldInvoker.() -> Unit) : Response {
    ShouldInvoker(this).apply(shouldBlock)
    return this
}