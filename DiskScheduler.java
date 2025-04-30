import java.util.ArrayList;
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

    public String getAlg() {
        return this.alg;
    }

    public void scheduleFromFile(String filepath) throws IOException {
        // read input
        int[] input;
        Scanner fileScanner = new Scanner(new File(filepath));
        ArrayList<Integer> cylinderList = new ArrayList<>();
        while (fileScanner.hasNextInt()) {
            cylinderList.add(fileScanner.nextInt());
        }
        fileScanner.close();
        input = new int[cylinderList.size()];
        int i = 0;
        while (i < input.length) {
            input[i] = cylinderList.get(i);
            i++;
        }

        schedule(input);

        System.out.println("Head movement count: " + headMovementCount);
        System.out.println("Direction change count: " + changeDirectionCount);
    }

    public void scheduleFromArray(int[] input) {
        schedule(input);

        System.out.println("Head movement count: " + headMovementCount);
        System.out.println("Direction change count: " + changeDirectionCount);
    }

    private int[] schedule(int[] input) {
        switch (alg) {
            case "fcfs":
                return fcfs(input);
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

    private int[] fcfs(int[] requests) {
        int direction = previousPosition <= initialPosition ? 1 : -1;
        int previous = initialPosition;
        for (int req : requests) {
            if (previous != req) {
                headMovementCount++;
            }
            if (((req > previous) && direction == -1) || ((req < previous) && direction == 1)) {
                direction *= -1;
                changeDirectionCount++;
            }
            previous = req;
        }
        return requests;
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
            }

            // add to results and continue
            previous = next;
            currentPosition = next;
            results[nextResultIndex++] = next;
        }

        return results;
    }

    private int[] scan(int[] requests) {
        ArrayList<Integer> requestsList = new ArrayList<>();
        for (Integer req : requests) {
            requestsList.add(req);
        }
        requestsList.sort(null);
        Integer[] requestsNew = new Integer[requestsList.size()]; // sorted array of requests
        requestsList.toArray(requestsNew);

        int[] results = new int[requests.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = Integer.MIN_VALUE;
        }
        int nextResultIndex = 0;
        int direction = previousPosition <= initialPosition ? 1 : -1;
        int previous = initialPosition;

        final int MAX_REQUEST_INDEX = requestsNew.length - 1;
        // find starting request index
        int i = 0;
        if (initialPosition > requestsNew[requestsNew.length - 1]) {
            i = requestsNew.length - 1;
            if (direction == 1) {
                direction *= -1;
                changeDirectionCount++;
            }
        } else if (initialPosition < requestsNew[0]) {
            i = 0;
            if (direction == -1) {
                direction *= -1;
                changeDirectionCount++;
            }
        } else {
            while (requestsNew[i] < initialPosition) {
                i++;
            }
            if (direction == -1) {
                i--;
            }
        }

        // check if direction change needed before first request
        if (((requestsNew[i] > initialPosition) && direction == -1)
                || ((requestsNew[i] < initialPosition) && direction == 1)) {

            changeDirectionCount++;
        }

        while (contains(results, Integer.MIN_VALUE)) {
            if (direction == 1) {
                if (previous != requestsNew[i]) {
                    headMovementCount++;
                }
                results[nextResultIndex++] = requestsNew[i];
                previous = requestsNew[i];
                requestsNew[i] = Integer.MAX_VALUE;
                i++;
                if (i > MAX_REQUEST_INDEX) {
                    // change direction and move to the next request
                    if (!contains(results, Integer.MIN_VALUE)) {
                        break;
                    }
                    direction *= -1;
                    changeDirectionCount++;
                    i = 0;
                    while (requestsNew[i + 1] != Integer.MAX_VALUE) {
                        i++;
                    }
                }
            }
            if (direction == -1) {
                if (previous != requestsNew[i]) {
                    headMovementCount++;
                }
                results[nextResultIndex++] = requestsNew[i];
                previous = requestsNew[i];
                requestsNew[i] = Integer.MAX_VALUE;
                i--;
                if (i < 0) {
                    // change direction and move to next request
                    if (!contains(results, Integer.MIN_VALUE)) {
                        break;
                    }
                    direction *= -1;
                    changeDirectionCount++;
                    i = 0;
                    while (requestsNew[i] == Integer.MAX_VALUE) {
                        i++;
                    }
                }
            }
        }

        return results;

    }

    private int[] cscan(int[] requests) {
        ArrayList<Integer> requestsList = new ArrayList<>();
        for (Integer req : requests) {
            requestsList.add(req);
        }
        requestsList.sort(null);
        Integer[] requestsNew = new Integer[requestsList.size()]; // sorted array of requests
        requestsList.toArray(requestsNew);

        int[] results = new int[requests.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = Integer.MIN_VALUE;
        }
        int nextResultIndex = 0;
        int direction = previousPosition <= initialPosition ? 1 : -1;
        int previous = initialPosition;

        final int MAX_REQUEST_INDEX = requestsNew.length - 1;
        // find starting request index
        int i = 0;
        if ((initialPosition > requestsNew[requestsNew.length - 1]) || (initialPosition < requestsNew[0])) {
            i = direction == 1 ? 0 : requestsNew.length - 1;
        } else {
            while (requestsNew[i] < initialPosition) {
                i++;
            }
            if (direction == -1) {
                i--;
            }
        }

        while (contains(results, Integer.MIN_VALUE)) {
            if (direction == 1) {
                if (previous != requestsNew[i]) {
                    headMovementCount++;
                }
                results[nextResultIndex++] = requestsNew[i];
                previous = requestsNew[i];
                requestsNew[i] = Integer.MAX_VALUE;
                i++;
                if (i > MAX_REQUEST_INDEX) {
                    // switch to lowest index
                    if (!contains(results, Integer.MIN_VALUE)) {
                        break;
                    }
                    i = 0;
                    while (requestsNew[i + 1] != Integer.MAX_VALUE) {
                        i++;
                    }
                }
            }
            if (direction == -1) {
                if (previous != requestsNew[i]) {
                    headMovementCount++;
                }
                results[nextResultIndex++] = requestsNew[i];
                previous = requestsNew[i];
                requestsNew[i] = Integer.MAX_VALUE;
                i--;
                if (i < 0) {
                    // switch to highest index
                    if (!contains(results, Integer.MIN_VALUE)) {
                        break;
                    }
                    i = requestsNew.length - 1;
                    while (requestsNew[i] == Integer.MAX_VALUE) {
                        i--;
                    }
                }
            }
        }

        return results;
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
