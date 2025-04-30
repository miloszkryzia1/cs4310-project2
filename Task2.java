import java.io.IOException;
import java.util.Random;

public class Task2 {
    public static void main(String[] args) throws IOException {
        // get user input
        int initialPosition = Integer.parseInt(args[0]);
        int previousPosition = Integer.parseInt(args[1]);

        DiskScheduler[] schedulers = new DiskScheduler[] {
                new DiskScheduler("fcfs", initialPosition, previousPosition),
                new DiskScheduler("sstf", initialPosition, previousPosition),
                new DiskScheduler("scan", initialPosition, previousPosition),
                new DiskScheduler("cscan", initialPosition, previousPosition)
        };

        // random array
        int[] input = new int[1000];
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            input[i] = random.nextInt(5000);
        }

        for (DiskScheduler sch : schedulers) {
            System.out.println(sch.getAlg());
            System.out.println("A. Random:");
            sch.scheduleFromArray(input); // random array used as input
            System.out.println();
            System.out.println("B. From input:");
            sch.scheduleFromFile("input.txt");
            System.out.println();
        }

    }
}
