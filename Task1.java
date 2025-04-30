import java.util.Scanner;

class Task1 {

    private static int address;
    private static int pageSize;

    public static void readInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter page size: ");
        pageSize = scanner.nextInt();
        System.out.println();

        System.out.print("Enter a virtual address: ");
        address = scanner.nextInt();
        System.out.println();

        scanner.close();
    }

    public static void calculatePW() {
        int pageSizeBytes = pageSize * 1024; // convert page size to bytes

        int p = address / pageSizeBytes; // get page number
        int w = address % pageSizeBytes; // get offset

        System.out.println("The address " + address + " contains: page number = " + p + " offset = " + w);

    }

}