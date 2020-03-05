package excercise.listeners;

import excercise.Customer;
import excercise.Menu;
import excercise.ReturnListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static excercise.Service.isNumeric;

public class InterestListener implements ActionListener {

    private Menu parent;
    private JFrame selectUserTypeFrame;

    public InterestListener(Menu context, JFrame frame) {
        this.parent = context;
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
                Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer You Wish to Apply Interest to:");

                for (Customer aCustomer : parent.customerList) {

                    if (aCustomer.getCustomerID().equals(customerID)) {
                        found = true;
                        parent.customer = aCustomer;
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
                    for (int i = 0; i < parent.customer.getAccounts().size(); i++) {


                        box.addItem(parent.customer.getAccounts().get(i).getNumber());
                    }


                    box.getSelectedItem();

                    JPanel boxPanel = new JPanel();

                    JLabel label = new JLabel("Select an account to apply interest to:");
                    boxPanel.add(label);
                    boxPanel.add(box);
                    JPanel buttonPanel = new JPanel();
                    JButton continueButton = new JButton("Apply Interest");
                    JButton returnButton = new JButton("Return");
                    buttonPanel.add(continueButton);
                    buttonPanel.add(returnButton);
                    Container content = selectUserTypeFrame.getContentPane();
                    content.setLayout(new GridLayout(2, 1));

                    content.add(boxPanel);
                    content.add(buttonPanel);


                    if (parent.customer.getAccounts().isEmpty()) {
                        JOptionPane.showMessageDialog(selectUserTypeFrame, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                        selectUserTypeFrame.dispose();
                        parent.admin();
                    } else {

                        for (int i = 0; i < parent.customer.getAccounts().size(); i++) {
                            if (parent.customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                parent.customerAccount = parent.customer.getAccounts().get(i);
                            }
                        }

                        continueButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent ae) {
                                String euro = "\u20ac";
                                double interest = 0;
                                boolean loop = true;

                                while (loop) {
                                    String interestString = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter interest percentage you wish to apply: \n NOTE: Please enter a numerical value. (with no percentage sign) \n E.g: If you wish to apply 8% interest, enter '8'");//the isNumeric method tests to see if the string entered was numeric.
                                    if (isNumeric(interestString)) {

                                        interest = Double.parseDouble(interestString);
                                        loop = false;

                                        parent.customerAccount.setBalance(parent.customerAccount.getBalance() + (parent.customerAccount.getBalance() * (interest / 100)));

                                        JOptionPane.showMessageDialog(selectUserTypeFrame, interest + "% interest applied. \n new balance = " + parent.customerAccount.getBalance() + euro, "Success!", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(selectUserTypeFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                    }


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
