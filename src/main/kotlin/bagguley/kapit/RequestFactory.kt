package bagguley.kapit

import org.http4k.core.Request

fun interface RequestFactory {
    fun createRequest(): Request
}