package bagguley.kapit

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import org.http4k.core.Body

fun bodyMatcher(expected: String): Matcher<Body> = object : Matcher<Body> {
    override fun test(value: Body): MatcherResult {
        val actualContent = value.toString()
        return MatcherResult(
            actualContent == expected,
            { "expected body to be '$expected' but was '$actualContent'"},
            { "body was '$expected' as expected"}
        )
    }
}