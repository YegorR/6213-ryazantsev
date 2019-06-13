package ru.cft.focusstart.ryazantsev;

import ru.cft.focusstart.ryazantsev.exception.StockIsFullException;

import java.util.Date;

public class Producer implements Runnable {
    private int id;
    private long period;
    private final Stock stock;

    Producer(Stock stock, int id, long period) {
        this.stock = stock;
        this.id = id;
        this.period = period;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Thread.sleep(period);
                Resource resource = new Resource();
                System.out.println(new Date() + " Ресурс #" + resource.getId() + " произведён производчиком #" + id);
                synchronized (stock) {
                    boolean isWaiting = false;
                    while (stock.isFull()) {
                        if (!isWaiting) {
                            isWaiting = true;
                            System.out.println(new Date() + " Производитель #" + id + " в режиме ожидания");
                        }
                        stock.wait();
                    }
                    if (isWaiting) {
                        System.out.println(new Date() + " Производитель #" + id + " возобновил работу");
                    }
                    stock.addResource(resource);
                    stock.notifyAll();
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(new Date() + " Производитель #" + id + " прекращает работу");
        } catch (StockIsFullException ex) {
            System.err.println(new Date() + " Произошло переполнение склада! " +
                    "Это исключение не должно было сгенерироваться...");
        }
    }
}
