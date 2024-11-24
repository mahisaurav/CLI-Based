import java.util.List;

public class MenuItem {
    private int id;
    private String name;
    private String category;
    private double price;
    private boolean isAvailable;

    public MenuItem(int id, String name, String category, double price, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " | Category: " + category + " | Price: $" + price + " | Available: " + isAvailable;
    }
}
