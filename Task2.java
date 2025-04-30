import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) throws IOException {
        // get user input
        int initialPosition;
        int previousPosition;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter initial head position: ");
        initialPosition = scanner.nextInt();
        System.out.println();
        System.out.print("Enter previous head position: ");
        previousPosition = scanner.nextInt();
        System.out.println();
        scanner.close();

        DiskScheduler[] schedulers = new DiskScheduler[] {
                new DiskScheduler("fcfs", initialPosition, previousPosition),
                new DiskScheduler("sstf", initialPosition, previousPosition),
                new DiskScheduler("scan", initialPosition, previousPosition),
                new DiskScheduler("cscan", initialPosition, previousPosition)
        };

        // schedule using each algorithm, both using a random array and input from file
        for (DiskScheduler sch : schedulers) {

            // random array
            int[] input = new int[1000];
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                input[i] = random.nextInt(5000);
            }

            System.out.println(sch.getAlg());
            System.out.println("A. Random:");
            sch.scheduleFromArray(input); // random array used as input
            System.out.println();

            // schedule from input.txt
            System.out.println("B. From input:");
            sch.scheduleFromFile("input.txt");
            System.out.println();
        }

    }
}
