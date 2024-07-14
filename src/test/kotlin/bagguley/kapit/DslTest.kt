package bagguley.kapit

import baggguley.kapit.ApiQueryBuilder
import baggguley.kapit.apiQuery
import baggguley.kapit.should
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import kotlin.test.Test

class DslTest {
    @Test
    fun `Check IP address`() {
        apiQuery {
            header("Accept", "application/json")
            ipApi("8.8.8.8")
        } should {
            haveStatus(OK)
        }
    }
}

fun ApiQueryBuilder.ipApi(address: String) {
    apiQuery { Request(Method.GET, "https://ipinfo.io/$address/geo") }
}