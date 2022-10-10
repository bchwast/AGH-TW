import ex01.Counter;
import ex01.Decrementator;
import ex01.Incrementator;
import ex01.SynchronizedCounter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Counter counter = new Counter();
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread incrementator = new Thread(new Incrementator(counter));
        Thread decrementator = new Thread(new Decrementator(counter));

        incrementator.start();
        decrementator.start();

        incrementator.join();
        decrementator.join();

        System.out.println(counter.getCounter());
    }
}