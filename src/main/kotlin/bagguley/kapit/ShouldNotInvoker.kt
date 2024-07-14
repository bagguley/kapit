package bagguley.kapit

import io.kotest.assertions.json.*
import io.kotest.matchers.Matcher
import io.kotest.matchers.resource.shouldNotMatchResource
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.Cookie
import org.http4k.kotest.*

class ShouldNotInvoker(private val response: Response) {
    fun haveStatus(status: Status) {
        response.shouldNotHaveStatus(status)
    }

    fun haveHeader(key: String) {
        response.shouldNotHaveHeader(key)
    }

    fun haveHeader(key: String, value: String) {
        response.shouldNotHaveHeader(key, value)
    }

    fun haveHeader(key: String, regex: Regex) {
        response.shouldNotHaveHeader(key, regex)
    }

    fun haveHeader(key: String, matcher: Matcher<String>) {
        response.shouldNotHaveHeader(key, matcher)
    }

    fun haveHeader(key: String, values: List<String?>) {
        response.shouldNotHaveHeader(key, values)
    }

    fun haveContentType(contentType: ContentType) {
        response.shouldNotHaveContentType(contentType)
    }

    fun haveCookie(cookie: Cookie) {
        response.shouldNotHaveSetCookie(cookie)
    }

    fun haveBody(matcher: Matcher<Body>) {
        response.shouldNotHaveBody(matcher)
    }

    fun haveBody(body: String) {
        response.shouldNotHaveBody(body)
    }

    fun beValidJson() {
        response.bodyString().shouldNotBeValidJson()
    }

    fun beJsonObject() {
        response.bodyString().shouldNotBeJsonObject()
    }

    fun beJsonArray() {
        response.bodyString().shouldNotBeJsonArray()
    }

    fun equalJson(expected: String) {
        response.bodyString().shouldNotEqualJson(expected)
    }

    fun equalSpecifiedJson(expected: String) {
        response.bodyString().shouldNotEqualSpecifiedJson(expected)
    }

    fun containJsonKey(key: String) {
        response.bodyString().shouldNotContainJsonKey(key)
    }

    fun containsJsonKeyValue(key: String, value: String) {
        response.bodyString().shouldNotContainJsonKeyValue(key, value)
    }

    fun matchJsonResource(resource: String) {
        response.bodyString().shouldNotMatchJsonResource(resource)
    }

    fun matchResource(path: String) {
        response.bodyString().shouldNotMatchResource(path)
    }
}

infix fun Response.shouldNot(shouldNotBlock: ShouldNotInvoker.() -> Unit) : Response {
    ShouldNotInvoker(this).apply(shouldNotBlock)
    return this
}