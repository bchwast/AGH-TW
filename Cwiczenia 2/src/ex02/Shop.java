package ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Shop {
    private final CountingSemaphore semaphore;
    private final List<Thread> customers = new ArrayList<>();

    public Shop(int baskets, int customers) {
        this.semaphore = new CountingSemaphore(baskets);
        IntStream.range(0, customers).mapToObj(i -> new Thread(new Customer(this, i))).forEach(this.customers::add);
    }

    public void comeIn(int number) throws InterruptedException {
        semaphore.setDown(number);
    }

    public void leave(int number) {
        semaphore.setUp(number);
    }

    public void simulate() {
        customers.forEach(Thread::start);
        customers.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        int baskets = 5;
        int customers = 30;
        Shop shop = new Shop(baskets, customers);
        shop.simulate();
    }
}
