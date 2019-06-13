package ru.cft.focusstart.ryazantsev;

public class MultiProduction {
    private final static int producerCount = 10;
    private final static int consumerCount = 20;
    private final static int stockSize = 8;
    private final static long producerPeriod = 6000;
    private final static long consumerPeriod = 4000;

    public static void main(String[] args) {
        Stock stock = new Stock(stockSize);
        for (int i = 0; i < producerCount; ++i) {
            new Thread(new Producer(stock, i, producerPeriod)).start();
        }
        for (int i = 0; i < consumerCount; ++i) {
            new Thread(new Consumer(stock, i, consumerPeriod)).start();
        }
    }
}
