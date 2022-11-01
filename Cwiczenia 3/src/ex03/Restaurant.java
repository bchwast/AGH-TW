package ex03;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Restaurant {

    private final List<Thread> guests = new ArrayList<>();
    private final Waiter waiter;

    public void simulate() {
        guests.forEach(Thread::start);
        guests.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Restaurant(int pairsCount) {
        waiter = new Waiter(pairsCount);
        IntStream.range(0, pairsCount).mapToObj(i -> new Thread(new Guest(this.waiter, i, 1))).forEach(guests::add);
        IntStream.range(0, pairsCount).mapToObj(i -> new Thread(new Guest(this.waiter, i, 2))).forEach(guests::add);
    }

    public static void main(String[] args) {
        int pairsCount = 10;
        Restaurant restaurant = new Restaurant(pairsCount);
        restaurant.simulate();
    }
}
