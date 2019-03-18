package com.huytran.rerm.interceptor

import com.google.common.base.Strings.nullToEmpty
import io.grpc.*
import org.lognet.springboot.grpc.GRpcGlobalInterceptor
import org.springframework.core.annotation.Order

@GRpcGlobalInterceptor
@Order(50)
class SecurityInterceptor : ServerInterceptor {

    companion object {
        val USER_IDENTITY = Context.key<String>("Identity")!!
    }

    override fun <ReqT : Any?, RespT : Any?> interceptCall(p0: ServerCall<ReqT, RespT>?, p1: Metadata?, p2: ServerCallHandler<ReqT, RespT>?): ServerCall.Listener<ReqT> {
        val validatedString = nullToEmpty(p1?.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)))

        val context = Context.current().withValue(USER_IDENTITY, validatedString)
        return Contexts.interceptCall(context, p0, p1, p2)
    }
}