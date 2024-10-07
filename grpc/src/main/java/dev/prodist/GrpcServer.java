package dev.prodist;

import dev.prodist.foo.proto.FooGrpc;
import dev.prodist.foo.proto.PingRequest;
import dev.prodist.foo.proto.PingResponse;
import io.grpc.stub.StreamObserver;

final class GrpcServer extends FooGrpc.FooImplBase
{
    @Override
    public void ping (PingRequest request, StreamObserver<PingResponse> responseObserver)
    {
        responseObserver.onNext( PingResponse.newBuilder().setMessage(request.getMessage()).build() );
        responseObserver.onCompleted();
    }
}
