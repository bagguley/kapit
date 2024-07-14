package bagguley.kapit

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.Matcher
import io.kotest.matchers.be
import io.kotest.matchers.shouldBe
import org.http4k.core.*
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.lens.contentType

class ShouldInvokerPositiveTests : ShouldSpec ({
    should("Pass with a matching status") {
        val response = Response(Status.OK)

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveStatus(Status.OK)
        }
    }

    should("Pass with a matching header key") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveHeader("my_header")
        }
    }

    should("Pass with a matching header key and value") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveHeader("my_header", "my_header_value")
        }
    }

    should("Pass with a matching header value regex") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveHeader("my_header", Regex("my_header_.*"))
        }
    }

    should("Pass with a matching header value matcher") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveHeader("my_header", be("my_header_value"))
        }
    }

    should("Pass with a matching header value list") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveHeader("my_header", listOf("my_header_value"))
        }
    }

    should("Pass with a matching content-type") {
        val response = Response(Status.OK)
            .contentType(ContentType.APPLICATION_JSON)

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveContentType(ContentType.APPLICATION_JSON)
        }
    }

    should("Pass with a matching cookie") {
        val response = Response(Status.OK)
            .cookie(Cookie("my_cookie", "my_cookie_value"))

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveCookie(Cookie("my_cookie", "my_cookie_value"))
        }
    }

    should("Pass with a matching body matcher") {
        val response = Response(Status.OK)
            .body("This is the body string")

        val shouldInvoker = ShouldInvoker(response)

        val bodyMatcher : Matcher<Body> = bodyMatcher("This is the body string")

        shouldNotThrowAny {
            shouldInvoker.haveBody(bodyMatcher)
        }
    }

    should("Pass with a matching body string") {
        val response = Response(Status.OK)
            .body("This is the body string")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.haveBody("This is the body string")
        }
    }

    should("Pass with valid JSON") {
        val response = Response(Status.OK)
            .body("{}")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.beValidJson()
        }
    }

    should("Pass with a JSON object") {
        val response = Response(Status.OK)
            .body("{}")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.beJsonObject()
        }
    }

    should("Pass with a JSON array") {
        val response = Response(Status.OK)
            .body("[]")

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.beJsonArray()
        }
    }

    should("Pass with equal JSON") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key": "value"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.equalJson("{\"key\":\"value\"}")
        }
    }

    should("Pass with equal specified JSON") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2"
               }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.equalSpecifiedJson("""
                {
                    "key1": "value1"
                }
            """)
        }
    }

    should("Pass with matching JSON key") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.containJsonKey("key1")
        }
    }

    should("Pass with matching JSON key and value") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.containsJsonKeyValue("key1", "value1")
        }
    }

    should("Pass with matching JSON resource") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.matchJsonResource("/jsonResource.txt")
        }
    }

    should("Pass with matching resource") {
        val textBody = this.javaClass.getResourceAsStream("/textResource.txt")?.bufferedReader()?.use {
            it.readText()  } ?: throw AssertionError("File not found in resources")

        val response = Response(Status.OK)
            .body(textBody)

        val shouldInvoker = ShouldInvoker(response)

        shouldNotThrowAny {
            shouldInvoker.matchResource("/textResource.txt")
        }
    }

    should("Call shouldBlock using infix notation") {
        val response = Response(Status.OK)

        var counter = 0
        val shouldBlock: ShouldInvoker.() -> Unit = {
            counter++
        }

        response should shouldBlock

        counter shouldBe 1
    }
})

class ShouldInvokerNegativeTests : ShouldSpec ({
    should("Fail with a non-matching status") {
        val response = Response(Status.OK)

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveStatus(Status.BAD_REQUEST)
        }
    }

    should("Fail with a non-matching header key") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveHeader("not_my_header")
        }
    }

    should("Fail with a non-matching header key and matching value") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveHeader("not_my_header", "my_header_value")
        }
    }

    should("Fail with a matching header key and non-matching value") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveHeader("my_header", "not_my_header_value")
        }
    }

    should("Fail with a non-matching header value regex") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveHeader("my_header", Regex("not_my_header_.*"))
        }
    }

    should("Fail with a non-matching header value matcher") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveHeader("my_header", be("not_my_header_value"))
        }
    }

    should("Fail with a non-matching header value list") {
        val response = Response(Status.OK)
            .header("my_header", "my_header_value")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveHeader("my_header", listOf("not_my_header_value"))
        }
    }

    should("Fail with a non-matching content-type") {
        val response = Response(Status.OK)
            .contentType(ContentType.APPLICATION_JSON)

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveContentType(ContentType.TEXT_CSV)
        }
    }

    should("Fail with a matching cookie name and non-matching value") {
        val response = Response(Status.OK)
            .cookie(Cookie("my_cookie", "my_cookie_value"))

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveCookie(Cookie("my_cookie", "not_my_cookie_value"))
        }
    }

    should("Fail with a non-matching cookie name and matching value") {
        val response = Response(Status.OK)
            .cookie(Cookie("my_cookie", "my_cookie_value"))

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveCookie(Cookie("not_my_cookie", "my_cookie_value"))
        }
    }

    should("Fail with a non-matching body matcher") {
        val response = Response(Status.OK)
            .body("This is the body string")

        val shouldInvoker = ShouldInvoker(response)

        val bodyMatcher : Matcher<Body> = bodyMatcher("This is not the body string")

        shouldThrow<AssertionError> {
            shouldInvoker.haveBody(bodyMatcher)
        }
    }

    should("Fail with a non-matching body string") {
        val response = Response(Status.OK)
            .body("This is the body string")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.haveBody("This is not the body string")
        }
    }

    should("Fail with non-valid JSON") {
        val response = Response(Status.OK)
            .body("{}}")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.beValidJson()
        }
    }

    should("Fail with not a JSON object") {
        val response = Response(Status.OK)
            .body("[]")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.beJsonObject()
        }
    }

    should("Fail with not a JSON array") {
        val response = Response(Status.OK)
            .body("{}")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.beJsonArray()
        }
    }

    should("Fail with non-equal JSON") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key": "value"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.equalJson("{\"not_key\":\"not_value\"}")
        }
    }

    should("Fail with non-equal specified JSON") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.equalSpecifiedJson("""
                {
                    "key3": "value3"
                }
            """)
        }
    }

    should("Fail with non-matching JSON key") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.containJsonKey("key3")
        }
    }

    should("Fail with non-matching JSON key and matching value") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.containsJsonKeyValue("key3", "value1")
        }
    }

    should("Fail with matching JSON key and non-matching value") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.containsJsonKeyValue("key1", "value3")
        }
    }

    should("Fail with non-matching JSON resource") {
        val response = Response(Status.OK)
            .body("""
                {
                    "key1": "value1",
                    "key2": "value2",
                    "key3": "value3"
                }
            """)

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.matchJsonResource("/jsonResource.txt")
        }
    }

    should("Fail with non-matching resource") {
        val response = Response(Status.OK)
            .body("Some text")

        val shouldInvoker = ShouldInvoker(response)

        shouldThrow<AssertionError> {
            shouldInvoker.matchResource("/textResource.txt")
        }
    }
})