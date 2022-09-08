package unocard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardMatchTest {
    private Card numberCard;
    private Card numberCard2;
    private Card numberCard3;

    private Card skipCard;
    private Card reverseCard;

    private Card drawTwoCard;
    private Card drawTwoCard2;

    private Card wildCard;
    private Card wildCard2;

    private Card wildDrawFourCard;
    private Card wildDrawFourCard2;

    @BeforeEach
    void SetUp() {
        numberCard = new NumberCard(Color.RED, Type.EIGHT);
        numberCard2 = new NumberCard(Color.GREEN, Type.EIGHT);
        numberCard3 = new NumberCard(Color.RED, Type.ONE);

        skipCard = new SkipCard(Color.RED);
        reverseCard = new ReverseCard(Color.GREEN);

        drawTwoCard = new DrawTwoCard(Color.RED);
        drawTwoCard2 = new DrawTwoCard(Color.BLUE);

        wildCard = new WildCard();
        wildCard2 = new WildCard();
        wildCard2.setEffectDone();
        wildCard2.setColor(Color.RED);

        wildDrawFourCard = new WildDrawFourCard();
        wildDrawFourCard2 = new WildCard();
        wildDrawFourCard2.setEffectDone();
        wildDrawFourCard2.setColor(Color.RED);
    }

    @Test
    void testNumCardMatch() {
        assertTrue(numberCard.isMatched(numberCard2));
        assertTrue(numberCard.isMatched(numberCard3));
        assertTrue(numberCard.isMatched(skipCard));
        assertFalse(numberCard.isMatched(reverseCard));
        assertFalse(numberCard.isMatched(wildCard));
        assertTrue(numberCard.isMatched(wildCard2));
        assertFalse(numberCard.isMatched(wildDrawFourCard));
        assertTrue(numberCard.isMatched(wildDrawFourCard2));
    }

    @Test
    void testSkipCardMatch() {
        assertFalse(skipCard.isMatched(wildCard));
        assertTrue(skipCard.isMatched(wildCard2));
        assertFalse(skipCard.isMatched(wildDrawFourCard));
        assertTrue(skipCard.isMatched(wildDrawFourCard2));
    }

    @Test
    void testReverseCardMatch() {
        assertFalse(reverseCard.isMatched(wildCard));
        assertFalse(reverseCard.isMatched(wildCard2));
        assertFalse(reverseCard.isMatched(wildDrawFourCard));
        assertFalse(reverseCard.isMatched(wildDrawFourCard2));
    }

    @Test
    void testDrawTwoCardMatch() {
        assertTrue(drawTwoCard.isMatched(drawTwoCard2));
        assertFalse(drawTwoCard.isMatched(wildDrawFourCard));
        assertTrue(drawTwoCard.isMatched(numberCard));
    }

    @Test
    void testWildCardMatch() {
        assertTrue(wildCard.isMatched(numberCard));
        assertTrue(wildCard.isMatched(reverseCard));
        assertTrue(wildCard.isMatched(wildCard2));
        assertFalse(wildCard.isMatched(drawTwoCard));
    }

    @Test
    void testWildDrawFourCardMatch() {
        assertTrue(wildDrawFourCard.isMatched(skipCard));
        assertTrue(wildDrawFourCard.isMatched(drawTwoCard));
        assertTrue(wildDrawFourCard.isMatched(wildDrawFourCard2));
    }

}
