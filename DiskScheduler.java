import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class DiskScheduler {

    private int initialPosition;
    private int previousPosition;
    private int headMovementCount = 0;
    private int changeDirectionCount = 0;
    private String alg;

    public DiskScheduler(String alg, int initPos, int prevPos) {
        this.alg = alg;
        this.initialPosition = initPos;
        this.previousPosition = prevPos;
    }

    public void scheduleFromInput(String filepath, int n) throws IOException {
        // read input
        int[] input = new int[n];
        Scanner fileScanner = new Scanner(new File(filepath));
        int i = 0;
        while (fileScanner.hasNextInt()) {
            input[i++] = fileScanner.nextInt();
        }
        fileScanner.close();

        int[] results = schedule(input);
        System.out.print("Result: ");
        for (int res : results) {
            System.out.print(res + ", ");
        }
        System.out.println();
        System.out.println("Head movement count: " + headMovementCount);
        System.out.println("Direction change count: " + changeDirectionCount);
    }

    public void scheduleRandom() {
        int[] input = new int[1000];

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            input[i] = random.nextInt(5000);
        }

        System.out.print("Requests: ");
        for (int in : input) {
            System.out.print(in + ", ");
        }
        System.out.println();
        System.out.println();

        int[] results = schedule(input);
        System.out.print("Result: ");
        for (int res : results) {
            System.out.print(res + ", ");
        }
        System.out.println();
        System.out.println("Head movement count: " + headMovementCount);
        System.out.println("Direction change count: " + changeDirectionCount);
    }

    private int[] schedule(int[] input) {
        switch (alg) {
            case "fcfs":
                return input;
            case "sstf":
                return sstf(input);
            case "scan":
                return scan(input);
            case "cscan":
                return cscan(input);
            default:
                return input;
        }
    }

    private int[] sstf(int[] requests) {
        int[] results = new int[requests.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = Integer.MIN_VALUE;
        }

        int next = initialPosition;
        int nextDifference = Integer.MAX_VALUE;
        int nextResultIndex = 0;
        int currentPosition = initialPosition;
        int lastFoundIndex = 0;
        int previous = initialPosition;
        int direction = previousPosition <= initialPosition ? 1 : -1;

        while (contains(results, Integer.MIN_VALUE)) {
            // find next closest request
            for (int i = 0; i < requests.length; i++) {
                if (Math.abs(requests[i] - currentPosition) < nextDifference) {
                    next = requests[i];
                    nextDifference = Math.abs(requests[i] - currentPosition);
                    lastFoundIndex = i;
                }
            }

            // remove processed request from requests
            requests[lastFoundIndex] = Integer.MAX_VALUE;
            // reset nextDifference
            nextDifference = Integer.MAX_VALUE;

            // increment counters
            if (next != previous) {
                headMovementCount++;
            }

            if (((next > previous) && direction == -1) || ((next < previous) && direction == 1)) {
                direction *= -1;
                changeDirectionCount++;
                System.out.println("Direction change between " + previous + " and " + next);
            }

            // add to results and continue
            previous = next;
            currentPosition = next;
            results[nextResultIndex++] = next;
        }

        return results;
    }

    private int[] scan(int[] requests) {
        return new int[] {};
    }

    private int[] cscan(int[] requests) {
        return new int[] {};
    }

    // helper
    private static boolean contains(int[] array, int value) {
        for (int v : array) {
            if (v == value) {
                return true;
            }
        }
        return false;
    }
}
