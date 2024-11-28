import java.util.Date;
import java.util.Scanner;

public class Inventory {
    public String name;
    public Date expiryDate;
    public int price;
    public int quantity;

    // Constructor
    public Inventory(String name, Date expiryDate, int price, int quantity) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.price = price;
        this.quantity = quantity;
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get inventory item details from user
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        System.out.print("Enter expiry date (yyyy-mm-dd): ");
        String dateInput = scanner.nextLine();
        Date expiryDate = java.sql.Date.valueOf(dateInput); // Convert string to Date

        System.out.print("Enter price: ");
        int price = scanner.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        // Create an instance of Inventory
        Inventory item = new Inventory(name, expiryDate, price, quantity);
        
        scanner.close(); // Close the scanner
    }
}
