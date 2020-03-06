package excercise.listeners;

import excercise.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnListener implements ActionListener {

    private Menu parent;
    private JFrame selectUserTypeFrame;

    public ReturnListener(Menu context, JFrame frame) {
        this.parent = context;
        this.selectUserTypeFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectUserTypeFrame.dispose();
        parent.menuStart();
    }
}
