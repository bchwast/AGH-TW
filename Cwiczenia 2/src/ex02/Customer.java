package ex02;

import java.util.Random;

public class Customer implements Runnable {
    private final Random random = new Random();
    private final Shop shop;
    private final int number;

    public Customer(Shop shop, int number) {
        this.shop = shop;
        this.number = number;
    }

    @Override
    public void run() {
        try {
            shop.comeIn(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000, random.nextInt(4000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shop.leave(number);
    }
}
