package ex03;

import java.util.Random;

public class Guest implements Runnable {

    private final Random random = new Random();
    private final int pair;
    private final int number;
    private final Waiter waiter;

    public Guest(Waiter waiter, int pair, int number) {
        this.waiter = waiter;
        this.pair = pair;
        this.number = number;
    }

    private void doSomething() throws InterruptedException {
        Thread.sleep(random.nextInt(3000));
    }

    private void eat() throws InterruptedException {
        System.out.println("Guest " + number + " from pair " + pair + " started eating");
        Thread.sleep(random.nextInt(5000));
        System.out.println("Guest " + number + " from pair " + pair + " finished eating");
    }

    @Override
    public void run() {
        while (true) {
            try {
                doSomething();
                waiter.reserve(pair, number);
                eat();
                waiter.free(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
