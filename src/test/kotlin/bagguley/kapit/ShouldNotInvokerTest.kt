package bagguley.kapit

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.Matcher
import io.kotest.matchers.be
import io.kotest.matchers.shouldBe
import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.core.cookie.cookies
import org.http4k.lens.contentType

class ShouldNotInvokerPositiveTests: ShouldSpec({
    should("Pass with a non-matching status") {
        val response = Response(Status.OK)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveStatus(Status.BAD_REQUEST)
        }
    }

    should("Pass with a non-matching header key") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveHeader("not_my_header")
        }
    }

    should("Pass with a non-matching header key and matching value") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveHeader("not_my_header", "my_header_value")
        }
    }

    should("Pass with a matching header key and non-matching value") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveHeader("my_header", "not_my_header_value")
        }
    }

    should("Pass with a non-matching header value regex") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveHeader("my_header", Regex("not_my_header_.*"))
        }
    }

    should("Pass with a matching header and non-matching value matcher") {
        val response = Response(Status.OK)
        	.header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveHeader("my_header", be("not_my_header_value"))
        }
    }

    should("Pass with a non-matching header and matching value matcher") {
        val response = Response(Status.OK)
        	.header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveHeader("not_my_header", be("my_header_value"))
        }
    }

    should("Pass with a non-matching header value list") {
        val response = Response(Status.OK)
        	.header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveHeader("my_header", listOf("not_my_header_value"))
        }
    }

    should("Pass with a non-matching content-type") {
        val response = Response(Status.OK)
        	.contentType(ContentType.APPLICATION_JSON)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveContentType(ContentType.TEXT_PLAIN)
        }
    }

    should("Pass with a matching cookie@ key and non-matching cookie value") {
        val response = Response(Status.OK)
        	.cookie(Cookie("my_cookie", "my_cookie_value"))

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveCookie(Cookie("my_cookie", "not_my_cookie_value"))
        }
    }

    should("Pass with a non-matching cookie key and matching value") {
        val response = Response(Status.OK)
        	.cookie(Cookie("my_cookie", "my_cookie_value"))

        val shouldNotInvoker = ShouldNotInvoker(response)
        response.cookies().find { "my_cookie" == it.name }
        shouldNotThrowAny {
            shouldNotInvoker.haveCookie(Cookie("not_my_cookie", "my_cookie_value"))
        }
    }

    should("Pass with a non-matching body matcher") {
        val response = Response(Status.OK)
        	.body("This is the body string")

        val shouldNotInvoker = ShouldNotInvoker(response)

        val bodyMatcher : Matcher<Body> = bodyMatcher("This is not the body string")

        shouldNotThrowAny {
            shouldNotInvoker.haveBody(bodyMatcher)
        }
    }

    should("Pass with a non-matching body string") {
        val response = Response(Status.OK)
        	.body("This is the body string")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.haveBody("This is not the body string")
        }
    }

    should("Pass with non-valid JSON") {
        val response = Response(Status.OK)
        	.body("{}}")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.beValidJson()
        }
    }

    should("Pass with a non-JSON object") {
        val response = Response(Status.OK)
        	.body("[]")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.beJsonObject()
        }
    }

    should("Pass with a non-JSON array") {
        val response = Response(Status.OK)
        	.body("{}")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.beJsonArray()
        }
    }

    should("Pass with non-equal JSON") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key": "value"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.equalJson("{\"key1\":\"value1\"}")
        }
    }

    should("Pass with non-equal specified JSON") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.equalSpecifiedJson("""
                {
                    "key3": "value3"
                }
            """)
        }
    }

    should("Pass with non-matching JSON key") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.containJsonKey("key3")
        }
    }

    should("Pass with non-matching JSON key and matching value") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.containsJsonKeyValue("key3", "value1")
        }
    }

    should("Pass with matching JSON key and non-matching value") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.containsJsonKeyValue("key1", "value3")
        }
    }

    should("Pass with non-matching JSON resource") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2",
                    "key3": "value3"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.matchJsonResource("/jsonResource.txt")
        }
    }

    should("Pass with non-matching resource") {
        val response = Response(Status.OK)
        	.body("Some text")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldNotThrowAny {
            shouldNotInvoker.matchResource("/textResource.txt")
        }
    }

    should("Call shouldNotBlock using infix notation") {
        val response = Response(Status.OK)

        var counter = 0
        val shouldNotBlock: ShouldNotInvoker.() -> Unit = {
            counter++
        }

        response shouldNot shouldNotBlock

        counter shouldBe 1
    }
})

class ShouldNotInvokerNegativeTests : ShouldSpec({
    should("Fail with a matching status") {
        val response = Response(Status.OK)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveStatus(Status.OK)
        }
    }

    should("Fail with a matching header key") {
        val response = Response(Status.OK)
        	.header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveHeader("my_header")
        }
    }

    should("Fail with a matching header key and value") {
        val response = Response(Status.OK)
        	.header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveHeader("my_header", "my_header_value")
        }
    }

    should("Fail with a matching header value regex") {
        val response = Response(Status.OK)
        	.header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveHeader("my_header", Regex("my_header_.*"))
        }
    }

    should("Fail with a matching header value matcher") {
        val response = Response(Status.OK)
        	.header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveHeader("my_header", be("my_header_value"))
        }
    }

    should("Fail with a matching header value list") {
        val response = Response(Status.OK)
        	.header("my_header", "my_header_value")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveHeader("my_header", listOf("my_header_value"))
        }
    }

    should("Fail with a matching content-type") {
        val response = Response(Status.OK)
        	.contentType(ContentType.APPLICATION_JSON)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveContentType(ContentType.APPLICATION_JSON)
        }
    }

    should("Fail with a matching cookie") {
        val response = Response(Status.OK)
        	.cookie(Cookie("my_cookie", "my_cookie_value"))

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveCookie(Cookie("my_cookie", "my_cookie_value"))
        }
    }

    should("Fail with a matching body matcher") {
        val response = Response(Status.OK)
        	.body("This is the body string")

        val shouldNotInvoker = ShouldNotInvoker(response)

        val bodyMatcher : Matcher<Body> = bodyMatcher("This is the body string")

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveBody(bodyMatcher)
        }
    }

    should("Fail with a matching body string") {
        val response = Response(Status.OK)
        	.body("This is the body string")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.haveBody("This is the body string")
        }
    }

    should("Fail with valid JSON") {
        val response = Response(Status.OK)
        	.body("{}")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.beValidJson()
        }
    }

    should("Fail with a JSON object") {
        val response = Response(Status.OK)
        	.body("{}")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.beJsonObject()
        }
    }

    should("Fail with a JSON array") {
        val response = Response(Status.OK)
        	.body("[]")

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.beJsonArray()
        }
    }

    should("Fail with equal JSON") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key": "value"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.equalJson("{\"key\":\"value\"}")
        }
    }

    should("Fail with equal specified JSON") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.equalSpecifiedJson("""
                {
                    "key1": "value1"
                }
            """)
        }
    }

    should("Fail with matching JSON key") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.containJsonKey("key1")
        }
    }

    should("Fail with matching JSON key and value") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.containsJsonKeyValue("key1", "value1")
        }
    }

    should("Fail with matching JSON resource") {
        val response = Response(Status.OK)
        	.body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.matchJsonResource("/jsonResource.txt")
        }
    }

    should("Fail with matching resource") {
        val textBody = this.javaClass.getResourceAsStream("/textResource.txt")?.bufferedReader()?.use {
            it.readText()  } ?: throw AssertionError("File not found in resources")

        val response = Response(Status.OK)
        	.body(textBody)

        val shouldNotInvoker = ShouldNotInvoker(response)

        shouldThrow<AssertionError> {
            shouldNotInvoker.matchResource("/textResource.txt")
        }
    }
})