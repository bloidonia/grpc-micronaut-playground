package client;

import io.grpc.ManagedChannel;
import io.grpc.examples.routeguide.RouteGuideGrpc;
import io.micronaut.context.annotation.Factory;
import io.micronaut.grpc.annotation.GrpcChannel;
import jakarta.inject.Singleton;

@Factory
public class StubFactory {

    @Singleton
    RouteGuideGrpc.RouteGuideFutureStub reactiveStub(
            @GrpcChannel("router") ManagedChannel channel
    ) {
        return RouteGuideGrpc.newFutureStub(channel);
    }

    @Singleton
    RouteGuideGrpc.RouteGuideBlockingStub blockingStub(
            @GrpcChannel("router") ManagedChannel channel
    ) {
        return RouteGuideGrpc.newBlockingStub(channel);
    }
}
