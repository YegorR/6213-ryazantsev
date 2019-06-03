package ru.cft.focusstart.ryazantsev;

public class MultiProduction {
    private final static int producerCount = 10;
    private final static int consumerCount = 20;

    public static void main(String[] args) {
        Stock stock = new Stock();
        for (int i = 0; i < producerCount; ++i) {
            new Thread(new Producer(stock)).start();
        }
        for (int i = 0; i < consumerCount; ++i) {
            new Thread(new Consumer(stock)).start();
        }
    }
}
