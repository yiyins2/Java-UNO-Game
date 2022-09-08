package unogui;

import unocontroller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinnerFrame {
    public JFrame frame;
    public JButton restartBtn;
    public JLabel winnerLabel;
    public JPanel winnerPanel;
    public JPanel btnPanel;
    public Container pane;

    public WinnerFrame(int winnerID) {
        frame = new JFrame();
        frame.setSize(800, 600);

        pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        winnerPanel = new JPanel();
        btnPanel = new JPanel();

        pane.add(winnerPanel);
        pane.add(btnPanel);

        // winner label
        winnerLabel = new JLabel("The winner is player " + winnerID);
        winnerLabel.setFont(winnerLabel.getFont().deriveFont(32f));
        winnerLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        winnerPanel.add(winnerLabel);

        // start game label
        restartBtn = new JButton("Start A New Game");
        restartBtn.setFont(restartBtn.getFont().deriveFont(24f));
        btnPanel.add(restartBtn);

        // dispose current frame and restart a game
        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new GameStartFrame();
            }
        });

        frame.setVisible(true);
    }

    /**
     * for testing
     */
    public static void main(String[] args) {
        WinnerFrame frame = new WinnerFrame(2);
    }
}
