package dev.prodist;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.net.URI;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GrpcTest
{
    ManagedChannel channel;

    Server server;

    @Test
    void test ()
    {
        final var foo = new GrpcProvider().open( URI.create("grpc://localhost:"+server.getPort()) );
        assertEquals( "bar", foo.ping("bar") );
    }

    @BeforeAll
    void prepare () throws Exception
    {
        server = ServerBuilder.forPort(0)
                .addService( new GrpcServer() )
                .build();
        server.start();
        channel = ManagedChannelBuilder.forAddress("localhost",server.getPort())
                .usePlaintext()
                .build();
    }

    @AfterAll
    void close () throws Exception
    {
        channel.shutdownNow();
        server.shutdownNow();
        server.awaitTermination(1,SECONDS);
        channel.awaitTermination(1,SECONDS);
    }
}
