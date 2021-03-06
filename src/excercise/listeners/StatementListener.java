package excercise.listeners;

import excercise.Customer;
import excercise.CustomerAccount;
import excercise.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StatementListener implements ActionListener {

    private Menu parent;
    private JFrame selectUserTypeFrame;
    private CustomerAccount acc;
    private Container content;
    private Customer customer;


    public StatementListener(Menu context, JFrame frame, CustomerAccount customerAccount, Container container, Customer c) {
        this.parent = context;
        this.selectUserTypeFrame = frame;
        this.acc = customerAccount;
        this.content = container;
        this.customer = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        selectUserTypeFrame.dispose();
        selectUserTypeFrame = new JFrame("Customer Menu");
        selectUserTypeFrame.setSize(400, 600);
        selectUserTypeFrame.setLocation(200, 200);
        selectUserTypeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        selectUserTypeFrame.setVisible(true);

        JLabel label1 = new JLabel("Summary of account transactions: ");

        JPanel returnPanel = new JPanel();
        JButton returnButton = new JButton("Return");
        returnPanel.add(returnButton);

        JPanel textPanel = new JPanel();

        textPanel.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea(40, 20);
        textArea.setEditable(false);
        textPanel.add(label1, BorderLayout.NORTH);
        textPanel.add(textArea, BorderLayout.CENTER);
        textPanel.add(returnButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel.add(scrollPane);

        for (int i = 0; i < acc.getTransactionList().size(); i++) {
            textArea.append(acc.getTransactionList().get(i).toString());

        }

        textPanel.add(textArea);
        content.removeAll();


        Container content = selectUserTypeFrame.getContentPane();
        content.setLayout(new GridLayout(1, 1));
        content.add(textPanel);

        ReturnListener returnListener = new ReturnListener(parent, selectUserTypeFrame);
        returnButton.addActionListener(returnListener);
    }

}

