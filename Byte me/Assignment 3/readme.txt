Canteen Management System

Project Overview
The Canteen Management System is a Java application designed to streamline canteen operations, including managing menu items, processing customer orders, generating sales reports, and handling customer reviews. This system is built using Object-Oriented Programming (OOP) principles to ensure efficient code organization, modularity, and maintainability.

Features
1. User Roles
Admin: Has access to manage menu items, view and process orders, and generate sales reports.
Customer: Can view the menu, place orders, and submit reviews for items.

2. Menu Management (Admin Only)
Add new items to the menu.
Modify item details, such as price and availability.
Remove items from the menu.

3. Order Management
Customers can add items to their cart and place orders.
Admins can view pending orders, update their status, and handle special requests or refunds.

4. Sales Reporting
Generate daily reports on total sales and identify the most popular items.

5. Customer Reviews
Customers can leave reviews for items they ordered, helping future customers make informed choices.
Ho

1. User
An abstract base class representing a generic user with basic properties like username and password.

2. Admin (extends User)
Adds functionalities specific to administrative tasks, such as menu management, order handling, and report generation.
Includes methods like addMenuItem(), modifyMenuItem(), removeMenuItem(), viewOrders(), and generateDailySalesReport().

3. Customer (extends User)
Represents a customer in the system, with methods to view the menu, add items to the cart, place orders, and leave reviews.

4. MenuItem
Represents a single item in the menu with attributes like id, name, category, price, and availability.
Methods include getters and setters for encapsulated fields.

5. Menu
Manages a list of MenuItem objects.
Provides methods to add, remove, and retrieve menu items for both customers and admins.

6. Order
Represents an order placed by a customer, containing the items ordered, order status, and any special requests.
Includes enums for different statuses (e.g., PENDING, DELIVERED, CANCELLED) and methods to update order details.

7. CartItem
Represents a single item in a customer’s cart, including the MenuItem and quantity.
Methods to get and set the item’s quantity are provided.


Object-Oriented Programming (OOP) Principles Used

Encapsulation
Classes like MenuItem, Order, and CartItem use private fields and expose necessary operations through public methods, hiding the internal state.

Inheritance
Admin and Customer classes inherit from the base User class, reducing code duplication and allowing common user attributes to be managed in a single location.

Polymorphism
The system leverages polymorphism primarily through method overriding (e.g., the toString() method in classes like MenuItem, CartItem, and Review), allowing each class to have a unique string representation.

Abstraction
The design abstracts complex operations like menu management and order processing into dedicated classes (Menu, Order), which makes the system easier to understand and maintain.
Usage

Admin Interface: After logging in as an Admin, you can manage menu items, view and update orders, handle refunds, and generate daily sales reports.
Customer Interface: Customers can view the menu, add items to the cart, place orders, and review items they’ve ordered.

Example Scenarios
Adding a Menu Item: Admins can use the option to add new items by providing details like item ID, name, category, price, and availability.
Placing an Order: Customers add items to their cart and confirm the order, after which it appears in the Admin’s list of pending orders.
Generating Sales Report: Admins can generate a daily report that summarizes total sales and highlights popular items.

Future Enhancements
Payment Processing: Integrate a payment system to handle customer transactions.

Order History: Allow customers to view their past orders.

Inventory Management: Track inventory for each menu item and prevent orders when stock is low.