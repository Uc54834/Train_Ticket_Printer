import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SLTrainTicketSystem extends JFrame {
    private JTextField customerNameField;
    private JTextField startPointField;
    private JTextField endPointField;
    private JTextField priceField;
    private JTextArea ticketArea;
    private JButton showButton;
    private JButton printButton;
    private JButton removeButton;
    private JComboBox<String> classComboBox;

    public SLTrainTicketSystem() {
        setTitle("Sri Lanka Train Ticket System");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel with Sri Lankan railway theme colors (blue and gold)
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 51, 102)), 
                "Ticket Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                new Color(0, 51, 102)));

        inputPanel.add(new JLabel("Customer Name:"));
        customerNameField = new JTextField();
        inputPanel.add(customerNameField);

        inputPanel.add(new JLabel("From (Station):"));
        startPointField = new JTextField();
        inputPanel.add(startPointField);

        inputPanel.add(new JLabel("To (Station):"));
        endPointField = new JTextField();
        inputPanel.add(endPointField);

        inputPanel.add(new JLabel("Class:"));
        String[] classes = {"1st Class", "2nd Class", "3rd Class"};
        classComboBox = new JComboBox<>(classes);
        inputPanel.add(classComboBox);

        inputPanel.add(new JLabel("Price (LKR):"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        add(inputPanel, BorderLayout.NORTH);

        // Ticket Display Area
        ticketArea = new JTextArea();
        ticketArea.setEditable(false);
        ticketArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        ticketArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(204, 153, 0)),
                "Ticket Preview",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                new Color(204, 153, 0)));
        JScrollPane scrollPane = new JScrollPane(ticketArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        showButton = new JButton("Show Ticket");
        printButton = new JButton("Print Ticket");
        removeButton = new JButton("Clear");

        // Set colors for buttons
        showButton.setBackground(new Color(0, 51, 102));
        showButton.setForeground(Color.WHITE);
        printButton.setBackground(new Color(0, 102, 0));
        printButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(153, 0, 0));
        removeButton.setForeground(Color.WHITE);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTicket();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printTicket();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTicket();
            }
        });

        buttonPanel.add(showButton);
        buttonPanel.add(printButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showTicket() {
        String name = customerNameField.getText().trim();
        String start = startPointField.getText().trim();
        String end = endPointField.getText().trim();
        String price = priceField.getText().trim();
        String ticketClass = (String) classComboBox.getSelectedItem();

        if (name.isEmpty() || start.isEmpty() || end.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double priceValue = Double.parseDouble(price);
            if (priceValue <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than zero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String ticket = "=================================\n" +
                      "    SRI LANKA RAILWAYS TICKET     \n" +
                      "=================================\n" +
                      " Passenger Name : " + name + "\n" +
                      " Journey        : " + start + " to " + end + "\n" +
                      " Class          : " + ticketClass + "\n" +
                      " Fare           : Rs. " + String.format("%,.2f", Double.parseDouble(price)) + "\n" +
                      "---------------------------------\n" +
                      " Date           : " + LocalDate.now() + "\n" +
                      " Time           : " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "\n" +
                      "=================================\n" +
                      "  Thank you for traveling with\n" +
                      "      Sri Lanka Railways";

        ticketArea.setText(ticket);
    }

    private void printTicket() {
        if (ticketArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please show ticket first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (ticketArea.print() == false) {
                JOptionPane.showMessageDialog(this, 
                    "Printing failed or was cancelled", 
                    "Print Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (java.awt.print.PrinterException e) {
            JOptionPane.showMessageDialog(this, 
                "Printing error: " + e.getMessage(), 
                "Print Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeTicket() {
        customerNameField.setText("");
        startPointField.setText("");
        endPointField.setText("");
        priceField.setText("");
        classComboBox.setSelectedIndex(0);
        ticketArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set system look and feel for better appearance
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                SLTrainTicketSystem frame = new SLTrainTicketSystem();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null); // Center the window
            }
        });
    }
}