package baggguley.kapit

import org.http4k.client.ApacheClient
import org.http4k.core.Request
import org.http4k.core.Response

class ApiQueryBuilder {
    private lateinit var _apiQuery: ApiQuery
    private val _headers = mutableListOf<Pair<String, String>>()

    fun call(): Response {
        check(::_apiQuery.isInitialized) { "Request is not set" }

        var request = _apiQuery.createRequest()

        request = request.headers(_headers)

        val client = ApacheClient()

        val response = client(request)
        return response
    }

    fun apiQuery(apiQuery: ApiQuery) {
        _apiQuery = apiQuery
    }

    fun header(key: String, value: String) {
        _headers.add(key to value)
    }
}

fun apiQuery(builder: ApiQueryBuilder.() -> Unit): Response {
    return ApiQueryBuilder().apply(builder).call()
}

fun interface ApiQuery {
    fun createRequest(): Request
}
