package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {


    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    JButton button;

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

        super("Personal Budget Manager");
        initializeGraphics();
        addButtons();
    }

    public void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addButtons() {
        button = new JButton("Click me");

    }

}
