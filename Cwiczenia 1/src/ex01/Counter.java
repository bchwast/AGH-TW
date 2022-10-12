package ex01;

public class Counter {
    protected int counter = 0;

    public Counter() {};

    public void increment() {
        counter++;
    }

    public void decrement() {
        counter--;
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread incrementator = new Thread(new Incrementator(counter));
        Thread decrementator = new Thread(new Decrementator(counter));

        incrementator.start();
        decrementator.start();

        incrementator.join();
        decrementator.join();

        System.out.println(counter.getCounter());
    }
}