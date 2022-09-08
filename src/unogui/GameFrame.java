package unogui;

import unogame.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameFrame {
    public JFrame frame;
    public JPanel streakPanel;
    public JPanel pilesPanel;
    public JPanel playerPanel;
    public JPanel handPanel;
    public JPanel controlPanel;
    public Container pane;

    public JLabel discardPileLabel;
    public JLabel streakLabel;
    public JLabel playerLabel;
    public JLabel roundLabel;
    public JLabel colorLabel;
    public JButton hideShowCardBtn;
    public JButton skipBtn;
    public JButton aiPlayBtn;
    public ArrayList<JButton> hand;

    public Image cardBackImgSmall;
    public Image cardBackImgBig;

    public ActionListener playCardListener;

    public GameFrame() {
        frame = new JFrame();
        streakPanel = new JPanel();
        pilesPanel = new JPanel();
        playerPanel = new JPanel();
        handPanel = new JPanel();
        controlPanel = new JPanel();

        frame.setSize(1280, 720);
        pane = frame.getContentPane();
        frame.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // card back image
        try {
            Image cardBackImg = ImageIO.read(getClass().getResource("/cardImages/back.png"));
            cardBackImgBig = cardBackImg.getScaledInstance(121, 181, java.awt.Image.SCALE_SMOOTH) ;
            cardBackImgSmall = cardBackImg.getScaledInstance(60, 90, java.awt.Image.SCALE_SMOOTH) ;
        } catch (Exception ex) {
            System.out.println("Image not found");
        }

        pane.add(streakPanel);
        pane.add(pilesPanel);
        pane.add(playerPanel);
        pane.add(handPanel);
        pane.add(controlPanel);

        pilesPanel.setLayout(new GridLayout(1,2));
        controlPanel.setLayout(new GridLayout(1,3));
        playerPanel.setLayout(new GridLayout(3, 1));

        // streak label
        streakLabel = new JLabel("Current draw two and four streak: 0");
        streakLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        streakLabel.setFont(streakLabel.getFont().deriveFont(20f));
        streakPanel.add(streakLabel);

        // draw pile
        JLabel drawPileLabel = new JLabel();
        drawPileLabel.setIcon(new ImageIcon(cardBackImgBig));

        drawPileLabel.setHorizontalAlignment(JLabel.CENTER);
        pilesPanel.add(drawPileLabel);

        // discard pile label
        discardPileLabel = new JLabel();
        discardPileLabel.setHorizontalAlignment(JLabel.CENTER);
        pilesPanel.add(discardPileLabel);

        // current player id label
        playerLabel = new JLabel("Current Player ID: 0");
        playerPanel.add(playerLabel);
        playerLabel.setHorizontalAlignment(JLabel.CENTER);
        playerLabel.setFont(playerLabel.getFont().deriveFont(20f));

        // round info label
        roundLabel = new JLabel();
        roundLabel.setHorizontalAlignment(JLabel.CENTER);
        playerPanel.add(roundLabel);
        roundLabel.setFont(roundLabel.getFont().deriveFont(20f));

        // choose color info label
        colorLabel = new JLabel();
        colorLabel.setHorizontalAlignment(JLabel.CENTER);
        playerPanel.add(colorLabel);
        colorLabel.setFont(colorLabel.getFont().deriveFont(20f));

        // hide/shwo card button
        hideShowCardBtn = new JButton("Show Cards");
        controlPanel.add(hideShowCardBtn);
        skipBtn = new JButton("Skip This Round");

        // ai play button
        controlPanel.add(skipBtn);
        aiPlayBtn = new JButton("AI Play");
        controlPanel.add(aiPlayBtn);
        aiPlayBtn.setEnabled(false);

        frame.setVisible(true);
    }

    /**
     * reset the streak label with new streak
     * @param streak the streak to display
     */
    public void setStreakLabel(int streak) {
        streakLabel.setText("Current draw two and four streak: " + streak);
    }

    /**
     * enable hide/show card button and skip button
     * @param play human or not
     */
    public void setHumanPlay(boolean play) {
        hideShowCardBtn.setEnabled(play);
        skipBtn.setEnabled(play);
    }

    /**
     * enable ai play button
     * @param play ai or not
     */
    public void setAiPlayBtn(boolean play) {
        aiPlayBtn.setEnabled(play);
    }

    /**
     * reset top card of discard pile
     * @param image image of the top card
     */
    public void setDiscardPileLabel(String image) {
        try {
            Image cardBackImg = ImageIO.read(getClass().getResource(image));
            Image cardBackImgReszied = cardBackImg.getScaledInstance(121, 181, java.awt.Image.SCALE_SMOOTH) ;
            discardPileLabel.setIcon(new ImageIcon(cardBackImgReszied));
        } catch (Exception ex) {
            System.out.println("Image not found");
        }
        pane.revalidate();
    }

    /**
     * reset player id label
     * @param playerID the new player id to display
     */
    public void setPlayerLabel(int playerID) {
        playerLabel.setText("Current Player ID: " + playerID);
    }

    /**
     * reset round info label
     * @param message new round info to display
     */
    public void setRoundLabel(String message) { roundLabel.setText(message); }

    /**
     * get round info
     * @return current round info
     */
    public String getRoundLabel() { return roundLabel.getText(); }

    /**
     * reset choose color info label
     * @param message new choose color to display
     */
    public void setColorLabel(String message) { colorLabel.setText(message); }

    /**
     * get choose color info
     * @return current choose color info
     */
    public String getColorLabel() { return colorLabel.getText(); }

    /**
     * hide hand
     * @param size hand size to display cards' back
     */
    public void hideHand(int size) {
        handPanel.removeAll();
        for (int i = 0; i < size; i++) {
            JLabel label = new JLabel();
            label.setIcon(new ImageIcon(cardBackImgSmall));
            handPanel.add(label);
        }
        pane.revalidate();
        pane.repaint();
    }

    /**
     * hand buttons
     * @param images an array of images of the hand to display
     */
    public void addHand(String[] images) {
        handPanel.removeAll();
        hand = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            JButton btn = new JButton();
            hand.add(btn);
            try {
                Image cardBackImg = ImageIO.read(getClass().getResource(images[i]));
                Image cardBackImgReszied = cardBackImg.getScaledInstance(60, 90, java.awt.Image.SCALE_SMOOTH);
                btn.setBorder(BorderFactory.createEmptyBorder());
                btn.setIcon(new ImageIcon(cardBackImgReszied));
            } catch (Exception ex) {
                System.out.println("Image not found");
            }
            handPanel.add(btn);
            btn.setActionCommand(String.valueOf(i));
            btn.addActionListener(playCardListener);
        }
        pane.revalidate();
        pane.repaint();
    }

    /**
     * switch between hide cards and show card
     * @param hide hide or show
     */
    public void setHideShowCardBtn(boolean hide) {
        if (hide) {
            hideShowCardBtn.setText("Hide Cards");
        } else {
            hideShowCardBtn.setText("Show Cards");
        }
    }

    /**
     * listener for play card
     */
    public void addPlayCardListener(ActionListener e) { playCardListener = e; }

    /**
     * listener show/hide cards button
     */
    public void addHideShowCardBtnListener(ActionListener e) { hideShowCardBtn.addActionListener(e); }

    /**
     * listener skip button
     */
    public void addSkipBtnListener(ActionListener e) { skipBtn.addActionListener(e); }

    /**
     * listener ai play button
     */
    public void addAiPlayBtnListener(ActionListener e) { aiPlayBtn.addActionListener(e); }

    /**
     * for testing
     */
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        frame.setStreakLabel(4);
        frame.setDiscardPileLabel("/cardImages/red1.png");
        String[] images = {"/cardImages/red3.png", "/cardImages/green9.png", "/cardImages/blue6.png",
                "/cardImages/yellowskip.png", "/cardImages/bluedrawtwo.png", "/cardImages/wild.png",
                "/cardImages/greenreverse.png"};
        frame.setPlayerLabel(3);
        frame.addHand(images);
    }
}