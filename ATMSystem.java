import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Account {
    private String username;
    private String pin;  // Changed from password to pin
    private double balance;

    public Account(String username, String pin) {
        this.username = username;
        this.pin = pin;
        this.balance = 0.0;  // No initial balance set
    }

    public String getUsername() {
        return username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String newPin) {
        this.pin = newPin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            JOptionPane.showMessageDialog(null, "Successfully deposited: " + amount);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            JOptionPane.showMessageDialog(null, "Successfully withdrew: " + amount);
        } else if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Insufficient funds.");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid withdrawal amount.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class ATMSystem {
    private static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        // Create Fixed Account (admin, 1234)
        String fixedUsername = "admin";
        String fixedPin = "1234";
        Account fixedAccount = new Account(fixedUsername, fixedPin);
        accounts.put(fixedUsername, fixedAccount); // Store the fixed account

        // Login JFrame
        JFrame loginFrame = new JFrame("ATM System - Login");

        // Set the image icon for the JFrame title
        ImageIcon icon = new ImageIcon("atm_icon.png");  // Path to your image
        loginFrame.setIconImage(icon.getImage());  // Set the icon for the window

        // Welcome Label
        JLabel welcomeLabel = new JLabel("            ATM System");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));  // Custom Font
        welcomeLabel.setBounds(50, 10, 250, 30); // Set position
        
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("PIN:");
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton createButton = new JButton("Create Account");

        // Tooltips
        loginButton.setToolTipText("Log in to your account");
        createButton.setToolTipText("Create a new account");

        // Set button color to Sky Blue
        loginButton.setBackground(Color.CYAN);
        createButton.setBackground(Color.CYAN);

        loginFrame.setLayout(null);
        welcomeLabel.setBounds(50, 10, 250, 30); // Place the welcome label
        userLabel.setBounds(30, 50, 100, 30);
        passLabel.setBounds(30, 90, 100, 30);
        userField.setBounds(120, 50, 150, 30);
        passField.setBounds(120, 90, 150, 30);
        loginButton.setBounds(50, 130, 100, 30);
        createButton.setBounds(160, 130, 150, 30);

        loginFrame.add(welcomeLabel);
        loginFrame.add(userLabel);
        loginFrame.add(passLabel);
        loginFrame.add(userField);
        loginFrame.add(passField);
        loginFrame.add(loginButton);
        loginFrame.add(createButton);

        loginFrame.setSize(350, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);

        // Login Button Action
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String pin = new String(passField.getPassword());

            if (fixedUsername.equals(username) && fixedPin.equals(pin)) {
                JOptionPane.showMessageDialog(loginFrame, "Login Successful!");
                loginFrame.dispose();
                openMenu(fixedAccount);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials, Try Again!");
            }
        });

        // Create Account Button Action (optional)
        createButton.addActionListener(e -> openCreateAccountFrame());
    }

    private static void openCreateAccountFrame() {
        // Allow the user to create a new account
        JFrame createFrame = new JFrame("ATM System - Create Account");
        JLabel userLabel = new JLabel("New Username:");
        JLabel passLabel = new JLabel("New PIN:");
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton createButton = new JButton("Create");

        createButton.setToolTipText("Submit your new account details");

        createFrame.setLayout(null);
        userLabel.setBounds(30, 30, 120, 30);
        passLabel.setBounds(30, 70, 120, 30);
        userField.setBounds(150, 30, 150, 30);
        passField.setBounds(150, 70, 150, 30);
        createButton.setBounds(100, 120, 100, 30);

        createFrame.add(userLabel);
        createFrame.add(passLabel);
        createFrame.add(userField);
        createFrame.add(passField);
        createFrame.add(createButton);

        createFrame.setSize(350, 200);
        createFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createFrame.setVisible(true);

        createButton.addActionListener(e -> {
            String username = userField.getText();
            String pin = new String(passField.getPassword());

            // Validate username (letters only)
            if (!username.matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(createFrame, "Username must only contain letters (no numbers or special characters)!");
                return;
            }

            // Validate PIN (numbers only)
            if (!pin.matches("\\d+")) {
                JOptionPane.showMessageDialog(createFrame, "PIN must only contain digits (no spaces or decimals)!");
                return;
            }

            // Check if fields are empty
            if (username.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(createFrame, "Please fill in all fields!");
            } else if (accounts.containsKey(username)) {
                JOptionPane.showMessageDialog(createFrame, "Account already exists!");
            } else {
                // Create new account (no initial balance setup)
                Account newAccount = new Account(username, pin);
                accounts.put(username, newAccount);
                JOptionPane.showMessageDialog(createFrame, "Account Created Successfully!");
                createFrame.dispose();  // Close the create account window
            }
        });
    }

    private static void openMenu(Account account) {
        JFrame menuFrame = new JFrame("ATM System - Menu");

        // Set the image icon for the JFrame title (for the menu frame)
        ImageIcon icon = new ImageIcon("atm_icon.png");  // Path to your image
        menuFrame.setIconImage(icon.getImage());  // Set the icon for the window

        JButton balanceButton = new JButton("Balance Inquiry");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton changePinButton = new JButton("Change PIN");
        JButton logoutButton = new JButton("Logout");

        // Tooltips
        balanceButton.setToolTipText("Check your current balance");
        depositButton.setToolTipText("Add money to your account");
        withdrawButton.setToolTipText("Take money from your account");
        changePinButton.setToolTipText("Update your account PIN");
        logoutButton.setToolTipText("Exit your account");

        // Set button color to Sky Blue
        balanceButton.setBackground(Color.CYAN);
        depositButton.setBackground(Color.CYAN);
        withdrawButton.setBackground(Color.CYAN);
        changePinButton.setBackground(Color.CYAN);
        logoutButton.setBackground(Color.CYAN);

        menuFrame.setLayout(null);
        balanceButton.setBounds(50, 30, 150, 30);
        depositButton.setBounds(50, 70, 150, 30);
        withdrawButton.setBounds(50, 110, 150, 30);
        changePinButton.setBounds(50, 150, 150, 30);
        logoutButton.setBounds(50, 190, 150, 30);

        menuFrame.add(balanceButton);
        menuFrame.add(depositButton);
        menuFrame.add(withdrawButton);
        menuFrame.add(changePinButton);
        menuFrame.add(logoutButton);

        menuFrame.setSize(250, 300);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setVisible(true);

        // Button Actions with Switch Case
        ActionListener actionListener = e -> {
            int choice;
            if (e.getSource() == balanceButton) choice = 1;
            else if (e.getSource() == depositButton) choice = 2;
            else if (e.getSource() == withdrawButton) choice = 3;
            else if (e.getSource() == changePinButton) choice = 4;
            else choice = 5;

            switch (choice) {
                case 1: // Balance Inquiry
                    JOptionPane.showMessageDialog(menuFrame, "Your balance is: " + account.getBalance());
                    break;
                case 2: // Deposit
                    String depositAmount = JOptionPane.showInputDialog("Enter deposit amount:");
                    try {
                        double amount = Double.parseDouble(depositAmount);
                        account.deposit(amount);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(menuFrame, "Invalid deposit amount.");
                    }
                    break;
                case 3: // Withdraw
                    String withdrawAmount = JOptionPane.showInputDialog("Enter withdrawal amount:");
                    try {
                        double amount = Double.parseDouble(withdrawAmount);
                        account.withdraw(amount);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(menuFrame, "Invalid withdrawal amount.");
                    }
                    break;
                case 4: // Change PIN
                    String currentPin = JOptionPane.showInputDialog("Enter current PIN:");
                    if (currentPin.equals(account.getPin())) {
                        String newPin = JOptionPane.showInputDialog("Enter new PIN:");
                        if (newPin.equals(account.getPin())) {
                            JOptionPane.showMessageDialog(menuFrame, "New PIN cannot be the same as the old one.");
                        } else {
                            account.setPin(newPin);
                            JOptionPane.showMessageDialog(menuFrame, "PIN successfully changed!");
                            menuFrame.dispose();
                            openLoginFrame();
                        }
                    } else {
                        JOptionPane.showMessageDialog(menuFrame, "Incorrect current PIN.");
                    }
                    break;
                case 5: // Logout
                    menuFrame.dispose();
                    openLoginFrame();
                    break;
            }
        };

        balanceButton.addActionListener(actionListener);
        depositButton.addActionListener(actionListener);
        withdrawButton.addActionListener(actionListener);
        changePinButton.addActionListener(actionListener);
        logoutButton.addActionListener(actionListener);
    }

    private static void openLoginFrame() {
        // Logic to open the login screen again after logout or PIN change
        // You can reuse the existing login frame creation code here
    }
}
