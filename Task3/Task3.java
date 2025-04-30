package Task3;

import java.util.Scanner;

public class Task3 {

    static void calculateBlockNumber(int numSectors, int numTracks, int numCylinders, int blockNumber) {
        int bn = blockNumber;
        int sectorNum = blockNumber % numSectors;
        blockNumber /= numSectors;
        int trackNum = blockNumber % numTracks;
        blockNumber /= numSectors;
        int cylinderNum = blockNumber % numCylinders;

        System.out.println(String.format("The logical block number %d is located at < %d , %d , %d >", bn, cylinderNum,
                trackNum, sectorNum));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a logical block number: ");
        int bn = scanner.nextInt();
        System.out.println("Enter HD number of cylinders: ");
        int nc = scanner.nextInt();
        System.out.println("Enter HD number of tracks: ");
        int nt = scanner.nextInt();
        System.out.println("Enter HD number of sectors: ");
        int ns = scanner.nextInt();
        scanner.close();

        calculateBlockNumber(ns, nt, nc, bn);
    }

}
