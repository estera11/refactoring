package excercise.listeners;

import excercise.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static excercise.Service.isNumeric;

public class WithdrawListener implements ActionListener {
    private Menu parent;
    private JFrame selectUserTypeFrame;
    private CustomerAccount customerAccount;
    private Customer customer;

    public WithdrawListener(Menu context, JFrame frame, CustomerAccount acc, Customer cust){
        this.parent = context;
        this.selectUserTypeFrame = frame;
        this.customerAccount = acc;
        this.customer = cust;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean loop = true;
        boolean on = true;
        double withdraw = 0;

        if (customerAccount instanceof CustomerCurrentAccount) {
            int count = 3;
            int checkPin = ((CustomerCurrentAccount) customerAccount).getAtm().getPin();
            loop = true;

            while (loop) {
                if (count == 0) {
                    JOptionPane.showMessageDialog(selectUserTypeFrame, "Pin entered incorrectly 3 times. ATM card locked.", "Pin", JOptionPane.INFORMATION_MESSAGE);
                    ((CustomerCurrentAccount) customerAccount).getAtm().setValid(false);
                    parent.customer(customer);
                    loop = false;
                    on = false;
                }

                String Pin = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter 4 digit PIN;");
                int i = Integer.parseInt(Pin);

                if (on) {
                    if (checkPin == i) {
                        loop = false;
                        JOptionPane.showMessageDialog(selectUserTypeFrame, "Pin entry successful", "Pin", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        count--;
                        JOptionPane.showMessageDialog(selectUserTypeFrame, "Incorrect pin. " + count + " attempts remaining.", "Pin", JOptionPane.INFORMATION_MESSAGE);

                    }

                }
            }


        }
        if (on) {
            String balanceTest = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter amount you wish to withdraw (max 500):");//the isNumeric method tests to see if the string entered was numeric.
            if (isNumeric(balanceTest)) {

                withdraw = Double.parseDouble(balanceTest);
                loop = false;


            } else {
                JOptionPane.showMessageDialog(selectUserTypeFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
            }
            if (withdraw > 500) {
                JOptionPane.showMessageDialog(selectUserTypeFrame, "500 is the maximum you can withdraw at a time.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                withdraw = 0;
            }
            if (withdraw > customerAccount.getBalance()) {
                JOptionPane.showMessageDialog(selectUserTypeFrame, "Insufficient funds.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                withdraw = 0;
            }

            String euro = "\u20ac";
            customerAccount.setBalance(customerAccount.getBalance() - withdraw);

            Date date = new Date();
            String date2 = date.toString();

            String type = "Withdraw";
            double amount = withdraw;


            AccountTransaction transaction = new AccountTransaction(date2, type, amount);
            customerAccount.getTransactionList().add(transaction);


            JOptionPane.showMessageDialog(selectUserTypeFrame, withdraw + euro + " withdrawn.", "Withdraw", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(selectUserTypeFrame, "New balance = " + customerAccount.getBalance() + euro, "Withdraw", JOptionPane.INFORMATION_MESSAGE);
        }


    }
}
