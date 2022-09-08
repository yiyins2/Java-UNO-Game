package unogame;

import unocard.Card;

import java.util.Stack;

/**
 * @author Yiyin Shen
 */
public class Pile {
    public Stack<Card> pile;

    public Pile() { this.pile = new Stack<Card>(); }

    /**
     * get the draw/discard pile size
     * @return
     */
    public int getSize() {
        return this.pile.size();
    }

    /**
     * @return true if the draw/discard pile is empty
     */
    public boolean isEmpty() {
        return this.pile.empty();
    }

    /**
     * @return the current the draw/discard pile
     */
    public Stack<Card> getPile() { return this.pile; }

    /**
     * set a new the draw/discard pile
     * @param newPile the new the draw/discard pile
     */
    public void setPile(Stack<Card> newPile) { this.pile = newPile; }

    /**
     * peek the top card of the draw/discard pile
     * @return top card of the draw/discard pile
     */
    public Card getTopCard() { return this.pile.peek(); }

    /**
     * remove the top card of the draw/discard pile
     * @return top card of the draw/discard pile
     */
    public Card drawOneCard() { return this.pile.pop(); }

    /**
     * add one card to the top of the draw/discard pile
     * @param card new card to add
     */
    public void addOneCard(Card card) { this.pile.push(card); }
}
