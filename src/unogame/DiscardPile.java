package unogame;

import unocard.*;

import java.util.Collections;
import java.util.Stack;

/**
 * @author Yiyin Shen
 */
public class DiscardPile extends Pile {

    public DiscardPile() {
        super();
    }

    /**
     * Prepare for moving discard pile to draw pile when draw pile is empty: set aside the top card,
     * shuffle the rest, and set the top card to be the new discard pile.
     * @return The shuffled discard pile without the top card
     */
    public Stack<Card> moveDiscardPile() {
        Card topCard = this.pile.pop();
        Collections.shuffle(this.pile);
        Stack<Card> newPile = (Stack<Card>)this.pile.clone();
        this.pile = new Stack<Card>();
        this.pile.push(topCard);
        return newPile;
    }
}
