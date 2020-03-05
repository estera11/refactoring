package excercise.listeners;

import excercise.Customer;
import excercise.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class DeleteCustomerListener implements ActionListener {
    private Menu parent;
    private JFrame selectUserTypeFrame;
    private Customer customer;

    public DeleteCustomerListener(Menu context, JFrame frame, Customer customer) {
        this.parent = context;
        this.selectUserTypeFrame = frame;
        this.customer = customer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean found = true, loop = true;

        if (parent.customerList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
            parent.dispose();
            parent.admin();
        } else {
            {
                Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer You Wish to Delete:");

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
                    if (customer.getAccounts().size() > 0) {
                        JOptionPane.showMessageDialog(selectUserTypeFrame, "This customer has accounts. \n You must delete a customer's accounts before deleting a customer ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        parent.customerList.remove(customer);
                        JOptionPane.showMessageDialog(selectUserTypeFrame, "Customer Deleted ", "Success.", JOptionPane.INFORMATION_MESSAGE);
                    }
                }


            }
        }
    }
}
