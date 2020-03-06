package excercise;


import excercise.listeners.*;

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

import java.util.List;

public class Menu extends JFrame {

    public ArrayList<Customer> customerList = new ArrayList<>();
    private int position = 0;
    public Customer customer = null;
    public CustomerAccount customerAccount = new CustomerAccount();
    private JFrame selectUserTypeFrame, createNewUserFrame;
    public JLabel firstNameLabel;
    public JLabel surnameLabel;
    public JLabel pPPSLabel;
    public JLabel dOBLabel;
    public JTextField firstNameTextField, surnameTextField, pPSTextField, dOBTextField;
    public JLabel customerIDLabel;
    public JLabel passwordLabel;
    public JTextField customerIDTextField, passwordTextField;
    private Container content;
    private Customer cust;
    private Menu context = this;
    private  final ButtonGroup userType = new ButtonGroup();


    public static void main(String[] args) {
        Menu driver = new Menu();

        //populating customerList for testing purpose
        ArrayList<CustomerAccount> ca = new ArrayList<>();
        Customer c1 = new Customer("1234", "Joe", "Bloggs", "11061998", "ID1235", "1234", ca);
        c1.getAccounts().add(new CustomerCurrentAccount(new ATMCard(1234, true), "C1234", 1000.0, new ArrayList<AccountTransaction>()));
        c1.getAccounts().add((new CustomerDepositAccount(1.7, "D1234", 2000.0, new ArrayList<AccountTransaction>())));
        driver.customerList.add(c1);


        ArrayList<CustomerAccount> ca2 = new ArrayList<>();
        ca2.add(new CustomerCurrentAccount(new ATMCard(1235, true), "C1235", 1000.0, new ArrayList<AccountTransaction>()));
        driver.customerList.add(new Customer("1235", "Mark", "Bloggs", "11061998", "ID1236", "1234", ca2));

        driver.menuStart();

    }

    public void menuStart() {
		   /*The menuStart method asks the user if they are a new customer, an existing customer or an admin.
		    It will then start the create customer process if they are a new customer,
		     or will ask them to log in if they are an existing customer or admin.*/

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
                switch (reply) {
                    case JOptionPane.YES_OPTION:
                        checkAdminUsername = true;
                        break;
                    case JOptionPane.NO_OPTION:
                        createNewUserFrame.dispose();
                        checkAdminUsername = false;
                        checkPassword = false;
                        menuStart();
                        break;
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
                switch (reply) {
                    case JOptionPane.YES_OPTION:
                        break;
                    case JOptionPane.NO_OPTION:
                        createNewUserFrame.dispose();
                        checkPassword = false;
                        menuStart();
                        break;
                }
            } else {
                checkPassword = false;
                openAdminMenu = true;
            }
        }

        if (openAdminMenu) {
            createNewUserFrame.dispose();
            admin();
        }

    }

    private void customerAction() {
        boolean loop = true, loop2 = true;
        boolean cont = false;
        boolean found = false;
        Customer existingCustomer = null;
        while (loop) {
            Object customerID = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter Customer ID:");

            for (Customer aCustomer : customerList) {

                if (aCustomer.getCustomerID().equals(customerID))//search customer list for matching customer ID
                {
                    found = true;
                    existingCustomer = aCustomer;
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
                        loop2 = false;
                        menuStart();
                        break;
                }
            } else {
                loop = false;
            }

        }

        while (loop2) {
            Object customerPassword = JOptionPane.showInputDialog(selectUserTypeFrame, "Enter Customer Password;");

            if (!existingCustomer.getPassword().equals(customerPassword))//check if customer password is correct
            {
                int reply = JOptionPane.showConfirmDialog(null, null, "Incorrect password. Try again?", JOptionPane.YES_NO_OPTION);
                switch (reply) {
                    case JOptionPane.YES_OPTION:
                        break;
                    case JOptionPane.NO_OPTION:
                        selectUserTypeFrame.dispose();
                        loop2 = false;
                        menuStart();
                        break;
                }
            } else {
                loop2 = false;
                cont = true;
            }
        }

        if (cont) {
            selectUserTypeFrame.dispose();
            customer(existingCustomer);
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

        JPanel panelAddCustomer = new JPanel();
        JButton add = new JButton("Add");

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
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNewUserFrame.dispose();
                menuStart();
            }
        });

        panelAddCustomer.add(add);
        panelAddCustomer.add(cancel);

        content1.add(panel, BorderLayout.CENTER);
        content1.add(panelAddCustomer, BorderLayout.SOUTH);

        createNewUserFrame.setVisible(true);

    }

    private void addToPanel(JPanel userTypePanel, ButtonGroup userType, String buttonName, String action) {
        JRadioButton radioButton;
        userTypePanel.add(radioButton = new JRadioButton(buttonName));
        radioButton.setActionCommand(action);
        userType.add(radioButton);
    }


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


        List<JPanel> panels2 = new ArrayList<>();

        panels2.add(createPanel("Add an Account to a Customer", new AddAcountListener(context, selectUserTypeFrame),true, FlowLayout.LEFT));
        panels2.add(createPanel("Apply Bank Charges", new BankChargesListener(context, customer, selectUserTypeFrame), true, FlowLayout.LEFT));
        panels2.add(createPanel("Apply Interest", new InterestListener(context, selectUserTypeFrame),true, FlowLayout.LEFT));
        panels2.add(createPanel("Edit existing Customer", new EditExistingCustomerListener(context, selectUserTypeFrame, customer), true, FlowLayout.LEFT));


        JPanel navigatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton navigateButton = new JButton("Navigate Customer Collection");
        navigatePanel.add(navigateButton);
        navigateButton.setPreferredSize(new Dimension(250, 20));
        panels2.add(navigatePanel);
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

                    setCustomerDetails(0);

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
                            setCustomerDetails(0);
                        }
                    });

                    previous.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {

                            //changed if statement so there is no empty body

                            if (position >= 1) {
                                position = position - 1;

                                setCustomerDetails(position);
                            }
                        }
                    });

                    next.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {

                            if (position != customerList.size() - 1) {
                                position = position + 1;
                                setCustomerDetails(position);
                            }

                        }
                    });

                    last.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            position = customerList.size() - 1;
                            setCustomerDetails(position);
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

        panels2.add(createPanel("Display Summary Of All Accounts", new DisplayAccountsSummaryListener(context,selectUserTypeFrame,content), true, FlowLayout.LEFT));
        panels2.add(createPanel("Delete Customer",  new DeleteCustomerListener(context, selectUserTypeFrame,customer), true, FlowLayout.LEFT));
        //panels2.add(createPanel("Delete Account", new DeleteAccountListener(context, selectUserTypeFrame,customer), true, FlowLayout.LEFT));
        panels2.add(createPanel("Exit Admin Menu", new ReturnListener(context,selectUserTypeFrame), false, FlowLayout.RIGHT));

        addPanelsToContent(new GridLayout(9, 1), panels2,"Please select an option");


    }

    private void setCustomerDetails(int position) {

        firstNameTextField.setText(customerList.get(position).getFirstName());
        surnameTextField.setText(customerList.get(position).getSurname());
        pPSTextField.setText(customerList.get(position).getPPS());
        dOBTextField.setText(customerList.get(position).getDOB());
        customerIDTextField.setText(customerList.get(position).getCustomerID());
        passwordTextField.setText(customerList.get(position).getPassword());
    }

    public void customer(Customer existingCustomer) {

        selectUserTypeFrame = new JFrame("Customer Menu");
        cust = existingCustomer;
        selectUserTypeFrame.setSize(400, 300);
        selectUserTypeFrame.setLocation(200, 200);
        selectUserTypeFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        selectUserTypeFrame.setVisible(true);



        if (cust.getAccounts().size() == 0) {
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

            //JComboBox<String> box =

            boxPanel.add(getStringJComboBox());
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


                    List<JPanel> panels = new ArrayList<>();

                    panels.add(createPanel("Display Bank Statement", new StatementListener(context, selectUserTypeFrame,  customerAccount, content, cust), true, FlowLayout.LEFT));

                    panels.add(createPanel("Lodge money into account", new LodgementListener(context, selectUserTypeFrame, cust), true, FlowLayout.LEFT));

                    panels.add(createPanel("Withdraw money from account", new WithdrawListener(context, selectUserTypeFrame, customerAccount, cust), true, FlowLayout.LEFT));

                    panels.add(createPanel("Exit Customer Menu", new ReturnListener(context, selectUserTypeFrame), false, FlowLayout.RIGHT));

                    addPanelsToContent(new GridLayout(5, 1), panels, "Please select an option");

                }
            });
        }
    }

    private JComboBox<String> getStringJComboBox() {
        JComboBox<String> box = new JComboBox<String>();
        for (int i = 0; i < cust.getAccounts().size(); i++) {
            box.addItem(cust.getAccounts().get(i).getNumber());
        }
        for (int i = 0; i < cust.getAccounts().size(); i++) {
            if (cust.getAccounts().get(i).getNumber() == box.getSelectedItem()) {
                customerAccount = cust.getAccounts().get(i);
            }
        }
        return box;
    }

    private void addPanelsToContent(GridLayout layout, List<JPanel> panels, String title) {
        JLabel label1 = new JLabel(title);
        content = selectUserTypeFrame.getContentPane();
        content.setLayout(layout);
        content.add(label1);

        for (JPanel p : panels) {
            content.add(p);
        }
    }

    private JPanel createPanel(String title, ActionListener listener, boolean hasDimension, int align) {
        Dimension size = new Dimension(250, 20);
        JPanel panel = new JPanel(new FlowLayout(align));
        JButton button = new JButton(title);
        panel.add(button);
        if(listener!=null) {
            button.addActionListener(listener);
        }

        if (hasDimension) {
            button.setPreferredSize(size);
        }
        return panel;
    }


    //TODO DO THE SAME (CREATE NEW CLASS FOR EACH LISTENER(ALMOST))

    //TODO MOVE ALL THE COMPUTATION AND CALCULATIONS TO ANOTHER CLASS(service class) for account
    //don't forget to use singleton for this
}

