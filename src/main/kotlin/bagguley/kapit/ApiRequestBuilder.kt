package bagguley.kapit

import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Method.*
import org.http4k.core.Request
import org.http4k.core.Response

class ApiRequestBuilder private constructor(private val requestFactory: RequestFactory) {
    private val _headers = mutableListOf<Pair<String, String>>()
    private val _queries = mutableListOf<Pair<String, String>>()
    private var _body: String? = null

    fun call(): Response {
        val request = requestFactory
            .createRequest()
            .headers(_headers)
            .let{ req -> if (_queries.isNotEmpty()) _queries.fold(req) { acc, pair -> acc.query(pair.first, pair.second) } else req }
            .let{ if (_body != null) it.body(_body!!) else it }

        val client = ApacheClient()

        return client(request)
    }

    fun query(key: String, value: String) {
        _queries.add(key to value)
    }

    fun header(key: String, value: String) {
        _headers.add(key to value)
    }

    fun body(body: String) {
        _body = body
    }

    companion object {
        private fun performRequestForMethod(method: Method, uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            val builder = ApiRequestBuilder { Request(method, uri) }
            return builder.apply(block).call()
        }

        fun get(uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            return performRequestForMethod(GET, uri, block)
        }

        fun patch(uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            return performRequestForMethod(PATCH, uri, block)
        }

        fun post(uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            return performRequestForMethod(POST, uri, block)
        }

        fun put(uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            return performRequestForMethod(PUT, uri, block)
        }

        fun delete(uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            return performRequestForMethod(DELETE, uri, block)
        }

        fun head(uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            return performRequestForMethod(HEAD, uri, block)
        }

        fun options(uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            return performRequestForMethod(OPTIONS, uri, block)
        }

        fun trace(uri: String, block: ApiRequestBuilder.() -> Unit): Response {
            return performRequestForMethod(TRACE, uri, block)
        }
    }
}