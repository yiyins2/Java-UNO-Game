package unocontroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unocard.*;

import java.util.ArrayList;

public class GameControllerTest {
    GameController gameController;
    int TIME_WAIT = 20;

    @BeforeEach
    void initializeGame() {
        gameController = new GameController(1, 1, false);
    }

    @Test
    void skipBtn() {
        gameController.getFrame().skipBtn.doClick();
        try {
            Thread.sleep(TIME_WAIT);
        } catch (Exception e) {
            //do nothing
        }
    }

    @Test
    void hideShowBtn() {
        gameController.getFrame().hideShowCardBtn.doClick();
    }

    @Test
    void playCard() {
        ArrayList<Card> emptyHand = new ArrayList<Card>();
        gameController.getGame().getPlayers()[0].setHand(emptyHand);
        gameController.getGame().getPlayers()[0].addOneCardToHand(new SkipCard(Color.RED));
        gameController.getGame().getPlayers()[0].addOneCardToHand(new WildCard());

        gameController.getGame().getPlayers()[1].setHand(emptyHand);
        gameController.getGame().getPlayers()[1].addOneCardToHand(new SkipCard(Color.BLUE));
        gameController.getGame().getPlayers()[1].addOneCardToHand(new WildCard());

        gameController.getFrame().hideShowCardBtn.doClick();
        gameController.getFrame().hand.get(0).doClick();
        try {
            Thread.sleep(TIME_WAIT);
        } catch (Exception e) {
            //do nothing
        }
        gameController.getFrame().aiPlayBtn.doClick();
        try {
            Thread.sleep(TIME_WAIT);
        } catch (Exception e) {
            //do nothing
        }
        gameController.getFrame().hideShowCardBtn.doClick();
        gameController.getFrame().hand.get(0).doClick();
        try {
            Thread.sleep(TIME_WAIT);
        } catch (Exception e) {
            //do nothing
        }
        gameController.getFrame().aiPlayBtn.doClick();
        try {
            Thread.sleep(TIME_WAIT);
        } catch (Exception e) {
            //do nothing
        }
        gameController.getFrame().hideShowCardBtn.doClick();
        gameController.getFrame().hand.get(0).doClick();
        try {
            Thread.sleep(TIME_WAIT);
        } catch (Exception e) {
            //do nothing
        }
        gameController.getFrame().aiPlayBtn.doClick();
        try {
            Thread.sleep(TIME_WAIT);
        } catch (Exception e) {
            //do nothing
        }
    }

    @Test
    void winner() {
        ArrayList<Card> emptyHand = new ArrayList<Card>();
        gameController.getGame().getPlayers()[0].setHand(emptyHand);
        gameController.getGame().getPlayers()[0].addOneCardToHand(new SkipCard(Color.RED));
        gameController.getGame().getDiscardPile().addOneCard(new NumberCard(Color.RED, Type.EIGHT));
        gameController.getFrame().hideShowCardBtn.doClick();
        gameController.getFrame().hand.get(0).doClick();
        try {
            Thread.sleep(TIME_WAIT);
        } catch (Exception e) {
            //do nothing
        }
    }
}
