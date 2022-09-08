package unogame;

import org.junit.jupiter.api.Test;
import unocard.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private int numOfUNOCards = 108;
    private int numOfCardsPlayerInitiallyHave = 7;
    private int numOfCardsInitiallyInDiscardPile = 1;

    @Test
    void testConstructor() {
        Game game = new Game(3, 0, true);
        assertEquals(numOfUNOCards - numOfCardsInitiallyInDiscardPile - 3 * numOfCardsPlayerInitiallyHave,
                game.getDrawPile().getSize());
        game.getDiscardPile().getTopCard().printCard();
        assertEquals(numOfCardsPlayerInitiallyHave, game.getPlayers()[0].getHand().size());
        assertEquals(numOfCardsPlayerInitiallyHave, game.getPlayers()[1].getHand().size());
        assertEquals(numOfCardsPlayerInitiallyHave, game.getPlayers()[2].getHand().size());
    }

    @Test
    void drawOneCard() {
        Game game = new Game(4, 0, true);
        Card cardToBeDrawn = game.getDrawPile().getTopCard();
        Card cardDrawn = game.drawOneCard();
        assertEquals(numOfUNOCards - numOfCardsInitiallyInDiscardPile - 4 * numOfCardsPlayerInitiallyHave - 1, game.getDrawPile().getSize());
        game.getDiscardPile().addOneCard(cardDrawn);
        assertEquals(cardToBeDrawn.getType(), game.getDiscardPile().getTopCard().getType());
        assertEquals(cardToBeDrawn.getColor(), game.getDiscardPile().getTopCard().getColor());
    }

    @Test
    void drawNumOfCards() {
        Game game = new Game(4, 0, true);
        ArrayList<Card> cardsDrawn = game.drawNumOfCards(20);
        assertEquals(numOfUNOCards - numOfCardsInitiallyInDiscardPile - 4 * numOfCardsPlayerInitiallyHave - 20, game.getDrawPile().getSize());
    }

    @Test
    void reuseDiscardPile() {
        // drawPile size: 108 - 1 - 10 * 7 = 37
        Game game = new Game(10, 0, true);
        // drawPile size: 7
        // discardPile size: 31
        ArrayList<Card> cardsDrawn = game.drawNumOfCards(30);
        for (Card card: cardsDrawn) {
            game.getDiscardPile().addOneCard(card);
        }
        assertEquals(numOfCardsPlayerInitiallyHave, game.getDrawPile().getSize());
        assertEquals(31, game.getDiscardPile().getSize());
        Card oldTopCard = game.getDiscardPile().getTopCard();
        /* draw 7 from drawPile, reuse discardPile (size 30 after set aside the top card), draw the remaining 3 from drawPile.
        drawPile size: 30 - 3 = 27
         */
        game.drawNumOfCards(10);
        assertEquals(27, game.getDrawPile().getSize());
        assertEquals(1, game.getDiscardPile().getSize());
        assertEquals(oldTopCard.getType(), game.getDiscardPile().getTopCard().getType());
        assertEquals(oldTopCard.getColor(), game.getDiscardPile().getTopCard().getColor());
    }

    @Test
    void reuseDiscardPileDrawOneCardCase() {
        // drawPile size: 108 - 1 - 10 * 7 = 37
        Game game = new Game(10,0, true);
        // drawPile size: 7
        // discardPile size: 31
        ArrayList<Card> cardsDrawn = game.drawNumOfCards(30);
        for (Card card: cardsDrawn) {
            game.getDiscardPile().addOneCard(card);
        }
        assertEquals(numOfCardsPlayerInitiallyHave, game.getDrawPile().getSize());
        assertEquals(31, game.getDiscardPile().getSize());
        Card oldTopCard = game.getDiscardPile().getTopCard();
        /* draw 7 from drawPile, reuse discardPile (size 30 after set aside the top card), new drawPile size: 30
         */
        game.drawNumOfCards(6);
        game.drawOneCard();
        assertEquals(30, game.getDrawPile().getSize());
        assertEquals(1, game.getDiscardPile().getSize());
        assertEquals(oldTopCard.getType(), game.getDiscardPile().getTopCard().getType());
        assertEquals(oldTopCard.getColor(), game.getDiscardPile().getTopCard().getColor());
    }

    @Test
    void gameFinished() {
        Game game = new Game(2, 0, true);
        assertEquals(-1, game.getWinner());
        ArrayList<Card> emptyHand = new ArrayList<Card>();
        game.getPlayers()[0].setHand(emptyHand);
        game.getPlayers()[0].addOneCardToHand(new SkipCard(Color.YELLOW));
        game.getDiscardPile().addOneCard(new NumberCard(Color.YELLOW, Type.ONE));
        game.playCard(new SkipCard(Color.YELLOW));
        assertEquals(0, game.getWinner());
    }

    @Test
    void playNoCard() {
        Game game  = new Game(2, 0, true);
        assertEquals(numOfCardsPlayerInitiallyHave, game.getPlayers()[0].getHand().size());
        game.getDiscardPile().addOneCard(new NumberCard(Color.YELLOW, Type.ONE));
        game.getDrawPile().addOneCard(new NumberCard(Color.YELLOW, Type.ONE));
        game.playCard(null);
        assertEquals(numOfCardsPlayerInitiallyHave, game.getPlayers()[0].getHand().size());
        assertEquals(Type.ONE, game.getDiscardPile().getTopCard().getType());
        assertEquals(Color.YELLOW, game.getDiscardPile().getTopCard().getColor());

        game.getDrawPile().addOneCard(new NumberCard(Color.RED, Type.SIX));
        game.playCard(null);
        assertEquals(Type.ONE, game.getDiscardPile().getTopCard().getType());
        assertEquals(Color.YELLOW, game.getDiscardPile().getTopCard().getColor());
    }

    @Test
    void testCustomPenalties() {
        Game game = new Game(3, 0, true);
        assertFalse(game.getCustomPenalties());
        game.setCustomPenalties(true);
        assertTrue(game.getCustomPenalties());

        assertEquals(numOfCardsPlayerInitiallyHave, game.getPlayers()[0].getHand().size());
        game.getDiscardPile().addOneCard(new NumberCard(Color.YELLOW, Type.ONE));
        game.getDrawPile().addOneCard(new NumberCard(Color.BLUE, Type.THREE));
        game.getPlayers()[0].addOneCardToHand(new NumberCard(Color.GREEN, Type.THREE));
        assertFalse(game.playCard(new NumberCard(Color.GREEN, Type.THREE)));
        assertEquals(numOfCardsPlayerInitiallyHave + 2, game.getPlayers()[0].getHand().size());
    }

    @Test
    void customBlackIsKing() {
        Game game = new Game(3, 0, true);
        assertFalse(game.getCustomBlackIsKing());
        game.setCustomBlackIsKing(true);
        assertTrue(game.getCustomBlackIsKing());

        game.getDiscardPile().addOneCard(new NumberCard(Color.YELLOW, Type.ONE));
        game.getDrawPile().addOneCard(new NumberCard(Color.YELLOW, Type.ONE));

        game.setTotalDrawTwoAndDrawFour(4);
        game.getDiscardPile().addOneCard(new WildDrawFourCard());
        game.getPlayers()[0].addOneCardToHand(new WildDrawFourCard());
        assertFalse(game.playCard(new WildDrawFourCard()));
        assertEquals(numOfCardsPlayerInitiallyHave + 1 + 4, game.getPlayers()[0].getHand().size());
    }

    @Test
    void isCardInHand() {
        Game game = new Game(3, 0, true);
        game.getPlayers()[0].addOneCardToHand(new WildCard());
        assertTrue(game.isCardInHand(new WildCard()) >= 0);
    }

    @Test
    void skip() {
        Game game = new Game(3, 0, true);
        game.getPlayers()[0].addOneCardToHand(new SkipCard(Color.YELLOW));
        game.getDiscardPile().addOneCard(new NumberCard(Color.YELLOW, Type.ONE));
        game.playCard(new SkipCard(Color.YELLOW));
        assertEquals(2, game.getCurrentPlayerId());

        game.reverse();

        game.getPlayers()[2].addOneCardToHand(new SkipCard(Color.YELLOW));
        game.playCard(new SkipCard(Color.YELLOW));
        assertEquals(0, game.getCurrentPlayerId());

        game.getPlayers()[0].addOneCardToHand(new SkipCard(Color.YELLOW));
        game.playCard(new SkipCard(Color.YELLOW));
        assertEquals(1, game.getCurrentPlayerId());
    }

    @Test
    void reverse() {
        Game game = new Game(3, 0, true);
        assertTrue(game.getRotateUp());

        game.getPlayers()[0].addOneCardToHand(new ReverseCard(Color.YELLOW));
        game.getDiscardPile().addOneCard(new NumberCard(Color.YELLOW, Type.ONE));
        game.playCard(new ReverseCard(Color.YELLOW));

        assertFalse(game.getRotateUp());

        game.getPlayers()[2].addOneCardToHand(new ReverseCard(Color.YELLOW));
        game.playCard(new ReverseCard(Color.YELLOW));

        assertTrue(game.getRotateUp());
    }

    @Test
    void drawTwoCard() {
        Game game = new Game(3, 0, true);
        game.setTotalDrawTwoAndDrawFour(4);
        assertEquals(numOfCardsPlayerInitiallyHave, game.getPlayers()[0].getHand().size());
        Card topCard = new DrawTwoCard(Color.YELLOW);
        game.getDiscardPile().addOneCard(topCard);
        game.playCard(null);
        assertEquals(numOfCardsPlayerInitiallyHave + 4, game.getPlayers()[0].getHand().size());
        assertEquals(0, game.getTotalDrawTwoAndDrawFour());

        game.getPlayers()[1].addOneCardToHand(new DrawTwoCard(Color.YELLOW));
        game.playCard(new DrawTwoCard(Color.YELLOW));
        assertEquals(2, game.getTotalDrawTwoAndDrawFour());
    }

    @Test
    void drawFourCard() {
        Game game = new Game(3, 0, true);
        game.setTotalDrawTwoAndDrawFour(4);
        assertEquals(numOfCardsPlayerInitiallyHave, game.getPlayers()[0].getHand().size());
        Card topCard = new WildDrawFourCard();
        game.getDiscardPile().addOneCard(topCard);
        game.playCard(null);
        assertEquals(numOfCardsPlayerInitiallyHave + 4, game.getPlayers()[0].getHand().size());
        assertEquals(0, game.getTotalDrawTwoAndDrawFour());

        game.getPlayers()[1].addOneCardToHand(new WildDrawFourCard());
        game.playCard(new WildDrawFourCard());
        assertEquals(4, game.getTotalDrawTwoAndDrawFour());
    }

    @Test
    void playNewDrawTwo() {
        Game game = new Game(3, 0, true);
        game.getPlayers()[0].addOneCardToHand(new WildDrawFourCard());
        game.playCard(new WildDrawFourCard());
        assertEquals(4, game.getTotalDrawTwoAndDrawFour());
        game.declareColor(Color.BLUE);
        assertFalse(game.playCard(null));
        assertEquals(0, game.getTotalDrawTwoAndDrawFour());
        assertEquals(4 + 7, game.getPlayers()[1].getHand().size());
        game.getPlayers()[2].addOneCardToHand(new DrawTwoCard(Color.BLUE));
        assertTrue(game.playCard(new DrawTwoCard(Color.BLUE)));
        assertEquals(2, game.getTotalDrawTwoAndDrawFour());
    }
}