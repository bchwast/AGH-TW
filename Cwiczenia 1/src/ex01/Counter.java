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
}