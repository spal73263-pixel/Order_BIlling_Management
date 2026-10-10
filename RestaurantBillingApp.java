package com.restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RestaurantBillingApp extends JFrame {
    
    private BillingService billingService;
    private List<FoodItem> menuItems;
    
    private JTextField txtCustomerName;
    private DefaultTableModel tableModel;
    private JTable orderTable;
    
    // Top Summary Cards
    private JLabel lblTotalItems;
    private JLabel lblTotalQuantity;
    private JLabel lblSubtotal;
    private JLabel lblGst;
    private JLabel lblGrandTotal;
    
    // UI Constants for theme
    private final Color COLOR_DARK_GREY = new Color(45, 45, 45);
    private final Color COLOR_ORANGE = new Color(255, 140, 0);
    private final Color COLOR_WHITE = Color.WHITE;
    private final Color COLOR_LIGHT_GREY = new Color(240, 240, 240);
    
    // Store spinners mapped to their food items
    private Map<FoodItem, JSpinner> spinnerMap;

    public RestaurantBillingApp() {
        billingService = new BillingService();
        menuItems = new ArrayList<>();
        spinnerMap = new LinkedHashMap<>();
        
        initializeMenu();
        initializeGUI();
    }
    
    private void initializeMenu() {
        int i = 1;
        // Starters
        menuItems.add(new FoodItem(i++, "Veg Manchurian", "Starters", 120));
        menuItems.add(new FoodItem(i++, "Paneer Tikka", "Starters", 180));
        menuItems.add(new FoodItem(i++, "Hara Bhara Kabab", "Starters", 150));
        menuItems.add(new FoodItem(i++, "Crispy Corn", "Starters", 130));
        menuItems.add(new FoodItem(i++, "Veg Spring Roll", "Starters", 120));
        menuItems.add(new FoodItem(i++, "French Fries", "Starters", 90));
        // South Indian
        menuItems.add(new FoodItem(i++, "Idli Sambhar", "South Indian", 60));
        menuItems.add(new FoodItem(i++, "Medu Vada", "South Indian", 70));
        menuItems.add(new FoodItem(i++, "Masala Dosa", "South Indian", 100));
        menuItems.add(new FoodItem(i++, "Mysore Dosa", "South Indian", 120));
        menuItems.add(new FoodItem(i++, "Uttapam", "South Indian", 110));
        menuItems.add(new FoodItem(i++, "Plain Dosa", "South Indian", 80));
        // Main Course
        menuItems.add(new FoodItem(i++, "Paneer Butter Masala", "Main Course", 220));
        menuItems.add(new FoodItem(i++, "Kadai Paneer", "Main Course", 210));
        menuItems.add(new FoodItem(i++, "Dal Tadka", "Main Course", 150));
        menuItems.add(new FoodItem(i++, "Veg Kolhapuri", "Main Course", 190));
        menuItems.add(new FoodItem(i++, "Chole Bhature", "Main Course", 140));
        menuItems.add(new FoodItem(i++, "Rajma Rice", "Main Course", 130));
        menuItems.add(new FoodItem(i++, "Veg Biryani", "Main Course", 180));
        menuItems.add(new FoodItem(i++, "Jeera Rice", "Main Course", 110));
        menuItems.add(new FoodItem(i++, "Butter Naan", "Main Course", 40));
        menuItems.add(new FoodItem(i++, "Tandoori Roti", "Main Course", 25));
        // Chinese
        menuItems.add(new FoodItem(i++, "Veg Hakka Noodles", "Chinese", 140));
        menuItems.add(new FoodItem(i++, "Schezwan Noodles", "Chinese", 160));
        menuItems.add(new FoodItem(i++, "Veg Fried Rice", "Chinese", 140));
        menuItems.add(new FoodItem(i++, "Schezwan Fried Rice", "Chinese", 160));
        menuItems.add(new FoodItem(i++, "Chilli Paneer", "Chinese", 190));
        menuItems.add(new FoodItem(i++, "Veg Momos", "Chinese", 100));
        // Snacks
        menuItems.add(new FoodItem(i++, "Veg Sandwich", "Snacks", 80));
        menuItems.add(new FoodItem(i++, "Cheese Sandwich", "Snacks", 110));
        menuItems.add(new FoodItem(i++, "Veg Burger", "Snacks", 100));
        menuItems.add(new FoodItem(i++, "Cheese Pizza", "Snacks", 180));
        menuItems.add(new FoodItem(i++, "Margherita Pizza", "Snacks", 160));
        menuItems.add(new FoodItem(i++, "Pav Bhaji", "Snacks", 100));
        menuItems.add(new FoodItem(i++, "Vada Pav", "Snacks", 25));
        // Desserts
        menuItems.add(new FoodItem(i++, "Gulab Jamun", "Desserts", 50));
        menuItems.add(new FoodItem(i++, "Rasmalai", "Desserts", 80));
        menuItems.add(new FoodItem(i++, "Brownie", "Desserts", 100));
        menuItems.add(new FoodItem(i++, "Vanilla Ice Cream", "Desserts", 60));
        menuItems.add(new FoodItem(i++, "Chocolate Ice Cream", "Desserts", 70));
        // Beverages
        menuItems.add(new FoodItem(i++, "Tea", "Beverages", 20));
        menuItems.add(new FoodItem(i++, "Coffee", "Beverages", 35));
        menuItems.add(new FoodItem(i++, "Cold Coffee", "Beverages", 80));
        menuItems.add(new FoodItem(i++, "Fresh Lime Soda", "Beverages", 50));
        menuItems.add(new FoodItem(i++, "Mango Juice", "Beverages", 60));
        menuItems.add(new FoodItem(i++, "Soft Drink", "Beverages", 40));
        menuItems.add(new FoodItem(i++, "Mineral Water", "Beverages", 20));
    }
    
    private void initializeGUI() {
        setTitle("Nilambari Mam Restaurant - Billing System");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // NORTH - Header & Summary Cards
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        northPanel.add(createSummaryCardsPanel(), BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        
        // CENTER - Split pane for Menu and Order Table
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createMenuPanel());
        splitPane.setRightComponent(createOrderPanel());
        splitPane.setDividerLocation(550);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);
        
        // SOUTH - Bottom Buttons
        add(createBottomPanel(), BorderLayout.SOUTH);
        
        updateSummary(); // Initialize cards to 0
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_DARK_GREY);
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        JLabel lblTitle = new JLabel("Nilambari Mam Restaurant");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(COLOR_ORANGE);
        
        JLabel lblWelcome = new JLabel("Restaurant Billing Management System");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblWelcome.setForeground(COLOR_WHITE);
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(COLOR_DARK_GREY);
        titlePanel.add(lblTitle);
        titlePanel.add(lblWelcome);
        
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        customerPanel.setBackground(COLOR_DARK_GREY);
        JLabel lblCustomer = new JLabel("Customer Name: ");
        lblCustomer.setForeground(COLOR_WHITE);
        lblCustomer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtCustomerName = new JTextField(15);
        txtCustomerName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        customerPanel.add(lblCustomer);
        customerPanel.add(txtCustomerName);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(customerPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createSummaryCardsPanel() {
        JPanel cardsPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        cardsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cardsPanel.setBackground(COLOR_LIGHT_GREY);
        
        lblTotalItems = new JLabel("0");
        lblTotalQuantity = new JLabel("0");
        lblSubtotal = new JLabel("₹0.00");
        lblGst = new JLabel("₹0.00");
        lblGrandTotal = new JLabel("₹0.00");
        
        cardsPanel.add(createSingleCard("Total Items", lblTotalItems, new Color(52, 152, 219))); // Blue
        cardsPanel.add(createSingleCard("Total Quantity", lblTotalQuantity, new Color(155, 89, 182))); // Purple
        cardsPanel.add(createSingleCard("Subtotal", lblSubtotal, new Color(241, 196, 15))); // Yellow
        cardsPanel.add(createSingleCard("GST (5%)", lblGst, new Color(230, 126, 34))); // Orange
        cardsPanel.add(createSingleCard("Grand Total", lblGrandTotal, new Color(46, 204, 113))); // Green
        
        return cardsPanel;
    }
    
    private JPanel createSingleCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(COLOR_WHITE);
        card.setBorder(BorderFactory.createLineBorder(color, 3, true));
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(COLOR_DARK_GREY);
        titleLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setBorder(new EmptyBorder(5, 5, 10, 5));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createMenuPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(COLOR_WHITE);
        container.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_ORANGE, 2), 
                " Menu Selection ", 
                TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 16), COLOR_DARK_GREY));

        // Menu List Section
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(COLOR_WHITE);

        String currentCategory = "";
        
        for (FoodItem item : menuItems) {
            if (!item.getCategory().equals(currentCategory)) {
                currentCategory = item.getCategory();
                JLabel categoryLabel = new JLabel(currentCategory);
                categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
                categoryLabel.setForeground(COLOR_ORANGE);
                categoryLabel.setBorder(new EmptyBorder(15, 10, 5, 10));
                categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                menuPanel.add(categoryLabel);
            }
            
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(COLOR_WHITE);
            itemPanel.setBorder(new EmptyBorder(3, 20, 3, 20));
            itemPanel.setMaximumSize(new Dimension(800, 35));
            itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel lblItemName = new JLabel(String.format("[%02d] %s", item.getItemNumber(), item.getName()));
            lblItemName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            JLabel lblPrice = new JLabel(String.format("₹%.2f  ", item.getPrice()));
            lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblPrice.setForeground(COLOR_DARK_GREY);
            
            SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 100, 1);
            JSpinner qtySpinner = new JSpinner(spinnerModel);
            qtySpinner.setPreferredSize(new Dimension(60, 25));
            JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) qtySpinner.getEditor();
            editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
            
            spinnerMap.put(item, qtySpinner);
            
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rightPanel.setBackground(COLOR_WHITE);
            rightPanel.add(lblPrice);
            rightPanel.add(qtySpinner);
            
            itemPanel.add(lblItemName, BorderLayout.CENTER);
            itemPanel.add(rightPanel, BorderLayout.EAST);
            
            menuPanel.add(itemPanel);
        }
        
        JButton btnAddToOrder = createStyledButton("Add Selected Item");
        btnAddToOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddToOrder.setMargin(new Insets(10, 20, 10, 20));
        btnAddToOrder.addActionListener(this::handleAddToOrder);
        
        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setBackground(COLOR_WHITE);
        buttonWrapper.setBorder(new EmptyBorder(20, 0, 20, 0));
        buttonWrapper.add(btnAddToOrder);
        buttonWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(buttonWrapper);
        
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        
        container.add(scrollPane, BorderLayout.CENTER);
        return container;
    }
    
    private JPanel createOrderPanel() {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBackground(COLOR_LIGHT_GREY);
        orderPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_DARK_GREY, 2), 
                " Current Bill ", 
                TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 16), COLOR_DARK_GREY));
        
        String[] columns = {"Item No", "Item Name", "Price", "Qty", "Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable = new JTable(tableModel);
        orderTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orderTable.setRowHeight(25);
        orderTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Adjust column widths
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        JScrollPane tableScroll = new JScrollPane(orderTable);
        orderPanel.add(tableScroll, BorderLayout.CENTER);
        
        JPanel orderActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        orderActionPanel.setBackground(COLOR_LIGHT_GREY);
        JButton btnRemove = createStyledButton("Remove Selected");
        JButton btnClear = createStyledButton("Clear Bill");
        
        btnRemove.addActionListener(e -> {
            int selectedRow = orderTable.getSelectedRow();
            if (selectedRow != -1) {
                billingService.removeOrderItem(selectedRow);
                updateOrderTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnClear.addActionListener(e -> {
            billingService.clearOrder();
            updateOrderTable();
        });
        
        orderActionPanel.add(btnRemove);
        orderActionPanel.add(btnClear);
        orderPanel.add(orderActionPanel, BorderLayout.SOUTH);
        
        return orderPanel;
    }
    
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(COLOR_DARK_GREY);
        
        JButton btnGenerateBill = createStyledButton("Generate Bill");
        btnGenerateBill.setPreferredSize(new Dimension(150, 40));
        
        JButton btnExit = createStyledButton("Exit");
        btnExit.setPreferredSize(new Dimension(100, 40));
        
        btnGenerateBill.addActionListener(e -> handleGenerateBill());
        btnExit.addActionListener(e -> System.exit(0));
        
        bottomPanel.add(btnGenerateBill);
        bottomPanel.add(btnExit);
        
        return bottomPanel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void handleAddToOrder(ActionEvent e) {
        boolean itemAdded = false;
        
        for (Map.Entry<FoodItem, JSpinner> entry : spinnerMap.entrySet()) {
            int qty = (Integer) entry.getValue().getValue();
            if (qty > 0) {
                billingService.addOrderItem(entry.getKey(), qty);
                entry.getValue().setValue(0); // Reset spinner
                itemAdded = true;
            }
        }
        
        if (itemAdded) {
            updateOrderTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select at least one item quantity greater than 0.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateOrderTable() {
        tableModel.setRowCount(0); // Clear table
        
        for (OrderItem item : billingService.getCurrentOrder()) {
            Object[] row = {
                item.getFoodItem().getItemNumber(),
                item.getFoodItem().getName(),
                String.format("₹%.2f", item.getFoodItem().getPrice()),
                item.getQuantity(),
                String.format("₹%.2f", item.getTotalPrice())
            };
            tableModel.addRow(row);
        }
        
        updateSummary();
    }
    
    private void updateSummary() {
        lblTotalItems.setText(String.valueOf(billingService.getTotalItems()));
        lblTotalQuantity.setText(String.valueOf(billingService.getTotalQuantity()));
        lblSubtotal.setText(String.format("₹%.2f", billingService.getSubtotal()));
        lblGst.setText(String.format("₹%.2f", billingService.getGst()));
        lblGrandTotal.setText(String.format("₹%.2f", billingService.getGrandTotal()));
    }
    
    private void handleGenerateBill() {
        if (billingService.getCurrentOrder().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cannot generate bill for an empty order.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        BillReceiptWindow receiptWindow = new BillReceiptWindow(billingService, txtCustomerName.getText());
        receiptWindow.setVisible(true);
        
        // After printing, optionally reset
        int confirm = JOptionPane.showConfirmDialog(this, "Bill Generated! Clear current bill for new customer?", "New Bill", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            billingService.clearOrder();
            txtCustomerName.setText("");
            updateOrderTable();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // fallback
        }
        
        SwingUtilities.invokeLater(() -> {
            RestaurantBillingApp app = new RestaurantBillingApp();
            app.setVisible(true);
        });
    }
}
