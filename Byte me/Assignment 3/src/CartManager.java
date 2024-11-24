import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static final String CART_FILE = "cart.txt";  // File to store cart data

    // Assuming we have a static Menu class that holds all menu items
    private static Menu menu = new Menu(); // Static reference to Menu, or you can pass it if needed

    // Method to read the cart from the file
    public List<CartItem> readCartFromFile() {
        List<CartItem> cart = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the format: "ItemName, Quantity"
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String itemName = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    // Find the MenuItem by the itemName
                    MenuItem item = findMenuItemByName(itemName);
                    if (item != null) {
                        cart.add(new CartItem(item, quantity));  // Add CartItem to cart
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No previous cart data found or error reading cart file.");
        }
        return cart;
    }

    // Method to write the updated cart to the file
    public void writeCartToFile(List<CartItem> cart) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE))) {
            for (CartItem item : cart) {
                // Write each CartItem's item name and quantity to the file
                writer.write(item.getItem().getName() + "," + item.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing cart data to file.");
        }
    }

    // Method to find a MenuItem by its name
    private MenuItem findMenuItemByName(String itemName) {
        // Assuming Menu has a method getMenuItems() that returns a list of all MenuItems
        for (MenuItem item : menu.getMenuItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;  // If the item is not found
    }

}
