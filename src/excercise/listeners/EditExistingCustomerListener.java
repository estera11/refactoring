package excercise.listeners;

import excercise.Customer;
import excercise.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditExistingCustomerListener implements ActionListener {
    private Menu parent;
    private JFrame selectUserTypeFrame;
    private Customer customer;


    public EditExistingCustomerListener(Menu context, JFrame frame, Customer c) {
        this.parent = context;
        this.selectUserTypeFrame = frame;
        this.customer = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean loop = true;

        boolean found = false;

        if (parent.customerList.isEmpty()) {
            JOptionPane.showMessageDialog(selectUserTypeFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
            selectUserTypeFrame.dispose();
            parent.admin();

        } else {

            while (loop) {
                Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter Customer ID:");

                for (Customer aCustomer : parent.customerList) {

                    if (aCustomer.getCustomerID().equals(customerID)) {
                        found = true;
                        customerID = aCustomer;
                    }
                }

                if (!found) {
                    int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        loop = true;
                    } else if (reply == JOptionPane.NO_OPTION) {
                        selectUserTypeFrame.dispose();
                        loop = false;

                        parent.admin();
                    }
                } else {
                    loop = false;
                }

            }

            selectUserTypeFrame.dispose();

            selectUserTypeFrame.dispose();
            selectUserTypeFrame = new JFrame("Administrator Menu");
            selectUserTypeFrame.setSize(400, 300);
            selectUserTypeFrame.setLocation(200, 200);
            selectUserTypeFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            });

            parent.firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
            parent.surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
            parent.pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
            parent.dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
            parent.customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
            parent.passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
            parent.firstNameTextField = new JTextField(20);
            parent.surnameTextField = new JTextField(20);
            parent.pPSTextField = new JTextField(20);
            parent.dOBTextField = new JTextField(20);
            parent.customerIDTextField = new JTextField(20);
            parent.passwordTextField = new JTextField(20);

            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JPanel cancelPanel = new JPanel();

            textPanel.add(parent.firstNameLabel);
            textPanel.add(parent.firstNameTextField);
            textPanel.add(parent.surnameLabel);
            textPanel.add(parent.surnameTextField);
            textPanel.add(parent.pPPSLabel);
            textPanel.add(parent.pPSTextField);
            textPanel.add(parent.dOBLabel);
            textPanel.add(parent.dOBTextField);
            textPanel.add(parent.customerIDLabel);
            textPanel.add(parent.customerIDTextField);
            textPanel.add(parent.passwordLabel);
            textPanel.add(parent.passwordTextField);

            parent.firstNameTextField.setText(customer.getFirstName());
            parent.surnameTextField.setText(customer.getSurname());
            parent.pPSTextField.setText(customer.getPPS());
            parent.dOBTextField.setText(customer.getDOB());
            parent.customerIDTextField.setText(customer.getCustomerID());
            parent.passwordTextField.setText(customer.getPassword());


            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Exit");

            cancelPanel.add(cancelButton, BorderLayout.SOUTH);
            cancelPanel.add(saveButton, BorderLayout.SOUTH);

            Container content = selectUserTypeFrame.getContentPane();
            content.setLayout(new GridLayout(2, 1));
            content.add(textPanel, BorderLayout.NORTH);
            content.add(cancelPanel, BorderLayout.SOUTH);

            selectUserTypeFrame.setContentPane(content);
            selectUserTypeFrame.setSize(340, 350);
            selectUserTypeFrame.setLocation(200, 200);
            selectUserTypeFrame.setVisible(true);
            selectUserTypeFrame.setResizable(false);

            saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {

                    parent.customer.setFirstName(parent.firstNameTextField.getText());
                    parent.customer.setSurname(parent.surnameTextField.getText());
                    parent.customer.setPPS(parent.pPSTextField.getText());
                    parent.customer.setDOB(parent.dOBTextField.getText());
                    parent.customer.setCustomerID(parent.customerIDTextField.getText());
                    parent.customer.setPassword(parent.passwordTextField.getText());

                    JOptionPane.showMessageDialog(null, "Changes Saved.");
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    selectUserTypeFrame.dispose();
                    parent.admin();
                }
            });
        }
    }
}
