package ru.cft.focusstart.ryazantsev;

import ru.cft.focusstart.ryazantsev.exception.StockIsEmptyException;

import java.util.Date;

public class Consumer implements Runnable {
    static private int idCount = 0;
    static final private long PERIOD = 8000;

    private int id;
    private final Stock stock;

    Consumer(Stock stock) {
        id = idCount;
        idCount++;

        this.stock = stock;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Resource resource;
                synchronized (stock) {
                    boolean isWaiting = false;
                    while (stock.isEmpty()) {
                        if (!isWaiting) {
                            isWaiting = true;
                            System.out.println(new Date() + " Потребитель #" + id + " в режиме ожидания");
                        }
                        stock.wait();
                    }
                    if (isWaiting) {
                        System.out.println(new Date() + " Потребитель #" + id + " возобновил работу");
                    }
                    resource = stock.takeResource();

                    stock.notifyAll();
                }
                Thread.sleep(PERIOD);
                System.out.println(new Date() + " Ресурс #" + resource.getId() + " потреблён потребителем #" + id);
            }
        } catch (InterruptedException ex) {
            System.out.println(new Date() + " Потребитель #" + id + " прекращает работу");
        } catch (StockIsEmptyException ex) {
            System.err.println(new Date() + " Обращение к пустому складу! " +
                    "Это исключение не должно было сгенерироваться...");
        }
    }
}
