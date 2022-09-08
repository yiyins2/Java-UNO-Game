package unogame;

import unocard.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * @author Yiyin Shen
 */
public class DrawPile extends Pile {

    /**
     * Initialize the draw pile with 108 cards and shuffle them.
     */
    public DrawPile() {
        super();
        Type[] oneToNine = {Type.ONE, Type.TWO, Type.THREE, Type.FOUR, Type.FIVE, Type.SIX, Type.SEVEN, Type.EIGHT, Type.NINE};
        Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};

        // number, skip, reverse, draw two cards
        for (int i = 0; i < 2; i++) {
            for (Color color: colors) {
                for (Type type: oneToNine) {
                    this.pile.push(new NumberCard(color, type));
                }

                this.pile.push(new SkipCard(color));
                this.pile.push(new ReverseCard(color));
                this.pile.push(new DrawTwoCard(color));
            }
        }

        // number cards
        for (Color color: colors) {
            this.pile.push(new NumberCard(color, Type.ZERO));
        }

        // wild, wild draw four cards
        for (int i = 0; i < 4; i++) {
            this.pile.push(new WildCard());
            this.pile.push(new WildDrawFourCard());
        }

        Collections.shuffle(this.pile);
    }

    public void shufflePile() { Collections.shuffle(this.pile); }

    /**
     * Draw num cards.
     * @param num num will always smaller than the size of draw pile.
     *            Moving discard pile to draw pile is handled in drawNumofCards of unogame.Game class.
     * @return An ArrayList of drawn cards
     */
    public ArrayList<Card> drawNumOfCards(int num) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < num; i++) {
            cards.add(this.pile.pop());
        }
        return cards;
    }
}
