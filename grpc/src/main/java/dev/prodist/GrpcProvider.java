package dev.prodist;

import dev.prodist.foo.proto.FooGrpc;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class GrpcProvider implements FooProvider
{
    @Override
    public Foo open (URI locator)
    {
        try {
            final var scheme = Objects.requireNonNull(locator.getScheme()).split("\\+");
            if (! "grpc".equals(scheme[0])) return null;

            final String target;
            if (scheme.length > 1)
                target = new URI(scheme[1],locator.getAuthority(),locator.getPath(),locator.getQuery(),locator.getFragment()).toString();
            else
                target = locator.getAuthority();

            final var builder = Grpc.newChannelBuilder(target,InsecureChannelCredentials.create());

            final var channel = builder.build();
            final var service = new GrpcClient( FooGrpc.newBlockingStub(channel) );
            cleaner.register(service,channel::shutdownNow);

            return service;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
