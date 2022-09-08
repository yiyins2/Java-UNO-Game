package unocard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void testNumCard() {
        Card numberCard = new NumberCard(Color.RED, Type.EIGHT);
        assertEquals(Color.RED, numberCard.getColor());
        assertEquals(Type.EIGHT, numberCard.getType());

        assertEquals("/cardImages/red8.png", numberCard.printPNG());

        Card numberCard1 = new NumberCard(Color.BLUE, Type.THREE);
        assertEquals("/cardImages/blue3.png", numberCard1.printPNG());

        Card numberCard2 = new NumberCard(Color.YELLOW, Type.FIVE);
        assertEquals("/cardImages/yellow5.png", numberCard2.printPNG());

        Card numberCard3 = new NumberCard(Color.GREEN, Type.NINE);
        assertEquals("/cardImages/green9.png", numberCard3.printPNG());
    }

    @Test
    void testSkipCard() {
        Card skipCard = new SkipCard(Color.GREEN);
        assertEquals(Color.GREEN, skipCard.getColor());
        assertEquals(Type.SKIP, skipCard.getType());
        assertEquals("/cardImages/greenskip.png", skipCard.printPNG());
    }

    @Test
    void testReverseCard() {
        Card reverseCard = new ReverseCard(Color.YELLOW);
        assertEquals(Color.YELLOW, reverseCard.getColor());
        assertEquals(Type.REVERSE, reverseCard.getType());
        assertEquals("/cardImages/yellowreverse.png", reverseCard.printPNG());
    }

    @Test
    void testDrawTwoCard() {
        Card drawTwoCard = new DrawTwoCard(Color.BLUE);
        assertEquals(Color.BLUE, drawTwoCard.getColor());
        assertEquals(Type.DRAW_TWO, drawTwoCard.getType());
        assertEquals("/cardImages/bluedrawtwo.png", drawTwoCard.printPNG());
    }

    @Test
    void wildCard() {
        Card wildCard = new WildCard();
        assertEquals(Color.WILD, wildCard.getColor());
        assertEquals(Type.WILD, wildCard.getType());
        assertEquals("/cardImages/wild.png", wildCard.printPNG());
    }

    @Test
    void testDrawFourCard() {
        Card drawFourCard = new WildDrawFourCard();
        assertEquals(Color.WILD, drawFourCard.getColor());
        assertEquals(Type.DRAW_FOUR, drawFourCard.getType());
        assertEquals("/cardImages/wilddrawfour.png", drawFourCard.printPNG());
    }

    @Test
    void testSetColor() {
        Card wildCard = new WildCard();
        assertEquals(Color.WILD, wildCard.getColor());
        wildCard.setColor(Color.YELLOW);
        assertEquals(Color.YELLOW, wildCard.getColor());
    }

    @Test
    void testGetSetEffectDone() {
        Card wildCard = new WildCard();
        assertFalse(wildCard.getEffectDone());
        wildCard.setEffectDone();
        assertTrue(wildCard.getEffectDone());
        wildCard.setEffectNotDone();
        assertFalse(wildCard.getEffectDone());
    }

    @Test
    void testIsWildIsNumber() {
        Card wildCard = new WildCard();
        assertTrue(wildCard.isWild());
        assertFalse(wildCard.isNumber());
    }
}