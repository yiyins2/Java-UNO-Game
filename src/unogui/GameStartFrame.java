package unogui;

import unocontroller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStartFrame {
    public JFrame frame;
    public JLabel titleLabel;
    public JLabel numPlayerMessageLabel;
    public JTextField numPlayerInput;
    public JLabel numAIPlayerMessageLabel;
    public JTextField numAIPlayerInput;
    public JRadioButton baselineAIBtn;
    public JRadioButton strategicAIBtn;
    public ButtonGroup AIGroup;
    public JButton startButton;
    public Container pane;

    // citation https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/BoxLayoutDemoProject/src/layout/BoxLayoutDemo.java
    public GameStartFrame() {
        frame = new JFrame("UNO");
        pane = frame.getContentPane();

        frame.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.setSize(800, 600);

        titleLabel = new JLabel("UNO");
        titleLabel.setFont(titleLabel.getFont().deriveFont(64f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // num players title
        numPlayerMessageLabel = new JLabel("Please enter the number of players:");
        numPlayerMessageLabel.setFont(titleLabel.getFont().deriveFont(18f));
        numPlayerMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // num players text field
        numPlayerInput = new JTextField();
        numPlayerInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        numPlayerInput.setFont(titleLabel.getFont().deriveFont(18f));
        // citation https://stackoverflow.com/questions/2709220/how-do-i-keep-jtextfields-in-a-java-swing-boxlayout-from-expanding
        numPlayerInput.setMaximumSize(
                new Dimension(300, numPlayerInput.getPreferredSize().height));

        // num ai players title
        numAIPlayerMessageLabel = new JLabel("Please enter the number of AI players:");
        numAIPlayerMessageLabel.setFont(titleLabel.getFont().deriveFont(18f));
        numAIPlayerMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // num ai players text field
        numAIPlayerInput = new JTextField();
        numAIPlayerInput.setFont(titleLabel.getFont().deriveFont(18f));
        numAIPlayerInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        // citation https://stackoverflow.com/questions/2709220/how-do-i-keep-jtextfields-in-a-java-swing-boxlayout-from-expanding
        numAIPlayerInput.setMaximumSize(
                new Dimension(300, numAIPlayerInput.getPreferredSize().height));

        // start game button
        startButton = new JButton("Start Game");
        startButton.setFont(titleLabel.getFont().deriveFont(18f));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        baselineAIBtn = new JRadioButton("Baseline AI");
        baselineAIBtn.setFont(titleLabel.getFont().deriveFont(18f));
        strategicAIBtn = new JRadioButton("Strategic AI");
        strategicAIBtn.setFont(titleLabel.getFont().deriveFont(18f));
        AIGroup = new ButtonGroup();
        AIGroup.add(baselineAIBtn);
        AIGroup.add(strategicAIBtn);

        // citation https://stackoverflow.com/questions/46541062/how-to-add-vertical-space-between-jbutton-in-boxlayout
        pane.add(Box.createVerticalStrut(50));
        pane.add(titleLabel);
        pane.add(Box.createVerticalStrut(50));
        pane.add(numPlayerMessageLabel);
        pane.add(numPlayerInput);
        pane.add(numAIPlayerMessageLabel);
        pane.add(numAIPlayerInput);
        pane.add(baselineAIBtn);
        pane.add(strategicAIBtn);
        pane.add(Box.createVerticalStrut(20));
        pane.add(startButton);

        /* if valid input, dispose current frame and open game frame
           if invalid input, pop up invalid input message window
         */
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numHumanPlayers = getNumHumanPlayers();
                int numAIPlayers = getNumAIPlayers();
                int numOfPlayers = numHumanPlayers + numAIPlayers;
                boolean okToStart = 2 <= numOfPlayers && numOfPlayers <= 10;
                if (okToStart) {
                    frame.dispose();
                    if (baselineAIBtn.isSelected()) {
                        new GameController(numHumanPlayers, numAIPlayers, true);
                    } else {
                        new GameController(numHumanPlayers, numAIPlayers, false);
                    }
                } else {
                    invalidMessage();
                }
            }
        });
        frame.setVisible(true);
    }

    /**
     * @return num of human players
     */
    public int getNumHumanPlayers() {
        return Integer.parseInt(numPlayerInput.getText());
    }

    /**
     * @return num of ai players
     */
    public int getNumAIPlayers() {
        return Integer.parseInt(numAIPlayerInput.getText());
    }

    /**
     * raise a message dialog for invalid num of players
     */
    public void invalidMessage() {
        JOptionPane.showMessageDialog(frame, "Invalid number of players: total number of players should between 2 and 10 inclusive.");
    }

    /**
     * for testing
     */
    public static void main(String[] args) {
        new GameStartFrame();
    }
}
