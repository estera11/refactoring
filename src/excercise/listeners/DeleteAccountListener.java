package excercise.listeners;

import excercise.Customer;
import excercise.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAccountListener implements ActionListener {

    private Menu parent;
    private JFrame selectUserTypeFrame;
    private Customer customer;

    public DeleteAccountListener(Menu context, JFrame frame, Customer c) {
        this.parent = context;
        this.selectUserTypeFrame = frame;
        this.customer = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean found = false, loop = true;
        while (loop) {
            Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer from which you wish to delete an account");

            for (Customer aCustomer : parent.customerList) {

                if (aCustomer.getCustomerID().equals(customerID)) {
                    found = true;
                    customer = aCustomer;

                }
            }

            if (!found) {
                int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                switch (reply) {
                    case JOptionPane.YES_OPTION:
                        loop = true;
                        break;
                    case JOptionPane.NO_OPTION:
                        selectUserTypeFrame.dispose();
                        loop = false;
                        parent.admin();
                        break;
                }
            }

        }

    }
}

