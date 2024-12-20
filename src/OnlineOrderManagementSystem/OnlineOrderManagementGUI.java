package OnlineOrderManagementSystem;

import java.awt.Color; // Import Color for setting background
import java.util.ArrayList; // Import ArrayList for managing lists
import java.util.List; // Import List for type-safe collections
import javax.swing.*; // Import Swing components for GUI

public class OnlineOrderManagementGUI {

    private OrderManagement orderManagement; // Manage all orders, customers, and products

    // Constructor to initialize the management system and GUI
    public OnlineOrderManagementGUI() {
        orderManagement = new OrderManagement(); // Initialize the order management system
        createAndShowGUI(); // Call method to create the GUI
    }

    // Method to set up and display the GUI
    private void createAndShowGUI() {
        JFrame frame = new JFrame("Online Order Management System"); // Create the main window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application on window close
        frame.setSize(600, 500); // Set the window size

        // Create buttons for different functionalities
        JButton addProductButton = new JButton("Add Product"); // Button to add a product
        JButton addCustomerButton = new JButton("Add Customer"); // Button to add a customer
        JButton createOrderButton = new JButton("Create Order"); // Button to create an order
        JButton listProductsButton = new JButton("List Products"); // Button to list all products
        JButton listCustomerButton = new JButton("List Customers"); // Button to list all customers
        JButton listOrdersButton = new JButton("List Orders"); // Button to list all orders

        // Create a panel to hold buttons
        JPanel panel = new JPanel();
        panel.setBackground(Color.pink); // Set panel background color
        panel.add(addProductButton); // Add the "Add Product" button to the panel
        panel.add(addCustomerButton); // Add the "Add Customer" button to the panel
        panel.add(createOrderButton); // Add the "Create Order" button to the panel
        panel.add(listProductsButton); // Add the "List Products" button to the panel
        panel.add(listCustomerButton); // Add the "List Customers" button to the panel
        panel.add(listOrdersButton); // Add the "List Orders" button to the panel

        frame.getContentPane().add(panel); // Add the panel to the main frame
        frame.setVisible(true); // Make the frame visible

        // Action for adding a product
        addProductButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Product Id: "); // Get product ID
            String name = JOptionPane.showInputDialog("Enter Product Name: "); // Get product name
            String priceStr = JOptionPane.showInputDialog("Enter Product price: "); // Get product price
            String stockStr = JOptionPane.showInputDialog("Enter Product Stock: "); // Get product stock
            try {
                double price = Double.parseDouble(priceStr); // Convert price to double
                int stock = Integer.parseInt(stockStr); // Convert stock to integer
                Product product = new Product(id, name, price, stock); // Create a new product object
                orderManagement.addProduct(product); // Add the product to the management system
                JOptionPane.showMessageDialog(frame, "Product added successfully"); // Display success message
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter numbers for price and stock.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action for adding a customer
        addCustomerButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter Customer Id: "); // Get customer ID
            String name = JOptionPane.showInputDialog("Enter Customer Name: "); // Get customer name
            String phone = JOptionPane.showInputDialog("Enter Customer Phone: "); // Get customer phone
            String email = JOptionPane.showInputDialog("Enter Customer Email: "); // Get customer email
            orderManagement.addCustomer(new Customer(id, name, phone, email)); // Add the customer to the system
            JOptionPane.showMessageDialog(frame, "Customer added successfully."); // Display success message
        });

        // Action for creating an order
        createOrderButton.addActionListener(e -> {
            String customerId = JOptionPane.showInputDialog("Enter Customer Id: "); // Get customer ID
            ArrayList<Product> orderProducts = new ArrayList<>(); // List to hold products for the order
            while (true) {
                String productId = JOptionPane.showInputDialog("Enter Product Id to add to order (or type 'done' to finish)"); // Get product ID
                if (productId.equalsIgnoreCase("done")) {
                    break; // Exit the loop if user types "done"
                }
                String quantityStr = JOptionPane.showInputDialog("Enter Quantity: "); // Get quantity
                try {
                    int quantity = Integer.parseInt(quantityStr); // Convert quantity to integer
                    Product product = orderManagement.findProductById(productId); // Find the product by ID
                    if (product != null && product.getStock() >= quantity) {
                        orderProducts.add(new Product(product.getProductId(), product.getProductName(), product.getPrice(), quantity)); // Add product to the order
                        orderManagement.updateStock(productId, quantity); // Update stock in the system
                    } else {
                        JOptionPane.showMessageDialog(frame, "Product not found or insufficient stock.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a number for quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (!orderProducts.isEmpty()) {
                Customer customer = orderManagement.findCustomerById(customerId); // Find the customer by ID
                if (customer != null) {
                    double totalCost = orderProducts.stream().mapToDouble(p -> p.getPrice() * p.getStock()).sum(); // Calculate total cost
                    Order order = new Order("O" + (orderManagement.displayAllOrders().size() + 1), customer, orderProducts, totalCost); // Create the order
                    orderManagement.createOrder(order); // Add the order to the system
                    JOptionPane.showMessageDialog(frame, "Order created successfully."); // Display success message
                } else {
                    JOptionPane.showMessageDialog(frame, "Customer not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action for listing all products
        listProductsButton.addActionListener(e -> {
            List<Product> products = orderManagement.displayAllProducts(); // Get the list of products
            StringBuilder message = new StringBuilder("Products in the System:\n"); // Create message
            for (Product product : products) {
                message.append(product.getProductName())
                        .append(" (ID: ").append(product.getProductId())
                        .append(", Price: ").append(product.getPrice())
                        .append(", Stock: ").append(product.getStock())
                        .append(")\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString()); // Display the products
        });

        // Action for listing all customers
        listCustomerButton.addActionListener(e -> {
            List<Customer> customers = orderManagement.displayAllCustomers(); // Get the list of customers
            StringBuilder message = new StringBuilder("Customers in the System:\n"); // Create message
            for (Customer customer : customers) {
                message.append(customer.getCustomerName())
                        .append(" (ID: ").append(customer.getCustomerId())
                        .append(", Phone: ").append(customer.getPhone())
                        .append(", Email: ").append(customer.getEmail())
                        .append(")\n");
            }
            JOptionPane.showMessageDialog(frame, message.toString()); // Display the customers
        });

        // Action for listing all orders
        listOrdersButton.addActionListener(e -> {
            try {
                List<Order> orders = orderManagement.displayAllOrders(); // Get the list of orders
                if (orders == null || orders.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No orders found."); // Display message if no orders
                    return;
                }
                StringBuilder message = new StringBuilder("Orders in the system:\n"); // Create message
                for (Order order : orders) {
                    message.append("Order Id: ").append(order.getOrderId())
                            .append(", Customer: ").append(order.getCustomer().getCustomerName())
                            .append(", Total cost: ").append(order.getTotalCost())
                            .append(", Products:\n");
                    List<Product> products = order.getProducts();
                    if (products != null && !products.isEmpty()) {
                        for (Product product : products) {
                            message.append(" - ").append(product.getProductName())
                                    .append(" x ").append(product.getStock()).append("\n");
                        }
                    } else {
                        message.append(" No products in the order.\n");
                    }
                    message.append("\n");
                }
                JOptionPane.showMessageDialog(frame, message.toString()); // Display the orders
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "An error occurred while retrieving orders: " + ex.getMessage());
            }
        });
    }

    // Main method to run the application
    public static void main(String[] args) {
        new OnlineOrderManagementGUI();
    }
}

