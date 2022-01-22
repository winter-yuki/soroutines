package io.lambdarpc.examples.basic.client

import io.lambdarpc.dsl.ServiceContext
import io.lambdarpc.dsl.cf
import io.lambdarpc.examples.basic.*
import io.lambdarpc.examples.basic.service1.NumpyArray
import io.lambdarpc.examples.basic.service1.facade.*
import io.lambdarpc.examples.basic.service2.facade.norm1
import io.lambdarpc.examples.basic.service2.facade.norm2
import kotlinx.coroutines.runBlocking

val serviceContext = ServiceContext(
    serviceId1 to endpoint1,
    serviceId2 to endpoint2
)

fun main(): Unit = runBlocking(serviceContext) {
    println("add5(2) = ${add5(2)}")
    val m = 3
    println("eval5 { it + m } = ${eval5 { it + m }}")
    println("specializeAdd(5)(37) = ${specializeAdd(5)(37)}")
    println("executeAndAdd { it + 12 }(100) = ${executeAndAdd { it + 12 }(100)}")
    println(
        "distance(Point(9.0, 1.0), Point(5.0, 4.0)) = " +
                "${distance(Point(9.0, 1.0), Point(5.0, 4.0))}"
    )
    val ps = listOf(Point(0.0, 0.0), Point(2.0, 1.0), Point(1.0, 1.5))
    println("normFilter($ps) { p, norm -> 2 <= norm(p) } = ${normFilter(ps) { p, norm -> 2 <= norm(p) }}")
    println("mapPoints(ps, norm()) = ${mapPoints(ps, norm1())}")
    println("mapPoints(ps, norm()) = ${mapPoints(ps, cf(norm2))}")
    println("numpyAdd(2, NumpyArray(40)) = ${numpyAdd(2, NumpyArray(40))}")
}
