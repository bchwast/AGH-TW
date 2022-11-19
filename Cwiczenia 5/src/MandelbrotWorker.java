import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class MandelbrotWorker implements Callable<Integer> {

    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    private final int maxIter;
    private final int height;
    private final int width;
    private final double zoom;
    private final BufferedImage bufferedImage;

    public MandelbrotWorker(int x1, int x2, int y1, int y2, int maxIter, int height, int width, double zoom, BufferedImage bufferedImage) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.maxIter = maxIter;
        this.height = height;
        this.width = width;
        this.zoom = zoom;
        this.bufferedImage = bufferedImage;
    }

    @Override
    public Integer call() {
        int halfHeight = height / 2;
        int halfWidth = width / 2;
        double zx;
        double zy;
        double cX;
        double cY;
        double tmp;
        int iter;

        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                zx = 0;
                zy = 0;
                cX = (x - halfWidth) / zoom;
                cY = (y - halfHeight) / zoom;
                iter = maxIter;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                bufferedImage.setRGB(x, y, iter | (iter << 8));
            }
        }
        return 0;
    }
}
