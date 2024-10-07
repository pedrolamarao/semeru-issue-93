package dev.prodist;

import java.lang.ref.Cleaner;
import java.net.URI;

public interface FooProvider 
{
    Cleaner cleaner = Cleaner.create();

    Foo open (URI locator);
}
