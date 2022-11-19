import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.*;

public class MandelbrotSimulator {

    private final int height;
    private final int width;
    private final double zoom;
    private final int maxIter;
    private final int threads;
    private final int tasks;
    private final BufferedImage bufferedImage;

    public MandelbrotSimulator(int height, int width, double zoom, int maxIter, int threads, int tasks) {
        this.height = height;
        this.width = width;
        this.zoom = zoom;
        this.maxIter = maxIter;
        this.threads = threads;
        this.tasks = tasks;
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void simulate() throws InterruptedException,  ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(threads);
        List<Future<Integer>> computations = new ArrayList<>();

        if (tasks == height * width) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    MandelbrotWorker mandelbrotWorker = new MandelbrotWorker(x, x, y, y, maxIter, height, width, zoom, bufferedImage);
                    Future<Integer> computation = threadPool.submit(mandelbrotWorker);
                    computations.add(computation);
                }
            }
        } else {
            int step = width / tasks;
            int x1 = 0;
            int x2;

            for (int i = 0; i < tasks - 1; i++) {
                x2 = x1 + step - 1;
                MandelbrotWorker mandelbrotWorker = new MandelbrotWorker(x1, x2, 0, height - 1, maxIter, height, width, zoom, bufferedImage);
                Future<Integer> computation = threadPool.submit(mandelbrotWorker);
                computations.add(computation);
                x1 += step;
            }
            MandelbrotWorker mandelbrotWorker = new MandelbrotWorker(x1, width - 1, 0, height - 1, maxIter, height, width, zoom, bufferedImage);
            Future<Integer> computation = threadPool.submit(mandelbrotWorker);
            computations.add(computation);
        }

        for (Future<Integer> computation : computations) {
            computation.get();
        }
        threadPool.shutdown();
    }

    public void showImage()
    {
        JFrame editorFrame = new JFrame("Mandelbrot");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(this.bufferedImage);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
    }
}