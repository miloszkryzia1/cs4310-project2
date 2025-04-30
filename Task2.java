import java.io.IOException;

public class Task2 {
    public static void main(String[] args) throws IOException {
        DiskScheduler scanScheduler = new DiskScheduler("scan", 500, 600);
        scanScheduler.scheduleFromInput("input.txt", 5);
        // scanScheduler.scheduleRandom(50);
    }
}
