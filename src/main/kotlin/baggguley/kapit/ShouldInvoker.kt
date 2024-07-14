package baggguley.kapit

import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.kotest.shouldHaveStatus

class ShouldInvoker(private val response: Response) {
    fun haveStatus(status: Status) {
        response shouldHaveStatus(status)
    }
}

infix fun Response.should(shouldBlock: ShouldInvoker.() -> Unit) : Response {
    ShouldInvoker(this).apply(shouldBlock)
    return this
}