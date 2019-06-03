package ru.cft.focusstart.ryazantsev;

import java.util.concurrent.atomic.AtomicInteger;

public class Resource {
    static private AtomicInteger idCount = new AtomicInteger(0);

    private int id;

    Resource() {
        id = idCount.getAndIncrement();
    }

    int getId() {
        return id;
    }
}
