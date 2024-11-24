import java.util.ArrayList;
import java.util.List;

class Menu {
    private List<MenuItem> menuItems;

    public Menu() {
        menuItems = new ArrayList<>();
        
        menuItems.add(new MenuItem(1, "Burger", "Fast Food", 95, true));
        menuItems.add(new MenuItem(2, "Pizza", "Italian", 225, true));
        menuItems.add(new MenuItem(3, "Pasta", "Italian", 125, false));
        menuItems.add(new MenuItem(4, "Salad", "Healthy", 80, true));
        menuItems.add(new MenuItem(5, "Sushi", "Japanese", 300, true));
        menuItems.add(new MenuItem(6, "Tacos", "Mexican", 150, true));
        menuItems.add(new MenuItem(7, "Steak", "Meat", 150, false));
        menuItems.add(new MenuItem(8, "Ice Cream", "Dessert", 100, true));
        menuItems.add(new MenuItem(9, "Fries", "Fast Food", 129, true));
        menuItems.add(new MenuItem(10, "Sandwich", "Deli", 80, true));
        menuItems.add(new MenuItem(11, "Chow Mein", "Chinese", 100, true));
        menuItems.add(new MenuItem(12, "Curry", "Indian", 100, true));
        menuItems.add(new MenuItem(13, "Pancakes", "Breakfast", 125, true));
        menuItems.add(new MenuItem(14, "Donuts", "Dessert", 40, true));
        menuItems.add(new MenuItem(15, "Coffee", "Beverage", 30, true));
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }
    public void removeMenuItem(MenuItem menuItem) {
        menuItems.remove(menuItem);
    }
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}