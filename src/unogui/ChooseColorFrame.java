package unogui;

import unogame.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// citation https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/GridBagLayoutDemoProject/src/layout/GridBagLayoutDemo.java
public class ChooseColorFrame {
    public JFrame frame;
    public JLabel messageLabel;
    public JPanel messagePanel;
    public JPanel buttonPanel;
    public Container pane;
    public JButton redButton;
    public JButton greenButton;
    public JButton blueButton;
    public JButton yellowButton;

    private Game game;

    public ChooseColorFrame(Game game) {
        this.game = game;
        frame = new JFrame("UNO");
        pane = frame.getContentPane();
        frame.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        messagePanel = new JPanel();
        buttonPanel = new JPanel();

        pane.add(messagePanel);
        pane.add(buttonPanel);

        buttonPanel.setLayout(new GridLayout(2, 2));

        frame.setSize(800, 600);

        // choose color title
        messagePanel.setMaximumSize(new Dimension(800, 300));
        messageLabel = new JLabel("Choose the color for your wild card");
        messageLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        messageLabel.setFont(messageLabel.getFont().deriveFont(32f));
        messagePanel.add(messageLabel);

        // red button
        redButton = new JButton("RED");
        redButton.setForeground(Color.RED);
        redButton.setFont(redButton.getFont().deriveFont(32f));
        buttonPanel.add(redButton);
        redButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.declareColor(unocard.Color.RED);
                frame.dispose();
            }
        });

        // green button
        greenButton = new JButton("GREEN");
        greenButton.setForeground(Color.GREEN);
        greenButton.setFont(greenButton.getFont().deriveFont(32f));
        buttonPanel.add(greenButton);
        greenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.declareColor(unocard.Color.GREEN);
                frame.dispose();
            }
        });

        // blue button
        blueButton = new JButton("BLUE");
        blueButton.setForeground(Color.BLUE);
        blueButton.setFont(blueButton.getFont().deriveFont(32f));
        buttonPanel.add(blueButton);
        blueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.declareColor(unocard.Color.BLUE);
                frame.dispose();
            }
        });

        // yellow button
        yellowButton = new JButton("YELLOW");
        yellowButton.setForeground(Color.YELLOW);
        yellowButton.setFont(yellowButton.getFont().deriveFont(32f));
        buttonPanel.add(yellowButton);
        yellowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.declareColor(unocard.Color.YELLOW);
                frame.dispose();
            }
        });
        frame.setVisible(true);
    }

    /**
     * for testing
     */
    public static void main(String[] args) { new ChooseColorFrame(new Game(2, 0, true)); }
}
