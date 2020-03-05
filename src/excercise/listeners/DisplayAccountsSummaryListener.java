package excercise.listeners;

import excercise.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DisplayAccountsSummaryListener implements ActionListener {

    private Menu parent;
    private JFrame selectUserTypeFrame;
    private Container content;

    public DisplayAccountsSummaryListener(Menu context, JFrame frame, Container c) {
        this.parent = context;
        this.selectUserTypeFrame = frame;
        this.content = c;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectUserTypeFrame.dispose();


        selectUserTypeFrame = new JFrame("Summary of Transactions");
        selectUserTypeFrame.setSize(400, 700);
        selectUserTypeFrame.setLocation(200, 200);
        selectUserTypeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        selectUserTypeFrame.setVisible(true);

        JLabel label1 = new JLabel("Summary of all transactions: ");

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

        for (int a = 0; a < parent.customerList.size(); a++)//For each customer, for each account, it displays each transaction.
        {
            for (int b = 0; b < parent.customerList.get(a).getAccounts().size(); b++) {
                parent.customerAccount = parent.customerList.get(a).getAccounts().get(b);
                for (int c = 0; c < parent.customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {

                    textArea.append(parent.customerAccount.getTransactionList().get(c).toString());
                    //Int total = acc.getTransactionList().get(c).getAmount(); //I was going to use this to keep a running total but I couldnt get it  working.

                }
            }
        }


        textPanel.add(textArea);
        content.removeAll();


        Container content = selectUserTypeFrame.getContentPane();
        content.setLayout(new GridLayout(1, 1));
        //	content.add(label1);
        content.add(textPanel);
        //content.add(returnPanel);

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                selectUserTypeFrame.dispose();
                parent.admin();
            }
        });

    }
}
