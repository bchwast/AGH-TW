package ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Office {
    private final List<Thread> officeBlokes = new ArrayList<>();
    private final Printers printers;

    public void simulate() {
        officeBlokes.forEach(Thread::start);
        officeBlokes.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    };

    public Office(int officeBlokesCount, int printersCount) {
        this.printers = new Printers(printersCount);
        IntStream.range(0, officeBlokesCount).mapToObj(i -> new Thread(new OfficeBloke(this.printers, i))).forEach(officeBlokes::add);
    }

    public static void main(String[] args) {
        int officeBlokesCount = 20;
        int printersCount = 10;
        Office office = new Office(officeBlokesCount, printersCount);
        office.simulate();
    }
}

