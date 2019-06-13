package ru.cft.focusstart.ryazantsev.resource;

import java.util.concurrent.atomic.AtomicInteger;

public class ResourceCreator {
    static private AtomicInteger idCount = new AtomicInteger(0);

    static public Resource createResource() {
        return new Resource(idCount.getAndIncrement());
    }
}
