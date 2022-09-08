package unocontroller;

import unocard.Card;
import unocard.Type;
import unogame.Game;
import unogui.ChooseColorFrame;
import unogui.GameFrame;
import unogui.WinnerFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameController {
    private Game game;
    private GameFrame frame;

    private boolean isCardHide;
    private boolean colorThisRound;

    private int WAIT_TIME = 10;

    public GameFrame getFrame() { return this.frame; }
    public Game getGame() { return this.game; }

    public GameController(int numHumanPlayers, int numAIPlayers, boolean baselineOrStrategic) {
        game = new Game(numHumanPlayers, numAIPlayers, baselineOrStrategic);
        game.setCustomPenalties(true);
        game.setCustomBlackIsKing(true);

        frame = new GameFrame();

        frame.addHideShowCardBtnListener(new HideShowCardBtnListener());

        frame.addSkipBtnListener(new SkipBtnListener());

        frame.addPlayCardListener(new PlayCardListener());

        frame.addAiPlayBtnListener(new AiPlayCardListener());

        frame.hideHand(game.getCurrentPlayerHand().size());
        frame.setDiscardPileLabel(game.getTopCard().printPNG());

        isCardHide = false;
        colorThisRound = false;
    }

    /**
     * Current player played card and apply card effect.
     * Check whether current player wins. If wins, go to winner frame.
     * Choose color if wild cards
     * Display played card information and update hand, streak, etc.
     * Check whether need to switch AI player next round
     * Update to next round
     */
    class PlayCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int idx = Integer.parseInt(e.getActionCommand());
            if (game.getWinner() != -1) {
                frame.frame.dispose();
                new WinnerFrame(game.getWinner());
                return;
            }
            boolean played = game.playCard(game.getCurrentPlayerHand().get(idx));
            playCardController(played, "");
            frame.addHand(game.printCardInHandPNG(game.getPreviousPlayerId()));
            if (game.getWinner() != -1) {
                frame.frame.dispose();
                new WinnerFrame(game.getWinner());
                return;
            }
            if (!chooseColorController()) {
                frame.setStreakLabel(game.getTotalDrawTwoAndDrawFour());

                Timer timer = new Timer(WAIT_TIME, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        checkAndSwitchToAI();
                        updateViewAfterPlayOrSkip();
                        frame.setHideShowCardBtn(false);
                        isCardHide = true;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    /**
     * Current player skipped and apply penalties or played drawn card.
     * Choose color if wild cards
     * Display skipped or played card information and update hand, streak, etc.
     * Check whether need to switch AI player next round
     * Update to next round
     */
    class SkipBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean played = game.playCard(null);
            frame.addHand(game.printCardInHandPNG(game.getPreviousPlayerId()));
            skipCardController(played, "");
            if (!chooseColorController()) {
                frame.setStreakLabel(game.getTotalDrawTwoAndDrawFour());

                Timer timer = new Timer(WAIT_TIME, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        checkAndSwitchToAI();
                        updateViewAfterPlayOrSkip();
                        frame.setHideShowCardBtn(false);
                        isCardHide = true;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    /**
     * Hide and show switch
     */
    class HideShowCardBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isCardHide) {
                frame.setHideShowCardBtn(false);
                frame.hideHand(game.getCurrentPlayerHand().size());
            } else {
                frame.setHideShowCardBtn(true);
                frame.addHand(game.printCardInHandPNG(game.getCurrentPlayerId()));
            }
            isCardHide = !isCardHide;
        }
    }

    /**
     * Get next move (the best card or skip) from AI.
     * Play best card or skip
     * Display skipped played card information and update hand, streak, etc.
     * Check whether need to switch human player next round
     * Update to next round
     */
    class AiPlayCardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Card playCard = game.getPlayers()[game.getCurrentPlayerId()].chooseCard(game.getTopCard());
            // ai skip round
            if (playCard == null) {
                boolean played = game.playCard(null);
                skipCardController(played, "AI ");
            } else {
                // ai play cards
                boolean played = game.playCard(playCard);
                if (game.getWinner() != -1) {
                    frame.frame.dispose();
                    new WinnerFrame(game.getWinner());
                    return;
                }
                playCardController(played, "AI ");
            }
            // ai choose color
            if (game.getUndeclaredWildCard()) {
                colorThisRound = true;
                game.declareColor(game.getPlayers()[game.getCurrentPlayerId()].chooseColor());
                frame.setColorLabel("AI Player " + game.getPreviousPlayerId() + " chose color: " +
                        game.getTopCard().getColor().toString());
            } else {
                colorThisRound = false;
            }
            frame.hideHand(game.getPlayers()[game.getPreviousPlayerId()].getHand().size());
            frame.setStreakLabel(game.getTotalDrawTwoAndDrawFour());
            Timer timer = new Timer(WAIT_TIME, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (game.getCurrentPlayerId() <= game.getNumOfHumanPlayers() - 1) {
                        frame.setHideShowCardBtn(false);
                        isCardHide = true;
                        frame.setHumanPlay(true);
                        frame.setAiPlayBtn(false);
                    }
                    updateViewAfterPlayOrSkip();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Go to choose color frame and print chosen color information
     * @return true if choose color
     */
    private boolean chooseColorController() {
        if (game.getUndeclaredWildCard()) {
            colorThisRound = true;
            ChooseColorFrame colorFrame = new ChooseColorFrame(game);
            colorFrame.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    frame.setColorLabel("Player " + game.getPreviousPlayerId() + " chose color: " +
                            game.getTopCard().getColor().toString());
                    frame.setStreakLabel(game.getTotalDrawTwoAndDrawFour());

                    Timer timer = new Timer(WAIT_TIME, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            checkAndSwitchToAI();
                            updateViewAfterPlayOrSkip();
                            frame.setHideShowCardBtn(false);
                            isCardHide = true;
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            });
            return true;
        } else {
            colorThisRound = false;
            return false;
        }
    }

    /**
     * Print played card information if the card is correct
     * Print penalties information if the card is incorrect
     * @param played true if correct card played, false if incorrect card played
     */
    private void playCardController(boolean played, String humanOrAI) {
        if (played) {
            if (game.getTopCard().isWild()) {
                frame.setRoundLabel(humanOrAI + "Player " + game.getCurrentPlayerId() + " played " +
                        game.getTopCard().printCard());
            } else {
                frame.setRoundLabel(humanOrAI + "Player " + game.getPreviousPlayerId() + " played " +
                        game.getTopCard().printCard());
            }
            frame.setDiscardPileLabel(game.getTopCard().printPNG());
        } else {
            frame.setRoundLabel(humanOrAI + "Player " + game.getPreviousPlayerId() + " played incorrect card and got penalized");
        }
    }

    /**
     * Print played drawn card info, or
     * Print drawn card cannot be played info
     * @param played true if drawn card played, false if drawn card cannot be played
     */
    private void skipCardController(boolean played, String humanOrAI) {
        if (played) {
            if (game.getUndeclaredWildCard()) {
                frame.setRoundLabel(humanOrAI + "Player " + game.getCurrentPlayerId() +
                        " skipped this round and played " + game.getTopCard().printCard());
            } else {
                frame.setRoundLabel(humanOrAI + "Player " + game.getPreviousPlayerId() +
                        " skipped this round and played " + game.getTopCard().printCard());
            }
            frame.setDiscardPileLabel(game.getTopCard().printPNG());
        } else {
            frame.setRoundLabel(humanOrAI + "Player " + game.getPreviousPlayerId() +
                    " skipped this round and didn't play");
        }
    }

    /**
     * check if current round should be ai or human
     * if game setting does not match the player selection, switch game setting
     */
    private void checkAndSwitchToAI() {
        if (game.getCurrentPlayerId() > game.getNumOfHumanPlayers() - 1) {
            frame.setHumanPlay(false);
            frame.setAiPlayBtn(true);
        }
    }

    /**
     * Print previous round info and hide hand
     */
    private void updateViewAfterPlayOrSkip() {
        frame.setPlayerLabel(game.getCurrentPlayerId());
        frame.setRoundLabel("Previous Round: " + frame.getRoundLabel());
        if (colorThisRound) {
            frame.setColorLabel("Previous Round: " + frame.getColorLabel());
        } else {
            frame.setColorLabel("");
        }
        frame.hideHand(game.getCurrentPlayerHand().size());
    }
}
