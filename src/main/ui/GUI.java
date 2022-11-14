package ui;

import model.BudgetProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {


    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JDesktopPane desktop;
    private JInternalFrame controlPanel;
    private BudgetProfile bp;


    public GUI() {

//        JFrame frame = new JFrame();
//        JPanel panel = new JPanel();
//        JButton button = new JButton("Click me");
//        JLabel label = new JLabel("Number of Clicks: 0");
//
//        panel.setBorder(BorderFactory.createEmptyBorder(300, 300, 100, 300));
//        panel.setLayout(new GridLayout(0,1));
//
//        frame.add(panel, BorderLayout.CENTER);
//        frame.add(button);
//        frame.add(label);
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setTitle("Out GUI");
//        frame.pack();
//        frame.setVisible(true);

        //super("Personal Budget Manager");

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        controlPanel = new JInternalFrame("Control Panel", false, false, false, false);
        controlPanel.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("Personal Budget Manager");
        setSize(WIDTH, HEIGHT);

//        initializeGraphics();
        addButtonPanel();
        addMenu();
    }

//    public void initializeGraphics() {
//        setLayout(new BorderLayout());
//        setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }

    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,2));
        buttonPanel.add(new JButton("testing"));
//        buttonPanel.add(new JButton(new RemoveCodeAction()));
//        buttonPanel.add(new JButton(new ArmAction()));
//        buttonPanel.add(new JButton(new DisarmAction()));
//        buttonPanel.add(new JButton(new AddSensorAction()));
//        buttonPanel.add(new JButton(new ClearLogAction()));
//        buttonPanel.add(new JButton(new PrintLogAction()));

    }

    public void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu sensorMenu = new JMenu("Sensor");
        sensorMenu.setMnemonic('S');
        addMenuItem(sensorMenu, new AddSensorAction(),
                KeyStroke.getKeyStroke("control S"));
        menuBar.add(sensorMenu);

    }

    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            GUI.this.requestFocusInWindow();
        }
    }

}
