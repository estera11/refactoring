package excercise.listeners;

import excercise.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddAcountListener implements ActionListener {
    private Menu parent;
    private JFrame selectUserTypeFrame;

    public AddAcountListener(Menu context, JFrame frame) {
        this.parent = context;
        this.selectUserTypeFrame = frame;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectUserTypeFrame.dispose();

        if (parent.customerList.isEmpty()) {
            JOptionPane.showMessageDialog(selectUserTypeFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
            selectUserTypeFrame.dispose();
            parent.admin();
        } else {
            boolean loop = true;

            boolean found = false;

            while (loop) {
                Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer You Wish to Add an Account to:");

                for (Customer aCustomer : parent.customerList) {

                    if (aCustomer.getCustomerID().equals(customerID)) {
                        found = true;
                        parent.customer = aCustomer;
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
                } else {
                    loop = false;
                    //a combo box in an dialog box that asks the admin what type of account they wish to create (deposit/current)
                    String[] choices = {"Current Account", "Deposit Account"};
                    String account = (String) JOptionPane.showInputDialog(null, "Please choose account type",
                            "Account Type", JOptionPane.QUESTION_MESSAGE, null, choices, choices[1]);

                    if (account.equals("Current Account")) {
                        //create current account
                        boolean valid = true;
                        double balance = 0;
                        String number = "C" + (parent.customerList.indexOf(parent.customer) + 1) * 10 + (parent.customer.getAccounts().size() + 1);//this simple algorithm generates the account number
                        ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
                        int randomPIN = (int) (Math.random() * 9000) + 1000;
                        String pin = String.valueOf(randomPIN);

                        ATMCard atm = new ATMCard(randomPIN, valid);

                        CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance, transactionList);

                        parent.customer.getAccounts().add(current);
                        JOptionPane.showMessageDialog(selectUserTypeFrame, "Account number = " + number + "\n PIN = " + pin, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                        closeFrame();

                    }

                    if (account.equals("Deposit Account")) {
                        //create deposit account

                        double balance = 0, interest = 0;
                        String number = "D" + (parent.customerList.indexOf(parent.customer) + 1) * 10 + (parent.customer.getAccounts().size() + 1);//this simple algorithm generates the account number
                        ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

                        CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance, transactionList);

                        parent.customer.getAccounts().add(deposit);
                        JOptionPane.showMessageDialog(selectUserTypeFrame, "Account number = " + number, "Account created.", JOptionPane.INFORMATION_MESSAGE);
                        closeFrame();
                    }



                }
            }
        }

    }
    private void closeFrame(){
        selectUserTypeFrame.dispose();
        parent.admin();
    }
}
