package excercise.listeners;

import excercise.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static excercise.Service.isNumeric;

public class LodgementListener implements ActionListener {

    private Menu parent;
    private JFrame selectUserTypeFrame;
    private Customer customer;


    public LodgementListener(Menu context, JFrame frame,  Customer cust) {
        this.parent = context;
        this.selectUserTypeFrame = frame;
        this.customer = cust;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        boolean loop = true;
        boolean on = true;
        double balance = 0;

        if (parent.customerAccount instanceof CustomerCurrentAccount) {
            int count = 3;
            int checkPin = ((CustomerCurrentAccount) parent.customerAccount).getAtm().getPin();
            loop = true;

            while (loop) {
                if (count == 0) {
                    JOptionPane.showMessageDialog(selectUserTypeFrame, "Pin entered incorrectly 3 times. ATM card locked.", "Pin", JOptionPane.INFORMATION_MESSAGE);
                    ((CustomerCurrentAccount) parent.customerAccount).getAtm().setValid(false);
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
            String balanceTest = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter amount you wish to lodge:");//the isNumeric method tests to see if the string entered was numeric.
            if (isNumeric(balanceTest)) {

                balance = Double.parseDouble(balanceTest);
                loop = false;


            } else {
                JOptionPane.showMessageDialog(selectUserTypeFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
            }


            String euro = "\u20ac";
            parent.customerAccount.setBalance(parent.customerAccount.getBalance() + balance);
            // String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            Date date = new Date();
            String date2 = date.toString();
            String type = "Lodgement";
            double amount = balance;


            AccountTransaction transaction = new AccountTransaction(date2, type, amount);
            parent.customerAccount.getTransactionList().add(transaction);

            JOptionPane.showMessageDialog(selectUserTypeFrame, balance + euro + " added do you account!", "Lodgement", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(selectUserTypeFrame, "New balance = " + parent.customerAccount.getBalance() + euro, "Lodgement", JOptionPane.INFORMATION_MESSAGE);
        }

    }

}
