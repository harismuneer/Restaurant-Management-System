import java.io.*;

public class Item {

    private String name;
    private double price;  // Price in double
    private int quantity;  // Quantity in int
    private int threshold; // Threshold in int

    // Default constructor
    public Item() {}

    // Parameterized constructor
    public Item(String name, double price, int quantity, int threshold) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    // Getter and Setter methods
    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;  // Returns price as double
    }

    public int getQuantity() {
        return quantity;  // Returns quantity as int
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {  // Accepts double
        this.price = price;
    }

    public void setQuantity(int quantity) {  // Accepts int
        this.quantity = quantity;
    }

    public static void main(String[] args) {
        // Create an instance of Item
        Item item = new Item("Apple", 0.99, 10, 2);

        // Display item details
        System.out.println("Item Name: " + item.getName());
        System.out.println("Item Price: $" + item.getPrice());
        System.out.println("Item Quantity: " + item.getQuantity());
        System.out.println("Item Threshold: " + item.getThreshold());
    }
}
