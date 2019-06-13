package ru.cft.focusstart.ryazantsev;

import ru.cft.focusstart.ryazantsev.exception.StockIsEmptyException;
import ru.cft.focusstart.ryazantsev.exception.StockIsFullException;
import ru.cft.focusstart.ryazantsev.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private int size;
    private List<Resource> resources = new ArrayList<>();

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
        resources.add(resource);
    }

    Resource takeResource() throws StockIsEmptyException {
        if (resources.isEmpty()) {
            throw new StockIsEmptyException();
        }
        return resources.remove(resources.size() - 1);
    }
}
