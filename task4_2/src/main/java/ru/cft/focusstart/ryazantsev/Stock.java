package ru.cft.focusstart.ryazantsev;

import ru.cft.focusstart.ryazantsev.exception.StockIsEmptyException;
import ru.cft.focusstart.ryazantsev.exception.StockIsFullException;
import ru.cft.focusstart.ryazantsev.resource.Resource;

import java.util.*;

public class Stock {
    private int size;
    private Queue<Resource> resources = new ArrayDeque<>();

    Stock(int size) {
        this.size = size;
    }

    boolean isEmpty() {
        return resources.isEmpty();
    }

    boolean isFull() {
        return resources.size() == size;
    }

    void addResource(Resource resource) throws StockIsFullException {
        if (resources.size() == size) {
            throw new StockIsFullException();
        }
        resources.offer(resource);
    }

    Resource takeResource() throws StockIsEmptyException {
        if (resources.isEmpty()) {
            throw new StockIsEmptyException();
        }
        return resources.poll();
    }
}
