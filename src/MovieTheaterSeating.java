import java.util.Scanner;

public class MovieTheaterSeating {

    private static final int ROWS = 5;
    private static final int COLS = 8;
    private static final char AVAILABLE = 'O';
    private static final char RESERVED = 'X';

    private final char[][] seatingChart;

    public MovieTheaterSeating() {
        seatingChart = new char[ROWS][COLS];
        initializeSeats();
    }

    // Initialize all seats to Available ('O')
    private void initializeSeats() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                seatingChart[r][c] = AVAILABLE;
            }
        }
    }

    // Display the 2D seating chart with row and column indicators
    public void displaySeatingChart() {
        System.out.println("\n========== SEATING CHART ==========");
        System.out.println("               [ SCREEN ]          ");
        System.out.print("      ");
        for (int c = 1; c <= COLS; c++) {
            System.out.print("Col " + c + " ");
        }
        System.out.println();

        for (int r = 0; r < ROWS; r++) {
            System.out.printf("Row %-2d ", (r + 1));
            for (int c = 0; c < COLS; c++) {
                System.out.print("  " + seatingChart[r][c] + "   ");
            }
            System.out.println();
        }
        System.out.println("Legend: O = Available, X = Reserved");
        System.out.println("===================================\n");
    }

    // Reserve a seat
    public boolean reserveSeat(int row, int col) {
        int r = row - 1;
        int c = col - 1;

        if (!isValidSeat(r, c)) {
            System.out.println("Error: Invalid seat selection. Row must be 1-" + ROWS + " and Column must be 1-" + COLS + ".");
            return false;
        }

        if (seatingChart[r][c] == AVAILABLE) {
            seatingChart[r][c] = RESERVED;
            System.out.println("Success: Seat at Row " + row + ", Column " + col + " has been successfully reserved!");
            return true;
        } else {
            System.out.println("Notice: Seat Row " + row + ", Column " + col + " is already taken.");
            suggestAlternativeSeat(r, c);
            return false;
        }
    }

    // Cancel a reservation
    public boolean cancelReservation(int row, int col) {
        int r = row - 1;
        int c = col - 1;

        if (!isValidSeat(r, c)) {
            System.out.println("Error: Invalid seat selection. Row must be 1-" + ROWS + " and Column must be 1-" + COLS + ".");
            return false;
        }

        if (seatingChart[r][c] == RESERVED) {
            seatingChart[r][c] = AVAILABLE;
            System.out.println("Success: Reservation for Row " + row + ", Column " + col + " has been canceled.");
            return true;
        } else {
            System.out.println("Notice: Seat Row " + row + ", Column " + col + " is not currently reserved.");
            return false;
        }
    }

    // Helper: Find and suggest the nearest available seat
    private void suggestAlternativeSeat(int startRow, int startCol) {
        int bestRow = -1;
        int bestCol = -1;
        double minDistance = Double.MAX_VALUE;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (seatingChart[r][c] == AVAILABLE) {
                    double distance = Math.hypot(r - startRow, c - startCol);
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestRow = r;
                        bestCol = c;
                    }
                }
            }
        }

        if (bestRow != -1) {
            System.out.println("Suggested alternative: Row " + (bestRow + 1) + ", Column " + (bestCol + 1) + " is available nearby.");
        } else {
            System.out.println("Sorry, the theater is completely full!");
        }
    }

    // Helper: Check if indices are within 2D array bounds
    private boolean isValidSeat(int r, int c) {
        return r >= 0 && r < ROWS && c >= 0 && c < COLS;
    }

    // Interactive console runner
    public static void main(String[] args) {
        MovieTheaterSeating theater = new MovieTheaterSeating();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Movie Theater Seating System!");
        theater.displaySeatingChart();

        while (running) {
            System.out.println("--- MENU ---");
            System.out.println("1. Display Seating Chart");
            System.out.println("2. Reserve a Seat");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    theater.displaySeatingChart();
                    break;
                case 2:
                    System.out.print("Enter Row number (1-" + ROWS + "): ");
                    int rRes = scanner.nextInt();
                    System.out.print("Enter Column number (1-" + COLS + "): ");
                    int cRes = scanner.nextInt();
                    theater.reserveSeat(rRes, cRes);
                    theater.displaySeatingChart();
                    break;
                case 3:
                    System.out.print("Enter Row number (1-" + ROWS + "): ");
                    int rCanc = scanner.nextInt();
                    System.out.print("Enter Column number (1-" + COLS + "): ");
                    int cCanc = scanner.nextInt();
                    theater.cancelReservation(rCanc, cCanc);
                    theater.displaySeatingChart();
                    break;
                case 4:
                    running = false;
                    System.out.println("Thank you for using the theater booking system. Enjoy the movie!");
                    break;
                default:
                    System.out.println("Invalid option. Please select 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }
}