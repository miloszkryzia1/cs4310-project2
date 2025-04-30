import java.io.IOException;

public class Task2 {
    public static void main(String[] args) throws IOException {
        DiskScheduler sstfScheduler = new DiskScheduler("sstf", 1000, 1001);
        sstfScheduler.scheduleRandom();
    }
}
