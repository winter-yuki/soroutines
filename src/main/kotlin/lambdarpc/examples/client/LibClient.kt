package lambdarpc.examples.client

import kotlinx.coroutines.runBlocking
import lambdarpc.utils.Endpoint

fun main() {
    conf.endpoint = Endpoint.of("localhost", 8088)
    runBlocking {
        println("square(4) = ${square(4)}")
    }
}
