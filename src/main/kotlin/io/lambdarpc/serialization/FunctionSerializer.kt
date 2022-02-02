package io.lambdarpc.serialization

import io.lambdarpc.exceptions.UnknownMessageType
import io.lambdarpc.functions.backend.*
import io.lambdarpc.functions.frontend.*
import io.lambdarpc.service.Connector
import io.lambdarpc.transport.grpc.channelFunction
import io.lambdarpc.transport.grpc.function
import io.lambdarpc.utils.AccessName
import io.lambdarpc.utils.Endpoint
import io.lambdarpc.utils.an
import io.lambdarpc.utils.grpc.encode
import io.lambdarpc.utils.toSid
import io.lambdarpc.transport.grpc.Function as TFunction

interface FunctionSerializer<F> : Serializer<F> {
    /**
     * Encoding function is saving it to the registry and
     * providing its name for the remote caller.
     *
     * @param registry [FunctionRegistry] to save the function as a backend function.
     */
    fun encode(f: F, registry: FunctionRegistry): TFunction

    /**
     * Creates a callable proxy object that serializes the data,
     * sends it to the backend side and receives the result.
     */
    fun decode(f: TFunction, functionRegistry: FunctionRegistry, channelRegistry: ChannelRegistry): F
}

abstract class AbstractFunctionSerializer<F> : FunctionSerializer<F> {
    override fun encode(f: F, registry: FunctionRegistry): TFunction =
        function {
            if (f is ClientFunction) {
                clientFunction = f.encode()
            } else {
                val name = registry.register(f.toBackendFunction())
                channelFunction = channelFunction { accessName = name.n }
            }
        }

    protected abstract fun F.toBackendFunction(): BackendFunction

    override fun decode(f: TFunction, functionRegistry: FunctionRegistry, channelRegistry: ChannelRegistry): F = f.run {
        when {
            hasChannelFunction() -> {
                val name = channelFunction.accessName.an
                channelFunction(name, channelRegistry.createExecuteChannel(), functionRegistry and channelRegistry)
            }
            hasClientFunction() -> {
                val name = clientFunction.accessName.an
                val id = clientFunction.serviceId.toSid()
                val endpoint = Endpoint(clientFunction.serviceURL)
                clientFunction(name, Connector(id, endpoint))
            }
            else -> throw UnknownMessageType("function")
        }
    }

    protected abstract fun channelFunction(
        name: AccessName,
        executionChannel: RequestExecutionChannel,
        serializationScope: SerializationScope,
    ): F

    protected abstract fun clientFunction(name: AccessName, connector: Connector): F
}

class FunctionSerializer0<R>(
    private val rs: Serializer<R>,
) : AbstractFunctionSerializer<suspend () -> R>() {
    override fun (suspend () -> R).toBackendFunction() = BackendFunction0(this, rs)

    override fun channelFunction(
        name: AccessName,
        executionChannel: RequestExecutionChannel,
        serializationScope: SerializationScope,
    ): suspend () -> R =
        ChannelFunction0(name, executionChannel, serializationScope, rs)

    override fun clientFunction(name: AccessName, connector: Connector): suspend () -> R =
        ClientFunction0(name, connector, rs)
}

class FunctionSerializer1<A, R>(
    private val s1: Serializer<A>,
    private val rs: Serializer<R>,
) : AbstractFunctionSerializer<suspend (A) -> R>() {
    override fun (suspend (A) -> R).toBackendFunction() = BackendFunction1(this, s1, rs)

    override fun channelFunction(
        name: AccessName,
        executionChannel: RequestExecutionChannel,
        serializationScope: SerializationScope,
    ): suspend (A) -> R =
        ChannelFunction1(name, executionChannel, serializationScope, s1, rs)

    override fun clientFunction(name: AccessName, connector: Connector): suspend (A) -> R =
        ClientFunction1(name, connector, s1, rs)
}

class FunctionSerializer2<A, B, R>(
    private val s1: Serializer<A>,
    private val s2: Serializer<B>,
    private val rs: Serializer<R>,
) : AbstractFunctionSerializer<suspend (A, B) -> R>() {
    override fun (suspend (A, B) -> R).toBackendFunction() = BackendFunction2(this, s1, s2, rs)

    override fun channelFunction(
        name: AccessName,
        executionChannel: RequestExecutionChannel,
        serializationScope: SerializationScope,
    ): suspend (A, B) -> R =
        ChannelFunction2(name, executionChannel, serializationScope, s1, s2, rs)

    override fun clientFunction(name: AccessName, connector: Connector): suspend (A, B) -> R =
        ClientFunction2(name, connector, s1, s2, rs)
}

class FunctionSerializer3<A, B, C, R>(
    private val s1: Serializer<A>,
    private val s2: Serializer<B>,
    private val s3: Serializer<C>,
    private val rs: Serializer<R>,
) : AbstractFunctionSerializer<suspend (A, B, C) -> R>() {
    override fun (suspend (A, B, C) -> R).toBackendFunction() = BackendFunction3(this, s1, s2, s3, rs)

    override fun channelFunction(
        name: AccessName,
        executionChannel: RequestExecutionChannel,
        serializationScope: SerializationScope,
    ): suspend (A, B, C) -> R =
        ChannelFunction3(name, executionChannel, serializationScope, s1, s2, s3, rs)

    override fun clientFunction(name: AccessName, connector: Connector): suspend (A, B, C) -> R =
        ClientFunction3(name, connector, s1, s2, s3, rs)
}

class FunctionSerializer4<A, B, C, D, R>(
    private val s1: Serializer<A>,
    private val s2: Serializer<B>,
    private val s3: Serializer<C>,
    private val s4: Serializer<D>,
    private val rs: Serializer<R>,
) : AbstractFunctionSerializer<suspend (A, B, C, D) -> R>() {
    override fun (suspend (A, B, C, D) -> R).toBackendFunction() = BackendFunction4(this, s1, s2, s3, s4, rs)

    override fun channelFunction(
        name: AccessName,
        executionChannel: RequestExecutionChannel,
        serializationScope: SerializationScope,
    ): suspend (A, B, C, D) -> R =
        ChannelFunction4(name, executionChannel, serializationScope, s1, s2, s3, s4, rs)

    override fun clientFunction(name: AccessName, connector: Connector): suspend (A, B, C, D) -> R =
        ClientFunction4(name, connector, s1, s2, s3, s4, rs)
}

class FunctionSerializer5<A, B, C, D, E, R>(
    private val s1: Serializer<A>,
    private val s2: Serializer<B>,
    private val s3: Serializer<C>,
    private val s4: Serializer<D>,
    private val s5: Serializer<E>,
    private val rs: Serializer<R>,
) : AbstractFunctionSerializer<suspend (A, B, C, D, E) -> R>() {
    override fun (suspend (A, B, C, D, E) -> R).toBackendFunction() = BackendFunction5(this, s1, s2, s3, s4, s5, rs)

    override fun channelFunction(
        name: AccessName,
        executionChannel: RequestExecutionChannel,
        serializationScope: SerializationScope,
    ): suspend (A, B, C, D, E) -> R =
        ChannelFunction5(name, executionChannel, serializationScope, s1, s2, s3, s4, s5, rs)

    override fun clientFunction(name: AccessName, connector: Connector): suspend (A, B, C, D, E) -> R =
        ClientFunction5(name, connector, s1, s2, s3, s4, s5, rs)
}
