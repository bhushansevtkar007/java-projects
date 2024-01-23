import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Product {
    private String name;
    private int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class InventoryManager {
    private List<Product> products;

    public InventoryManager() {
        this.products = new ArrayList<>();
    }

    public void addProduct(String name, int quantity) {
        Product product = new Product(name, quantity);
        products.add(product);
    }

    public void updateProductQuantity(String name, int newQuantity) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                product.setQuantity(newQuantity);
                break;
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }
}

class InventoryGUI extends JFrame {
    private JTextField productNameField;
    private JTextField quantityField;
    private JTextArea displayArea;
    private InventoryManager inventoryManager;

    public InventoryGUI(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;

        // UI components
        productNameField = new JTextField(20);
        quantityField = new JTextField(5);
        displayArea = new JTextArea(15, 30);
        JButton addButton = new JButton("Add Product");
        JButton updateButton = new JButton("Update Quantity");

        // Layout
        setLayout(new FlowLayout());
        add(new JLabel("Product Name:"));
        add(productNameField);
        add(new JLabel("Quantity:"));
        add(quantityField);
        add(addButton);
        add(updateButton);
        add(new JScrollPane(displayArea));

        // Event listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                inventoryManager.addProduct(productName, quantity);
                updateDisplay();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameField.getText();
                int newQuantity = Integer.parseInt(quantityField.getText());
                inventoryManager.updateProductQuantity(productName, newQuantity);
                updateDisplay();
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateDisplay() {
        displayArea.setText("");
        List<Product> products = inventoryManager.getProducts();
        for (Product product : products) {
            displayArea.append(product.getName() + ": " + product.getQuantity() + "\n");
        }
    }
}

public class InventoryManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InventoryManager inventoryManager = new InventoryManager();
                new InventoryGUI(inventoryManager);
            }
        });
    }
}
