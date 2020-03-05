package excercise.listeners;

import excercise.Customer;
import excercise.Menu;

import javax.swing.*;
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
        boolean found = true, loop = true;


        {
            Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer from which you wish to delete an account");

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
                //Here I would make the user select a an account to delete from a combo box. If the account had a balance of 0 then it would be deleted. (I do not have time to do this)
            }


        }

    }
}
