package io.lambdarpc.dsl

import io.lambdarpc.serialization.Serializer
import io.lambdarpc.utils.AccessName
import io.lambdarpc.utils.ServiceId
import io.lambdarpc.utils.an
import kotlinx.coroutines.CoroutineScope
import kotlin.properties.ReadOnlyProperty

class Configuration(val serviceId: ServiceId)

fun <D> def(accessName: String?, definitionProvider: (AccessName) -> D) =
    ReadOnlyProperty { _: Nothing?, property ->
        definitionProvider(accessName?.an ?: property.name.an)
    }

inline fun <reified R> Configuration.def(
    rs: Serializer<R> = s(),
    accessName: String? = null
) = def<suspend CoroutineScope.() -> R>(accessName) { name ->
    Declaration0(name, serviceId, rs)
}

inline fun <reified A, reified R> Configuration.def(
    s1: Serializer<A> = s(),
    rs: Serializer<R> = s(),
    accessName: String? = null
) = def<suspend CoroutineScope.(A) -> R>(accessName) { name ->
    Declaration1(name, serviceId, s1, rs)
}

inline fun <reified A, reified B, reified R> Configuration.def(
    s1: Serializer<A> = s(),
    s2: Serializer<B> = s(),
    rs: Serializer<R> = s(),
    accessName: String? = null
) = def<suspend CoroutineScope.(A, B) -> R>(accessName) { name ->
    Declaration2(name, serviceId, s1, s2, rs)
}

inline fun <reified A, reified B, reified C, reified R> Configuration.def(
    s1: Serializer<A> = s(),
    s2: Serializer<B> = s(),
    s3: Serializer<C> = s(),
    rs: Serializer<R> = s(),
    accessName: String? = null
) = def<suspend CoroutineScope.(A, B, C) -> R>(accessName) { name ->
    Declaration3(name, serviceId, s1, s2, s3, rs)
}

inline fun <reified A, reified B, reified C, reified D, reified R> Configuration.def(
    s1: Serializer<A> = s(),
    s2: Serializer<B> = s(),
    s3: Serializer<C> = s(),
    s4: Serializer<D> = s(),
    rs: Serializer<R> = s(),
    accessName: String? = null
) = def<suspend CoroutineScope.(A, B, C, D) -> R>(accessName) { name ->
    Declaration4(name, serviceId, s1, s2, s3, s4, rs)
}

inline fun <reified A, reified B, reified C, reified D, reified E, reified R> Configuration.def(
    s1: Serializer<A> = s(),
    s2: Serializer<B> = s(),
    s3: Serializer<C> = s(),
    s4: Serializer<D> = s(),
    s5: Serializer<E> = s(),
    rs: Serializer<R> = s(),
    accessName: String? = null
) = def<suspend CoroutineScope.(A, B, C, D, E) -> R>(accessName) { name ->
    Declaration5(name, serviceId, s1, s2, s3, s4, s5, rs)
}