package unogame;

import unocard.*;

import java.util.ArrayList;

/**
 * @author Yiyin Shen
 */
public class Player {
    private ArrayList<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }

    /**
     * @return true if hand is empty
     */
    public boolean isHandEmpty() { return this.hand.isEmpty(); }

    /**
     * @return the hand
     */
    public ArrayList<Card> getHand() { return this.hand; }

    /**
     * set a new hand
     * @param hand new hand
     */
    public void setHand(ArrayList<Card> hand) { this.hand = hand; }

    /**
     * add one card to hand
     * @param newCard the new card to add
     */
    public void addOneCardToHand(Card newCard) { this.hand.add(newCard); }

    /**
     * add an arraylist of cards to hand
     * @param newCards an arraylist of cards to add
     */
    public void addMultipleCardsToHand(ArrayList<Card> newCards) {
        this.hand.addAll(newCards);
    }

    /**
     * Print current hand
     */
    public void printHand() {
        for (Card card: this.hand) {
            card.printCard();
        }
    }

    /**
     * @param topCard the top card of discard pile
     * @return an arraylist of valid card index
     */
    public ArrayList<Integer> validCardsInHandIdx(Card topCard) {
        ArrayList<Card> hand = getHand();
        ArrayList<Integer> validCardIdx = new ArrayList<Integer>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).isMatched(topCard)) {
                validCardIdx.add(i);
            }
        }
        return validCardIdx;
    }

    /**
     * choose the best valid card in hand
     * @param topCard the top card of discard pile
     * @return the best valid card in hand
     */
    public Card chooseCard(Card topCard) {return null; }

    /**
     * choose color
     * @return the chosen color
     */
    public Color chooseColor() {return null; }
}

