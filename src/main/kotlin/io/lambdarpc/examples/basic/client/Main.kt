package io.lambdarpc.examples.basic.client

import io.lambdarpc.dsl.ServiceContext
import io.lambdarpc.examples.basic.endpoint
import io.lambdarpc.examples.basic.service.facade.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking

val serviceContext = ServiceContext(
    conf.serviceId to endpoint
)

fun main(): Unit = runBlocking(serviceContext) {
    println("add5(2) = ${add5(2)}")
    val m = 3
    println("eval5 { it + m } = ${eval5 { it + m }}")
    println("specializeAdd(5)(37) = ${specializeAdd(5)(37)}")
    println("executeAndAdd { it + 12 }(100) = ${executeAndAdd { it + 12 }(100)}")
}
