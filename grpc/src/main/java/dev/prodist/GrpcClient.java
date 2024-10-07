package dev.prodist;

import dev.prodist.foo.proto.FooGrpc;
import dev.prodist.foo.proto.PingRequest;

final class GrpcClient implements Foo
{
    private final FooGrpc.FooBlockingStub stub;

    GrpcClient (FooGrpc.FooBlockingStub stub)
    {
        this.stub = stub;
    }

    @Override
    public String ping (String message)
    {
        final var request = PingRequest.newBuilder()
            .setMessage(message);
        final var response = stub.ping(request.build());
        return response.getMessage();
    }
}
