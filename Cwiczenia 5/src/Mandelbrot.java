import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Mandelbrot {

    private final int cores = Runtime.getRuntime().availableProcessors();
    private final List<Integer> testThreads = new ArrayList<>(List.of(1, cores, 2 * cores));
    private final List<Integer> maxIterations;
    private final int runs;
    private final int height;
    private final int width;
    private final double zoom;
    private final List<DataHolder> results = new ArrayList<>();

    public Mandelbrot(int height, int width, double zoom, int runs, List<Integer> maxIterations) {
        this.height = height;
        this.width = width;
        this.zoom = zoom;
        this.runs = runs;
        this.maxIterations = maxIterations;
    }

    private void run() throws ExecutionException, InterruptedException, FileNotFoundException {
        for (int maxIter: maxIterations) {
            for (int threads: testThreads) {
                List<Integer> testTasks = new ArrayList<>(List.of(threads, 10 * threads, height * width));
                for (int tasks: testTasks) {
                    List<Long> times = new ArrayList<>();
                    for (int i = 0; i < runs; i++) {
                        MandelbrotSimulator mandelbrotSimulator = new MandelbrotSimulator(height, width, zoom, maxIter, threads, tasks);
                        long startTime = System.nanoTime();
                        mandelbrotSimulator.simulate();
                        times.add(System.nanoTime() - startTime);
                    }

                    long avgTime = (long) (times.stream().mapToLong(time -> time).average()).orElse(0);
                    long stDev = (long) Math.sqrt(times.stream().map(time -> time - avgTime).map(time -> time * time)
                            .mapToLong(time -> time).average().orElse(0));

                    DataHolder result = new DataHolder(maxIter, threads, tasks, avgTime, stDev);
                    results.add(result);
                }
            }
        }

        String output = formatOutput();
        System.out.println(output);

        PrintStream out = new PrintStream(new FileOutputStream("results.txt"));
        out.print(output);
    }

    private void showExample() throws ExecutionException, InterruptedException {
        MandelbrotSimulator mandelbrotSimulator = new MandelbrotSimulator(height, width, zoom, 500, 3, 3);
        mandelbrotSimulator.simulate();
        mandelbrotSimulator.showImage();
    }

    private String formatOutput() {
        StringBuilder resultString = new StringBuilder();
        Formatter formatter = new Formatter(resultString);
        formatter.format("        maxIter|        threads|          tasks|        avgTime|          stDev\n");

        for (DataHolder result: results)
        {
            formatter.format("%15d|", result.maxIter());
            formatter.format("%15d|", result.threads());
            formatter.format("%15d|", result.tasks());
            formatter.format("%15d|", result.avgTime());
            formatter.format("%15d\n", result.stDev());
        }

        return resultString.toString();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, FileNotFoundException {
        List<Integer> maxIterations = new ArrayList<>(List.of(100, 500, 1000, 5000, 10000));
        Mandelbrot mandelbrot = new Mandelbrot(900, 1600, 450, 10, maxIterations);
        mandelbrot.run();
//        mandelbrot.showExample();
    }
}