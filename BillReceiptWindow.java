package com.restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BillReceiptWindow extends JFrame {
    private JTextArea receiptTextArea;
    private BillingService billingService;
    private String customerName;
    private String orderNumber;

    public BillReceiptWindow(BillingService billingService, String customerName) {
        this.billingService = billingService;
        this.customerName = (customerName != null && !customerName.trim().isEmpty()) ? customerName : "Guest";
        this.orderNumber = "ORD" + (1000 + new Random().nextInt(9000));
        
        setTitle("Bill Receipt");
        setSize(420, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        receiptTextArea = new JTextArea();
        receiptTextArea.setEditable(false);
        receiptTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(receiptTextArea);
        add(scrollPane, BorderLayout.CENTER);

        generateReceiptText();

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.DARK_GRAY);
        
        JButton btnSave = createStyledButton("Save Bill");
        JButton btnPrint = createStyledButton("Print Bill");
        
        btnSave.addActionListener(e -> saveBillToFile());
        btnPrint.addActionListener(e -> printBill());
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnPrint);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private void generateReceiptText() {
        StringBuilder receipt = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        receipt.append("=============================================\n");
        receipt.append("          NILAMBARI MAM RESTAURANT\n");
        receipt.append("     Restaurant Billing Management System\n");
        receipt.append("=============================================\n");
        receipt.append(String.format("Order Number : %s\n", orderNumber));
        receipt.append(String.format("Date         : %s\n", dateFormat.format(new Date())));
        receipt.append(String.format("Customer     : %s\n", customerName));
        receipt.append("---------------------------------------------\n");
        receipt.append(String.format("%-4s %-18s %-7s %-5s %-10s\n", "No", "Item Name", "Price", "Qty", "Total"));
        receipt.append("---------------------------------------------\n");
        
        for (OrderItem item : billingService.getCurrentOrder()) {
            String name = item.getFoodItem().getName();
            if (name.length() > 16) {
                name = name.substring(0, 16) + "..";
            }
            receipt.append(String.format("%-4d %-18s ₹%-6.2f %-5d ₹%.2f\n", 
                item.getFoodItem().getItemNumber(),
                name, 
                item.getFoodItem().getPrice(),
                item.getQuantity(), 
                item.getTotalPrice()));
        }
        
        receipt.append("---------------------------------------------\n");
        receipt.append(String.format("%-20s %-9d\n", "Total Items/Qty:", billingService.getTotalItems() + billingService.getTotalQuantity()));
        receipt.append(String.format("%-30s ₹%.2f\n", "Subtotal:", billingService.getSubtotal()));
        receipt.append(String.format("%-30s ₹%.2f\n", "GST (5%):", billingService.getGst()));
        receipt.append("---------------------------------------------\n");
        receipt.append(String.format("%-30s ₹%.2f\n", "GRAND TOTAL:", billingService.getGrandTotal()));
        receipt.append("=============================================\n");
        receipt.append("         Thank you for your visit! \n");
        receipt.append("=============================================\n");
        
        receiptTextArea.setText(receipt.toString());
    }
    
    private void saveBillToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Bill");
        fileChooser.setSelectedFile(new File(orderNumber + "_Bill.txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(receiptTextArea.getText());
                JOptionPane.showMessageDialog(this, "Bill saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving bill: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void printBill() {
        try {
            boolean complete = receiptTextArea.print();
            if (complete) {
                JOptionPane.showMessageDialog(this, "Printing Completed", "Printer", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Printing Cancelled", "Printer", JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Printer Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
