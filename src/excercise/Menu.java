package excercise;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Menu extends JFrame {

    private ArrayList<Customer> customerList = new ArrayList<>();
    private int position = 0;
    private Customer customer = null;
    private CustomerAccount acc = new CustomerAccount();
    private JFrame selectUserTypeFrame, createNewUserFrame;
    private JLabel firstNameLabel, surnameLabel, pPPSLabel, dOBLabel;
    private JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
    private JLabel customerIDLabel, passwordLabel;
    private JTextField customerIDTextField, passwordTextField;
    private Container content;
    Customer e;


    public JPanel panel2;
    private JButton add;

    public static void main(String[] args) {
        Menu driver = new Menu();

        //populating customerList for testing purpose
        ArrayList<CustomerAccount> ca = new ArrayList<>(Arrays.asList(new CustomerDepositAccount(1.5, "D1234", 2000.0, new ArrayList<AccountTransaction>())));
        ca.add(new CustomerCurrentAccount(new ATMCard(1234, true), "C1234", 1000.0, new ArrayList<AccountTransaction>()));
        driver.customerList.add(new Customer("1234", "Joe", "Bloggs", "11061998", "ID1234", "1234", ca));

        driver.menuStart();

    }

    public void menuStart() {
		   /*The menuStart method asks the user if they are a new customer, an existing customer or an admin. It will then start the create customer process
		  if they are a new customer, or will ask them to log in if they are an existing customer or admin.*/

        selectUserTypeFrame = new JFrame("User Type");
        selectUserTypeFrame.setSize(400, 300);
        selectUserTypeFrame.setLocation(200, 200);
        selectUserTypeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        createNewUserFrame = new JFrame("Create New Customer");


        JPanel userTypePanel = new JPanel();
        final ButtonGroup userType = new ButtonGroup();
        JRadioButton radioButton;
        addToPanel(userTypePanel, userType, "Existing Customer", "Customer");

        addToPanel(userTypePanel, userType, "Administrator", "Administrator");

        addToPanel(userTypePanel, userType, "New Customer", "New Customer");

        JPanel continuePanel = new JPanel();
        JButton continueButton = new JButton("Continue");
        continuePanel.add(continueButton);

        Container content = selectUserTypeFrame.getContentPane();
        content.setLayout(new GridLayout(2, 1));
        content.add(userTypePanel);
        content.add(continuePanel);


        continueButton.addActionListener(ae -> {
            String user = userType.getSelection().getActionCommand();


            //if user selects NEW CUSTOMER
            switch (user) {
                case "New Customer":
                    newCustomerAction();
                    break;
                case "Customer":
                    customerAction();
                    break;
                case "Administrator":
                    administratorAction();
                    break;
            }

        });
        selectUserTypeFrame.setVisible(true);
    }

    private void administratorAction() {
        boolean checkAdminUsername = true, checkPassword = true;
        boolean openAdminMenu = false;
        while (checkAdminUsername) {
            Object adminUsername = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter Administrator Username:");

            if (!adminUsername.equals("admin"))//search admin list for admin with matching admin username
            {
                int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Username. Try again?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    checkAdminUsername = true;
                } else if (reply == JOptionPane.NO_OPTION) {
                    createNewUserFrame.dispose();
                    checkAdminUsername = false;
                    checkPassword = false;
                    menuStart();
                }
            } else {
                checkAdminUsername = false;
            }
        }

        while (checkPassword) {
            Object adminPassword = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter Administrator Password;");

            if (!adminPassword.equals("admin11"))//search admin list for admin with matching admin password
            {
                int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect Password. Try again?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {

                } else if (reply == JOptionPane.NO_OPTION) {
                    createNewUserFrame.dispose();
                    checkPassword = false;
                    menuStart();
                }
            } else {
                checkPassword = false;
                openAdminMenu = true;
            }
        }

        if (openAdminMenu) {
            createNewUserFrame.dispose();
            checkAdminUsername = false;
            admin();
        }

    }

    private void customerAction() {
        boolean loop = true, loop2 = true;
        boolean cont = false;
        boolean found = false;
        Customer customer = null;
        while (loop) {
            Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter Customer ID:");

            for (Customer aCustomer : customerList) {

                if (aCustomer.getCustomerID().equals(customerID))//search customer list for matching customer ID
                {
                    found = true;
                    customer = aCustomer;
                }
            }

            if (found == false) {
                int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    loop = true;
                } else if (reply == JOptionPane.NO_OPTION) {
                    selectUserTypeFrame.dispose();
                    loop = false;
                    loop2 = false;
                    menuStart();
                }
            } else {
                loop = false;
            }

        }

        while (loop2) {
            Object customerPassword = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter Customer Password;");

            if (!customer.getPassword().equals(customerPassword))//check if customer password is correct
            {
                int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {

                } else if (reply == JOptionPane.NO_OPTION) {
                    selectUserTypeFrame.dispose();
                    loop2 = false;
                    menuStart();
                }
            } else {
                loop2 = false;
                cont = true;
            }
        }

        if (cont) {
            selectUserTypeFrame.dispose();
            loop = false;
            customer(customer);
        }

    }

    private void newCustomerAction() {
        selectUserTypeFrame.dispose();
        createNewUserFrame.setSize(400, 300);
        createNewUserFrame.setLocation(200, 200);
        createNewUserFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        Container content1 = createNewUserFrame.getContentPane();
        content1.setLayout(new BorderLayout());

        firstNameLabel = new JLabel("First Name:", SwingConstants.RIGHT);
        surnameLabel = new JLabel("Surname:", SwingConstants.RIGHT);
        pPPSLabel = new JLabel("PPS Number:", SwingConstants.RIGHT);
        dOBLabel = new JLabel("Date of birth", SwingConstants.RIGHT);
        firstNameTextField = new JTextField(20);
        surnameTextField = new JTextField(20);
        pPSTextField = new JTextField(20);
        dOBTextField = new JTextField(20);
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(firstNameLabel);
        panel.add(firstNameTextField);
        panel.add(surnameLabel);
        panel.add(surnameTextField);
        panel.add(pPPSLabel);
        panel.add(pPSTextField);
        panel.add(dOBLabel);
        panel.add(dOBTextField);

        panel2 = new JPanel();
        add = new JButton("Add");

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String PPS = pPSTextField.getText();
                String firstName = firstNameTextField.getText();
                String surname = surnameTextField.getText();
                String DOB = dOBTextField.getText();
                String password = "";
                String CustomerID = "ID" + PPS;

                createNewUserFrame.dispose();

                boolean loop = true;
                while (loop) {
                    password = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter 7 character Password:");

                    if (password.length() != 7)//Making sure password is 7 characters
                    {
                        JOptionPane.showMessageDialog(null, null, "Password must be 7 characters long", JOptionPane.OK_OPTION);
                    } else {
                        loop = false;
                    }
                }

                ArrayList<CustomerAccount> accounts = new ArrayList<CustomerAccount>();
                Customer customer = new Customer(PPS, surname, firstName, DOB, CustomerID, password, accounts);
                customerList.add(customer);

                JOptionPane.showMessageDialog(selectUserTypeFrame, "CustomerID = " + CustomerID + "\n Password = " + password, "Customer created.", JOptionPane.INFORMATION_MESSAGE);
                menuStart();
                //f.dispose();
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNewUserFrame.dispose();
                menuStart();
            }
        });

        panel2.add(add);
        panel2.add(cancel);

        content1.add(panel, BorderLayout.CENTER);
        content1.add(panel2, BorderLayout.SOUTH);

        createNewUserFrame.setVisible(true);


    }

    private void addToPanel(JPanel userTypePanel, ButtonGroup userType, String buttonName, String action) {
        JRadioButton radioButton;
        userTypePanel.add(radioButton = new JRadioButton(buttonName));
        radioButton.setActionCommand(action);
        userType.add(radioButton);
    }

    //here finishes menu function

    public void admin() {
        dispose();
        selectUserTypeFrame = new JFrame("Administrator Menu");
        selectUserTypeFrame.setSize(400, 400);
        selectUserTypeFrame.setLocation(200, 200);
        selectUserTypeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        selectUserTypeFrame.setVisible(true);

        JPanel deleteCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteCustomer = new JButton("Delete Customer");
        deleteCustomer.setPreferredSize(new Dimension(250, 20));
        deleteCustomerPanel.add(deleteCustomer);

        JPanel deleteAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton deleteAccount = new JButton("Delete Account");
        deleteAccount.setPreferredSize(new Dimension(250, 20));
        deleteAccountPanel.add(deleteAccount);

        JPanel bankChargesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton bankChargesButton = new JButton("Apply Bank Charges");
        bankChargesButton.setPreferredSize(new Dimension(250, 20));
        bankChargesPanel.add(bankChargesButton);

        JPanel interestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton interestButton = new JButton("Apply Interest");
        interestPanel.add(interestButton);
        interestButton.setPreferredSize(new Dimension(250, 20));

        JPanel editCustomerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editCustomerButton = new JButton("Edit existing Customer");
        editCustomerPanel.add(editCustomerButton);
        editCustomerButton.setPreferredSize(new Dimension(250, 20));

        JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton navigateButton = new JButton("Navigate Customer Collection");
        navigatePanel.add(navigateButton);
        navigateButton.setPreferredSize(new Dimension(250, 20));

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton summaryButton = new JButton("Display Summary Of All Accounts");
        summaryPanel.add(summaryButton);
        summaryButton.setPreferredSize(new Dimension(250, 20));

        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton accountButton = new JButton("Add an Account to a Customer");
        accountPanel.add(accountButton);
        accountButton.setPreferredSize(new Dimension(250, 20));

        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("Exit Admin Menu");
        returnPanel.add(returnButton);

        JLabel label1 = new JLabel("Please select an option");

        content = selectUserTypeFrame.getContentPane();
        content.setLayout(new GridLayout(9, 1));
        content.add(label1);
        content.add(accountPanel);
        content.add(bankChargesPanel);
        content.add(interestPanel);
        content.add(editCustomerPanel);
        content.add(navigatePanel);
        content.add(summaryPanel);
        content.add(deleteCustomerPanel);
        //	content.add(deleteAccountPanel);
        content.add(returnPanel);


        bankChargesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                boolean loop = true;

                boolean found = false;

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(selectUserTypeFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    selectUserTypeFrame.dispose();
                    admin();

                } else {
                    while (loop) {
                        Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer You Wish to Apply Charges to:");

                        for (Customer aCustomer : customerList) {

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

                                admin();
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
                                admin();
                            } else {

                                for (int i = 0; i < customer.getAccounts().size(); i++) {
                                    if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                        acc = customer.getAccounts().get(i);
                                    }
                                }

                                continueButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        String euro = "\u20ac";


                                        if (acc instanceof CustomerDepositAccount) {


                                            JOptionPane.showMessageDialog(selectUserTypeFrame, "25" + euro + " deposit account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                            acc.setBalance(acc.getBalance() - 25);
                                            JOptionPane.showMessageDialog(selectUserTypeFrame, "New balance = " + acc.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                        }

                                        if (acc instanceof CustomerCurrentAccount) {


                                            JOptionPane.showMessageDialog(selectUserTypeFrame, "15" + euro + " current account fee aplied.", "", JOptionPane.INFORMATION_MESSAGE);
                                            acc.setBalance(acc.getBalance() - 25);
                                            JOptionPane.showMessageDialog(selectUserTypeFrame, "New balance = " + acc.getBalance(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                                        }


                                        selectUserTypeFrame.dispose();
                                        admin();
                                    }
                                });

                                returnButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        selectUserTypeFrame.dispose();
                                        menuStart();
                                    }
                                });

                            }
                        }
                    }
                }


            }
        });

        interestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                boolean loop = true;

                boolean found = false;

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(selectUserTypeFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    selectUserTypeFrame.dispose();
                    admin();

                } else {
                    while (loop) {
                        Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer You Wish to Apply Interest to:");

                        for (Customer aCustomer : customerList) {

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

                                admin();
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


                            if (customer.getAccounts().isEmpty()) {
                                JOptionPane.showMessageDialog(selectUserTypeFrame, "This customer has no accounts! \n The admin must add acounts to this customer.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                selectUserTypeFrame.dispose();
                                admin();
                            } else {

                                for (int i = 0; i < customer.getAccounts().size(); i++) {
                                    if (customer.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                                        acc = customer.getAccounts().get(i);
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

                                                acc.setBalance(acc.getBalance() + (acc.getBalance() * (interest / 100)));

                                                JOptionPane.showMessageDialog(selectUserTypeFrame, interest + "% interest applied. \n new balance = " + acc.getBalance() + euro, "Success!", JOptionPane.INFORMATION_MESSAGE);
                                            } else {
                                                JOptionPane.showMessageDialog(selectUserTypeFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                            }


                                        }

                                        selectUserTypeFrame.dispose();
                                        admin();
                                    }
                                });

                                returnButton.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        selectUserTypeFrame.dispose();
                                        menuStart();
                                    }
                                });

                            }
                        }
                    }
                }

            }
        });

        editCustomerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                boolean loop = true;

                boolean found = false;

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(selectUserTypeFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    selectUserTypeFrame.dispose();
                    admin();

                } else {

                    while (loop) {
                        Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter Customer ID:");

                        for (Customer aCustomer : customerList) {

                            if (aCustomer.getCustomerID().equals(customerID)) {
                                found = true;
                                customer = aCustomer;
                            }
                        }

                        if (!found) {
                            int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                loop = true;
                            } else if (reply == JOptionPane.NO_OPTION) {
                                selectUserTypeFrame.dispose();
                                loop = false;

                                admin();
                            }
                        } else {
                            loop = false;
                        }

                    }

                    selectUserTypeFrame.dispose();

                    selectUserTypeFrame.dispose();
                    selectUserTypeFrame = new JFrame("Administrator Menu");
                    selectUserTypeFrame.setSize(400, 300);
                    selectUserTypeFrame.setLocation(200, 200);
                    selectUserTypeFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                            System.exit(0);
                        }
                    });

                    firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
                    surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
                    pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
                    dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
                    customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
                    passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
                    firstNameTextField = new JTextField(20);
                    surnameTextField = new JTextField(20);
                    pPSTextField = new JTextField(20);
                    dOBTextField = new JTextField(20);
                    customerIDTextField = new JTextField(20);
                    passwordTextField = new JTextField(20);

                    JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                    JPanel cancelPanel = new JPanel();

                    textPanel.add(firstNameLabel);
                    textPanel.add(firstNameTextField);
                    textPanel.add(surnameLabel);
                    textPanel.add(surnameTextField);
                    textPanel.add(pPPSLabel);
                    textPanel.add(pPSTextField);
                    textPanel.add(dOBLabel);
                    textPanel.add(dOBTextField);
                    textPanel.add(customerIDLabel);
                    textPanel.add(customerIDTextField);
                    textPanel.add(passwordLabel);
                    textPanel.add(passwordTextField);

                    firstNameTextField.setText(customer.getFirstName());
                    surnameTextField.setText(customer.getSurname());
                    pPSTextField.setText(customer.getPPS());
                    dOBTextField.setText(customer.getDOB());
                    customerIDTextField.setText(customer.getCustomerID());
                    passwordTextField.setText(customer.getPassword());

                    //JLabel label1 = new JLabel("Edit customer details below. The save");


                    JButton saveButton = new JButton("Save");
                    JButton cancelButton = new JButton("Exit");

                    cancelPanel.add(cancelButton, BorderLayout.SOUTH);
                    cancelPanel.add(saveButton, BorderLayout.SOUTH);
                    //	content.removeAll();
                    Container content = selectUserTypeFrame.getContentPane();
                    content.setLayout(new GridLayout(2, 1));
                    content.add(textPanel, BorderLayout.NORTH);
                    content.add(cancelPanel, BorderLayout.SOUTH);

                    selectUserTypeFrame.setContentPane(content);
                    selectUserTypeFrame.setSize(340, 350);
                    selectUserTypeFrame.setLocation(200, 200);
                    selectUserTypeFrame.setVisible(true);
                    selectUserTypeFrame.setResizable(false);

                    saveButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {

                            customer.setFirstName(firstNameTextField.getText());
                            customer.setSurname(surnameTextField.getText());
                            customer.setPPS(pPSTextField.getText());
                            customer.setDOB(dOBTextField.getText());
                            customer.setCustomerID(customerIDTextField.getText());
                            customer.setPassword(passwordTextField.getText());

                            JOptionPane.showMessageDialog(null, "Changes Saved.");
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            selectUserTypeFrame.dispose();

                            admin();
                        }
                    });
                }
            }
        });

        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
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

                for (int a = 0; a < customerList.size(); a++)//For each customer, for each account, it displays each transaction.
                {
                    for (int b = 0; b < customerList.get(a).getAccounts().size(); b++) {
                        acc = customerList.get(a).getAccounts().get(b);
                        for (int c = 0; c < customerList.get(a).getAccounts().get(b).getTransactionList().size(); c++) {

                            textArea.append(acc.getTransactionList().get(c).toString());
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
                        admin();
                    }
                });
            }
        });

        navigateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                selectUserTypeFrame.dispose();

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
                    admin();
                } else {

                    JButton first, previous, next, last, cancel;
                    JPanel gridPanel, buttonPanel, cancelPanel;

                    Container content = getContentPane();

                    content.setLayout(new BorderLayout());

                    buttonPanel = new JPanel();
                    gridPanel = new JPanel(new GridLayout(8, 2));
                    cancelPanel = new JPanel();

                    firstNameLabel = new JLabel("First Name:", SwingConstants.LEFT);
                    surnameLabel = new JLabel("Surname:", SwingConstants.LEFT);
                    pPPSLabel = new JLabel("PPS Number:", SwingConstants.LEFT);
                    dOBLabel = new JLabel("Date of birth", SwingConstants.LEFT);
                    customerIDLabel = new JLabel("CustomerID:", SwingConstants.LEFT);
                    passwordLabel = new JLabel("Password:", SwingConstants.LEFT);
                    firstNameTextField = new JTextField(20);
                    surnameTextField = new JTextField(20);
                    pPSTextField = new JTextField(20);
                    dOBTextField = new JTextField(20);
                    customerIDTextField = new JTextField(20);
                    passwordTextField = new JTextField(20);

                    first = new JButton("First");
                    previous = new JButton("Previous");
                    next = new JButton("Next");
                    last = new JButton("Last");
                    cancel = new JButton("Cancel");

                    firstNameTextField.setText(customerList.get(0).getFirstName());
                    surnameTextField.setText(customerList.get(0).getSurname());
                    pPSTextField.setText(customerList.get(0).getPPS());
                    dOBTextField.setText(customerList.get(0).getDOB());
                    customerIDTextField.setText(customerList.get(0).getCustomerID());
                    passwordTextField.setText(customerList.get(0).getPassword());

                    firstNameTextField.setEditable(false);
                    surnameTextField.setEditable(false);
                    pPSTextField.setEditable(false);
                    dOBTextField.setEditable(false);
                    customerIDTextField.setEditable(false);
                    passwordTextField.setEditable(false);

                    gridPanel.add(firstNameLabel);
                    gridPanel.add(firstNameTextField);
                    gridPanel.add(surnameLabel);
                    gridPanel.add(surnameTextField);
                    gridPanel.add(pPPSLabel);
                    gridPanel.add(pPSTextField);
                    gridPanel.add(dOBLabel);
                    gridPanel.add(dOBTextField);
                    gridPanel.add(customerIDLabel);
                    gridPanel.add(customerIDTextField);
                    gridPanel.add(passwordLabel);
                    gridPanel.add(passwordTextField);

                    buttonPanel.add(first);
                    buttonPanel.add(previous);
                    buttonPanel.add(next);
                    buttonPanel.add(last);

                    cancelPanel.add(cancel);

                    content.add(gridPanel, BorderLayout.NORTH);
                    content.add(buttonPanel, BorderLayout.CENTER);
                    content.add(cancelPanel, BorderLayout.AFTER_LAST_LINE);
                    first.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            position = 0;
                            firstNameTextField.setText(customerList.get(0).getFirstName());
                            surnameTextField.setText(customerList.get(0).getSurname());
                            pPSTextField.setText(customerList.get(0).getPPS());
                            dOBTextField.setText(customerList.get(0).getDOB());
                            customerIDTextField.setText(customerList.get(0).getCustomerID());
                            passwordTextField.setText(customerList.get(0).getPassword());
                        }
                    });

                    previous.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {

                            if (position < 1) {
                                //don't do anything
                            } else {
                                position = position - 1;

                                firstNameTextField.setText(customerList.get(position).getFirstName());
                                surnameTextField.setText(customerList.get(position).getSurname());
                                pPSTextField.setText(customerList.get(position).getPPS());
                                dOBTextField.setText(customerList.get(position).getDOB());
                                customerIDTextField.setText(customerList.get(position).getCustomerID());
                                passwordTextField.setText(customerList.get(position).getPassword());
                            }
                        }
                    });

                    next.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {

                            if (position == customerList.size() - 1) {
                                //don't do anything
                            } else {
                                position = position + 1;

                                firstNameTextField.setText(customerList.get(position).getFirstName());
                                surnameTextField.setText(customerList.get(position).getSurname());
                                pPSTextField.setText(customerList.get(position).getPPS());
                                dOBTextField.setText(customerList.get(position).getDOB());
                                customerIDTextField.setText(customerList.get(position).getCustomerID());
                                passwordTextField.setText(customerList.get(position).getPassword());
                            }


                        }
                    });

                    last.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {

                            position = customerList.size() - 1;

                            firstNameTextField.setText(customerList.get(position).getFirstName());
                            surnameTextField.setText(customerList.get(position).getSurname());
                            pPSTextField.setText(customerList.get(position).getPPS());
                            dOBTextField.setText(customerList.get(position).getDOB());
                            customerIDTextField.setText(customerList.get(position).getCustomerID());
                            passwordTextField.setText(customerList.get(position).getPassword());
                        }
                    });

                    cancel.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            dispose();
                            admin();
                        }
                    });
                    setContentPane(content);
                    setSize(400, 300);
                    setVisible(true);
                }
            }
        });

        accountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                selectUserTypeFrame.dispose();

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(selectUserTypeFrame, "There are no customers yet!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                    selectUserTypeFrame.dispose();
                    admin();
                } else {
                    boolean loop = true;

                    boolean found = false;

                    while (loop) {
                        Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer You Wish to Add an Account to:");

                        for (Customer aCustomer : customerList) {

                            if (aCustomer.getCustomerID().equals(customerID)) {
                                found = true;
                                customer = aCustomer;
                            }
                        }

                        if (found == false) {
                            int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                loop = true;
                            } else if (reply == JOptionPane.NO_OPTION) {
                                selectUserTypeFrame.dispose();
                                loop = false;

                                admin();
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
                                String number = String.valueOf("C" + (customerList.indexOf(customer) + 1) * 10 + (customer.getAccounts().size() + 1));//this simple algorithm generates the account number
                                ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();
                                int randomPIN = (int) (Math.random() * 9000) + 1000;
                                String pin = String.valueOf(randomPIN);

                                ATMCard atm = new ATMCard(randomPIN, valid);

                                CustomerCurrentAccount current = new CustomerCurrentAccount(atm, number, balance, transactionList);

                                customer.getAccounts().add(current);
                                JOptionPane.showMessageDialog(selectUserTypeFrame, "Account number = " + number + "\n PIN = " + pin, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                                selectUserTypeFrame.dispose();
                                admin();
                            }

                            if (account.equals("Deposit Account")) {
                                //create deposit account

                                double balance = 0, interest = 0;
                                String number = String.valueOf("D" + (customerList.indexOf(customer) + 1) * 10 + (customer.getAccounts().size() + 1));//this simple algorithm generates the account number
                                ArrayList<AccountTransaction> transactionList = new ArrayList<AccountTransaction>();

                                CustomerDepositAccount deposit = new CustomerDepositAccount(interest, number, balance, transactionList);

                                customer.getAccounts().add(deposit);
                                JOptionPane.showMessageDialog(selectUserTypeFrame, "Account number = " + number, "Account created.", JOptionPane.INFORMATION_MESSAGE);

                                selectUserTypeFrame.dispose();
                                admin();
                            }

                        }
                    }
                }
            }
        });

        deleteCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean found = true, loop = true;

                if (customerList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There are currently no customers to display. ");
                    dispose();
                    admin();
                } else {
                    {
                        Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer You Wish to Delete:");

                        for (Customer aCustomer : customerList) {

                            if (aCustomer.getCustomerID().equals(customerID)) {
                                found = true;
                                customer = aCustomer;
                                loop = false;
                            }
                        }

                        if (found == false) {
                            int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                loop = true;
                            } else if (reply == JOptionPane.NO_OPTION) {
                                selectUserTypeFrame.dispose();
                                loop = false;

                                admin();
                            }
                        } else {
                            if (customer.getAccounts().size() > 0) {
                                JOptionPane.showMessageDialog(selectUserTypeFrame, "This customer has accounts. \n You must delete a customer's accounts before deleting a customer ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                customerList.remove(customer);
                                JOptionPane.showMessageDialog(selectUserTypeFrame, "Customer Deleted ", "Success.", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }


                    }
                }
            }
        });

        deleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean found = true, loop = true;


                {
                    Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Customer ID of Customer from which you wish to delete an account");

                    for (Customer aCustomer : customerList) {

                        if (aCustomer.getCustomerID().equals(customerID)) {
                            found = true;
                            customer = aCustomer;
                            loop = false;
                        }
                    }

                    if (found == false) {
                        int reply = JOptionPane.showConfirmDialog(null, null, "User not found. Try again?", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            loop = true;
                        } else if (reply == JOptionPane.NO_OPTION) {
                            selectUserTypeFrame.dispose();
                            loop = false;

                            admin();
                        }
                    } else {
                        //Here I would make the user select a an account to delete from a combo box. If the account had a balance of 0 then it would be deleted. (I do not have time to do this)
                    }


                }
            }

        });
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                selectUserTypeFrame.dispose();
                menuStart();
            }
        });
    }

    public void customer(Customer e1) {
        selectUserTypeFrame = new JFrame("Customer Menu");
        e = e1;
        selectUserTypeFrame.setSize(400, 300);
        selectUserTypeFrame.setLocation(200, 200);
        selectUserTypeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        selectUserTypeFrame.setVisible(true);

        if (e.getAccounts().size() == 0) {
            JOptionPane.showMessageDialog(selectUserTypeFrame, "This customer does not have any accounts yet. \n An admin must create an account for this customer \n for them to be able to use customer functionality. ", "Oops!", JOptionPane.INFORMATION_MESSAGE);
            selectUserTypeFrame.dispose();
            menuStart();
        } else {
            JPanel buttonPanel = new JPanel();
            JPanel boxPanel = new JPanel();
            JPanel labelPanel = new JPanel();

            JLabel label = new JLabel("Select Account:");
            labelPanel.add(label);

            JButton returnButton = new JButton("Return");
            buttonPanel.add(returnButton);
            JButton continueButton = new JButton("Continue");
            buttonPanel.add(continueButton);

            JComboBox<String> box = new JComboBox<String>();
            for (int i = 0; i < e.getAccounts().size(); i++) {
                box.addItem(e.getAccounts().get(i).getNumber());
            }


            for (int i = 0; i < e.getAccounts().size(); i++) {
                if (e.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                    acc = e.getAccounts().get(i);
                }
            }


            boxPanel.add(box);
            content = selectUserTypeFrame.getContentPane();
            content.setLayout(new GridLayout(3, 1));
            content.add(labelPanel);
            content.add(boxPanel);
            content.add(buttonPanel);

            returnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    selectUserTypeFrame.dispose();
                    menuStart();
                }
            });

            continueButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {

                    selectUserTypeFrame.dispose();

                    selectUserTypeFrame = new JFrame("Customer Menu");
                    selectUserTypeFrame.setSize(400, 300);
                    selectUserTypeFrame.setLocation(200, 200);
                    selectUserTypeFrame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                            System.exit(0);
                        }
                    });
                    selectUserTypeFrame.setVisible(true);

                    JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JButton statementButton = new JButton("Display Bank Statement");
                    statementButton.setPreferredSize(new Dimension(250, 20));

                    statementPanel.add(statementButton);

                    JPanel lodgementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JButton lodgementButton = new JButton("Lodge money into account");
                    lodgementPanel.add(lodgementButton);
                    lodgementButton.setPreferredSize(new Dimension(250, 20));

                    JPanel withdrawalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JButton withdrawButton = new JButton("Withdraw money from account");
                    withdrawalPanel.add(withdrawButton);
                    withdrawButton.setPreferredSize(new Dimension(250, 20));

                    JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    JButton returnButton = new JButton("Exit Customer Menu");
                    returnPanel.add(returnButton);

                    JLabel label1 = new JLabel("Please select an option");

                    content = selectUserTypeFrame.getContentPane();
                    content.setLayout(new GridLayout(5, 1));
                    content.add(label1);
                    content.add(statementPanel);
                    content.add(lodgementPanel);
                    content.add(withdrawalPanel);
                    content.add(returnPanel);

                    statementButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
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
                            //	content.add(label1);
                            content.add(textPanel);
                            //content.add(returnPanel);

                            returnButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent ae) {
                                    selectUserTypeFrame.dispose();
                                    customer(e);
                                }
                            });
                        }
                    });

                    lodgementButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            boolean loop = true;
                            boolean on = true;
                            double balance = 0;

                            if (acc instanceof CustomerCurrentAccount) {
                                int count = 3;
                                int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
                                loop = true;

                                while (loop) {
                                    if (count == 0) {
                                        JOptionPane.showMessageDialog(selectUserTypeFrame, "Pin entered incorrectly 3 times. ATM card locked.", "Pin", JOptionPane.INFORMATION_MESSAGE);
                                        ((CustomerCurrentAccount) acc).getAtm().setValid(false);
                                        customer(e);
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
                            if (on == true) {
                                String balanceTest = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter amount you wish to lodge:");//the isNumeric method tests to see if the string entered was numeric.
                                if (isNumeric(balanceTest)) {

                                    balance = Double.parseDouble(balanceTest);
                                    loop = false;


                                } else {
                                    JOptionPane.showMessageDialog(selectUserTypeFrame, "You must enter a numerical value!", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                }


                                String euro = "\u20ac";
                                acc.setBalance(acc.getBalance() + balance);
                                // String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                                Date date = new Date();
                                String date2 = date.toString();
                                String type = "Lodgement";
                                double amount = balance;


                                AccountTransaction transaction = new AccountTransaction(date2, type, amount);
                                acc.getTransactionList().add(transaction);

                                JOptionPane.showMessageDialog(selectUserTypeFrame, balance + euro + " added do you account!", "Lodgement", JOptionPane.INFORMATION_MESSAGE);
                                JOptionPane.showMessageDialog(selectUserTypeFrame, "New balance = " + acc.getBalance() + euro, "Lodgement", JOptionPane.INFORMATION_MESSAGE);
                            }

                        }
                    });

                    withdrawButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            boolean loop = true;
                            boolean on = true;
                            double withdraw = 0;

                            if (acc instanceof CustomerCurrentAccount) {
                                int count = 3;
                                int checkPin = ((CustomerCurrentAccount) acc).getAtm().getPin();
                                loop = true;

                                while (loop) {
                                    if (count == 0) {
                                        JOptionPane.showMessageDialog(selectUserTypeFrame, "Pin entered incorrectly 3 times. ATM card locked.", "Pin", JOptionPane.INFORMATION_MESSAGE);
                                        ((CustomerCurrentAccount) acc).getAtm().setValid(false);
                                        customer(e);
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
                            if (on == true) {
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
                                if (withdraw > acc.getBalance()) {
                                    JOptionPane.showMessageDialog(selectUserTypeFrame, "Insufficient funds.", "Oops!", JOptionPane.INFORMATION_MESSAGE);
                                    withdraw = 0;
                                }

                                String euro = "\u20ac";
                                acc.setBalance(acc.getBalance() - withdraw);
                                //recording transaction:
                                //		String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                                Date date = new Date();
                                String date2 = date.toString();

                                String type = "Withdraw";
                                double amount = withdraw;


                                AccountTransaction transaction = new AccountTransaction(date2, type, amount);
                                acc.getTransactionList().add(transaction);


                                JOptionPane.showMessageDialog(selectUserTypeFrame, withdraw + euro + " withdrawn.", "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                                JOptionPane.showMessageDialog(selectUserTypeFrame, "New balance = " + acc.getBalance() + euro, "Withdraw", JOptionPane.INFORMATION_MESSAGE);
                            }


                        }
                    });

                    returnButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            selectUserTypeFrame.dispose();
                            menuStart();
                        }
                    });
                }
            });
        }
    }

    public static boolean isNumeric(String str)  // a method that tests if a string is numeric
    {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

