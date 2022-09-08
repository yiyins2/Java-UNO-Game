package unogame;

import org.junit.jupiter.api.Test;
import unocard.Card;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class DrawPileTest {
    @Test
    void testConstructor() {
        DrawPile pile = new DrawPile();
        assertEquals(108, pile.getSize());
        Stack<Card> drawpile = pile.getPile();
        for (int i = 0; i < 10; i++) {
            drawpile.get(i).printCard();
        }
    }

    @Test
    void drawOneCard() {
        DrawPile pile = new DrawPile();
        pile.drawOneCard();
        assertEquals(108 - 1, pile.getSize());
    }

    @Test
    void drawNumOfCards() {
        DrawPile pile = new DrawPile();
        pile.drawNumOfCards(20);
        assertEquals(108 - 20, pile.getSize());
    }
}