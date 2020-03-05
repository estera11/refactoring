package excercise.listeners;


import excercise.Customer;
import excercise.CustomerCurrentAccount;
import excercise.CustomerDepositAccount;
import excercise.Menu;
import excercise.ReturnListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BankChargesListener implements ActionListener {

    private Menu parent;
    private Customer customer;
    private JFrame selectUserTypeFrame;


    public BankChargesListener(Menu context, Customer customer, JFrame frame) {
        this.parent = context;
        this.customer = customer;
        this.selectUserTypeFrame = frame;
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
                Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer You Wish to Apply Charges to:");

                for (Customer aCustomer : parent.customerList) {

                    if (aCustomer.getCustomerID().equals(customerID)) {
                        found = true;
                        customer = aCustomer;
                        loop = false;
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
                    selectUserTypeFrame.dispose();
                    selectUserTypeFrame = new JFrame("Administrator Menu");
                    selectUserTypeFrame.setSize(400, 300);
                    selectUserTypeFrame.setLocation(200, 200);
                    selectUserTypeFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                            System.exit(0);
                        }
                    });
                    selectUserTypeFrame.setVisible(true);


                    JComboBox<String> box = new JComboBox<String>();
                    for (int i = 0; i < customer.getAccounts().size(); i++) {


                        box.addItem(customer.getAccounts().get(i).getNumber());
                    }


                    box.getSelectedItem();

                    JPanel boxPanel = new JPanel();
                    boxPanel.add(box);

                    JPanel buttonPanel = new JPanel();
                    JButton continueButton = new JButton("Apply Charge");
                    JButton returnButton = new JButton("Return");
                    buttonPanel.add(continueButton);
                    buttonPanel.add(returnButton);
                    Container content = selectUserTypeFrame.getContentPane();
                    content.setLayout(new GridLayout(2, 1));

                    content.add(boxPanel);
                    content.add(buttonPanel);


                    if (customer.getAccounts().isEmpty()) {
                        JOptionPane.showMessageDialog(selectUserTypeFrame, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                        selectUserTypeFrame.dispose();
                        parent.admin();
                    } else {

                        for (int i = 0; i < customer.getAccounts().size(); i++) {
                            if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                parent.customerAccount = customer.getAccounts().get(i);
                            }
                        }

                        continueButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent ae) {
                                String euro = "\u20ac";


                                if (parent.customerAccount instanceof CustomerDepositAccount) {


                                    JOptionPane.showMessageDialog(selectUserTypeFrame, "25" + euro + " deposit account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                    parent.customerAccount.setBalance(parent.customerAccount.getBalance() - 25);
                                    JOptionPane.showMessageDialog(selectUserTypeFrame, "New balance = " + parent.customerAccount.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                }

                                if (parent.customerAccount instanceof CustomerCurrentAccount) {


                                    JOptionPane.showMessageDialog(selectUserTypeFrame, "15" + euro + " current account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                    parent.customerAccount.setBalance(parent.customerAccount.getBalance() - 25);
                                    JOptionPane.showMessageDialog(selectUserTypeFrame, "New balance = " + parent.customerAccount.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                }


                                selectUserTypeFrame.dispose();
                                parent.admin();
                            }
                        });

                        ReturnListener returnListener = new ReturnListener(parent, selectUserTypeFrame);
                        returnButton.addActionListener(returnListener);

                    }
                }
            }
        }


    }
}
