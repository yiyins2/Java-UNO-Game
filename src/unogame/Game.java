package unogame;

import unocard.*;

import java.util.ArrayList;

/**
 * @author Yiyin Shen
 */
public class Game {
    private final int numOfPlayers;
    private final int numOfHumanPlayers;
    private final int numOfAIPlayers;
    private boolean rotateUp;
    private DrawPile drawPile;
    private DiscardPile discardPile;
    private Player[] players;
    private int currentPlayerId;
    private int previousPlayerId;
    private int totalDrawTwoAndDrawFour;
    private boolean undeclaredWildCard;
    private int winner;
    private boolean playedAfterSkip;
    private boolean baselineOrStratgeicAI;

    private boolean customPenalties;
    private boolean customBlackIsKing;

    /**
     * @return true if total number of players is between 2 and 10 inclusive, false otherwise
     */
    public static boolean checkNumPlayer(int numOfHumanPlayers, int numOfAIPlayers) {
        int numOfPlayers = numOfHumanPlayers + numOfAIPlayers;
        return 2 <= numOfPlayers && numOfPlayers <= 10;
    }

    /**
     * Initialize one game. Create a draw pile and a discard pile.
     * Create an array of players and deal seven cards to each of them.
     * Set initial rotation, first player, and totalDrawTwoAndDrawFour.
     */
    public Game(int numOfHumanPlayers, int numOfAIPlayers, boolean baselineOrStratgeicAI) {
        this.numOfHumanPlayers = numOfHumanPlayers;
        this.numOfAIPlayers = numOfAIPlayers;
        this.numOfPlayers = numOfHumanPlayers + numOfAIPlayers;
        this.baselineOrStratgeicAI = baselineOrStratgeicAI;

        this.drawPile = new DrawPile();
        this.discardPile = new DiscardPile();

        // First card of discard pile need to be a number card.
        Card topCard = drawOneCard();
        while (!topCard.isNumber()) {
            this.drawPile.addOneCard(topCard);
            this.drawPile.shufflePile();
            topCard = drawOneCard();
        }
        discardPile.addOneCard(topCard);

        this.players = new Player[numOfPlayers];
        for (int i = 0; i < numOfHumanPlayers; i++) {
            players[i] = new Player();
            players[i].addMultipleCardsToHand(drawNumOfCards(7));
        }

        if (baselineOrStratgeicAI) {
            for (int i = numOfHumanPlayers; i < numOfPlayers; i++) {
                players[i] = new BaselineAIPlayer();
                players[i].addMultipleCardsToHand(drawNumOfCards(7));
            }
        } else {
            for (int i = numOfHumanPlayers; i < numOfPlayers; i++) {
                players[i] = new StrategicAIPlayer();
                players[i].addMultipleCardsToHand(drawNumOfCards(7));
            }
        }

        this.rotateUp = true;
        this.currentPlayerId = 0;

        this.totalDrawTwoAndDrawFour = 0;

        this.winner = -1;

        this.customPenalties = false;
        this.customBlackIsKing = false;
    }

    /**
     * getter for numOfHumanPlayers
     * @return numOfHumanPlayers
     */
    public int getNumOfHumanPlayers() { return this.numOfHumanPlayers; }

    /**
     * setter for numOfHumanPlayers
     * @return new numOfHumanPlayers
     */
    public int getNumOfAIPlayers() { return this.numOfAIPlayers; }

    /**
     * getter for numOfPlayers
     * @return numOfPlayers
     */
    public int getNumOfPlayers() { return this.numOfPlayers; }

    /**
     * getter for baselineOrStrategicAI
     * @return
     */
    public boolean getBaselineOrStrategicAI() { return this.baselineOrStratgeicAI; }

    /**
     * getter for winner
     * @return winner
     */
    public int getWinner() { return this.winner; }

    /**
     * getter for players
     * @return the array of players
     */
    public Player[] getPlayers() { return this.players; }

    /**
     * getter for draw pile
     * @return the draw pile
     */
    public DrawPile getDrawPile() { return drawPile; }

    /**
     * getter for top card of draw pile
     * @return the top card of draw pile
     */
    public Card getTopCard() { return this.discardPile.getTopCard(); }

    /**
     * getter for discard pile
     * @return the discard pile
     */
    public DiscardPile getDiscardPile() { return discardPile; }

    /**
     * getter for total streak
     * @return the total streak
     */
    public int getTotalDrawTwoAndDrawFour() { return this.totalDrawTwoAndDrawFour; }

    /**
     * setter for total streak
     * @param num the new total streak
     */
    public void setTotalDrawTwoAndDrawFour(int num) { this.totalDrawTwoAndDrawFour = num; }

    /**
     * getter for current player id
     * @return the current player id
     */
    public int getCurrentPlayerId() { return currentPlayerId; }

    /**
     * getter for current player hand
     * @return the hand of current player
     */
    public ArrayList<Card> getCurrentPlayerHand() { return players[currentPlayerId].getHand(); }

    /**
     * getter for rotation
     * @return the current rotation (up or down )
     */
    public boolean getRotateUp() { return this.rotateUp; }

    /**
     * getter for customPenalties
     * @return customPenalties
     */
    public boolean getCustomPenalties() { return this.customPenalties; }

    /**
     * setter for customPenalties
     * @param set new customPenalties
     */
    public void setCustomPenalties(boolean set) { this.customPenalties = set; }

    /**
     * getter for customBlackIsKing
     * @return customBlackIsKing
     */
    public boolean getCustomBlackIsKing() { return this.customBlackIsKing; }

    /**
     * setter for customBlackIsKing
     * @param set new customBlackIsKing
     */
    public void setCustomBlackIsKing(boolean set) { this.customBlackIsKing = set; }

    /**
     * getter for undeclaredWildCard
     * @return undeclaredWildCard
     */
    public boolean getUndeclaredWildCard() { return this.undeclaredWildCard; }

    /**
     * getter for previousPlayerId
     * @return previousPlayerId
     */
    public int getPreviousPlayerId() {return this.previousPlayerId; }

    /**
     * Draw one card from draw pile. If size of draw pile is smaller or equal to num, move discard pile to draw pile.
     * @return The card drawn
     */
    public Card drawOneCard() {
        if (this.drawPile.getSize() < 1) {
            drawPile.setPile(this.discardPile.moveDiscardPile());
            return this.drawPile.drawOneCard();
        } else if (this.drawPile.getSize() == 1) {
            Card newCard = this.drawPile.drawOneCard();
            drawPile.setPile(this.discardPile.moveDiscardPile());
            return newCard;
        } else {
            return this.drawPile.drawOneCard();
        }
    }

    /**
     * Draw num cards from draw pile. If size of draw pile is smaller or equal to num, move discard pile to draw pile.
     * @return An ArrayList of drawn cards
     */
    public ArrayList<Card> drawNumOfCards(int num) {
        int drawPileSize = this.drawPile.getSize();
        ArrayList<Card> cards;
        if (drawPileSize <= num) {
            cards = this.drawPile.drawNumOfCards(drawPileSize);
            drawPile.setPile(this.discardPile.moveDiscardPile());
            cards.addAll(drawPile.drawNumOfCards(num - drawPileSize));
        } else {
            cards = this.drawPile.drawNumOfCards(num);
        }
        return cards;
    }

    /**
     * bufferedWildCard must have a wild card
     * @param color color to declare
     * @return true if successful
     */
    public boolean declareColor(Color color) {
        if (!this.undeclaredWildCard) {
            return false;
        }
        getTopCard().setColor(color);
        this.undeclaredWildCard = false;
        this.previousPlayerId = this.currentPlayerId;
        skip();
        return true;
    }

    /**
     * If skip this round, then apply penalty.
     * If played unmatched card, then apply penalty.
     * If played correct card, apply effect
     * @param playedCard null if skip this round or the played card
     * @return true if played one card, false otherwise
     */
    public boolean playCard(Card playedCard) {
        if (undeclaredWildCard) {
            return false;
        }
        boolean notPlayThisRound = playedCard == null;
        if (!notPlayThisRound) {
            int cardIdx = isCardInHand(playedCard);
            if (cardIdx == -1) {
                return false;
            } else {
                Card topCard = getTopCard();
                // custom rule: black is king: stop stacking and apply penalties
                if (customBlackIsKing) {
                    if (topCard.isWild() && topCard.getType() == Type.DRAW_FOUR && !topCard.getEffectDone()) {
                        applyCardEffect(null);
                        return false;
                    }
                }
                if (playedCard.isMatched(topCard)) {
                    players[currentPlayerId].getHand().remove(cardIdx);
                    applyCardEffect(playedCard);
                    if (players[currentPlayerId].isHandEmpty()) {
                        winner = currentPlayerId;
                    }
                    return true;
                } else {
                    // custom rule: penalties
                    if (customPenalties) {
                        applyCardEffect(null);
                        return false;
                    }
                    return false;
                }
            }
        } else {
            applyCardEffect(null);
            return playedAfterSkip;
        }
    }

    /**
     * Apply card effect after playing a card. Including discarding the card.
     * @param playedCard the card played, null if player choose not to play this round,
     *                   must be a valid play
     */
    private void applyCardEffect(Card playedCard) {
        Card topCard = getTopCard();
        boolean notPlayThisRound = playedCard == null;

        if (!notPlayThisRound) {
            if (topCard.getType() == Type.WILD || (topCard.getType() == Type.DRAW_FOUR && topCard.getEffectDone())) {
                topCard.setColor(Color.WILD);
            }
            discardPile.addOneCard(playedCard);
        }

        if (topCard.getType() == Type.DRAW_TWO && !topCard.effectDone) {
            drawTwoCardCase(playedCard, topCard);
            return;
        }

        if (topCard.getType() == Type.DRAW_FOUR && !topCard.effectDone) {
            drawFourCardCase(playedCard, topCard);
            return;
        }

        // topCard.effectDone true
        // draw one card if the player decided not to play this round
        if (notPlayThisRound) {
            Card newCard = drawOneCard();
            if (newCard.isMatched(topCard)) {
                applyCardEffect(newCard);
                playedAfterSkip = true;
                return;
            }
            playedAfterSkip = false;
            players[currentPlayerId].addOneCardToHand(newCard);
            this.previousPlayerId = this.currentPlayerId;
            skip();
            return;
        }

        if (topCard.getType() == Type.DRAW_TWO || topCard.getType() == Type.DRAW_FOUR) {
            topCard.setEffectNotDone();
        }

        if (playedCard.getType() == Type.DRAW_TWO) {
            setTotalDrawTwoAndDrawFour(2);
        }

        if (playedCard.getType() == Type.DRAW_FOUR) {
            setTotalDrawTwoAndDrawFour(4);
            wild();
            return;
        }

        if (playedCard.getType() == Type.REVERSE) {
            reverse();
        }

        if (playedCard.getType() == Type.WILD) {
            wild();
            return;
        }

        if (playedCard.getType() == Type.SKIP) {
            this.previousPlayerId = this.currentPlayerId;
            skip();
            skip();
            return;
        }
        this.previousPlayerId = this.currentPlayerId;
        skip();
    }

    /**
     * If top card is draw two card, there are three cases:
     *      if there is no card to play, add steak number of cards to hand
     *      if play another draw two, streak plus two
     *      if play draw four, streak plus four
     * @param playedCard card to play
     * @param topCard top Card
     */
    public void drawTwoCardCase(Card playedCard, Card topCard) {
        // stop draw two and draw four streak if no draw two or four card to play
        if (playedCard == null) {
            players[currentPlayerId].addMultipleCardsToHand(drawNumOfCards(totalDrawTwoAndDrawFour));
            this.totalDrawTwoAndDrawFour = 0;
            playedAfterSkip = false;
            this.previousPlayerId = this.currentPlayerId;
            skip();
            topCard.setEffectDone();
            return;
        }
        if (playedCard.getType() == Type.DRAW_TWO) {
            this.totalDrawTwoAndDrawFour += 2;
            this.previousPlayerId = this.currentPlayerId;
            skip();
            topCard.setEffectNotDone();
            return;
        }
        if (playedCard.getType() == Type.DRAW_FOUR) {
            this.totalDrawTwoAndDrawFour += 4;
            wild();
            topCard.setEffectNotDone();
        }
    }

    /**
     * If top card is draw four card, there are two cases:
     *      if there is no card to play, add streak number of cards to hand
     *      if play draw four, streak plus four
     * @param playedCard card to play
     * @param topCard top Card
     */
    public void drawFourCardCase(Card playedCard, Card topCard) {
        // stop draw two and draw four streak if no draw four card to play
        if (playedCard == null || customBlackIsKing) {
            players[currentPlayerId].addMultipleCardsToHand(drawNumOfCards(totalDrawTwoAndDrawFour));
            this.totalDrawTwoAndDrawFour = 0;
            playedAfterSkip = false;
            this.previousPlayerId = this.currentPlayerId;
            skip();
            topCard.setEffectDone();
            return;
        }
        if (playedCard.getType() == Type.DRAW_FOUR) {
            this.totalDrawTwoAndDrawFour += 4;
            wild();
            topCard.setEffectNotDone();
        }
    }

    /**
     * set undeclaredWildCard
     */
    public void wild() {
        undeclaredWildCard = true;
    }

    /**
     * skip one player
     */
    public void skip() {
        if (this.rotateUp == true) {
            this.currentPlayerId = (this.currentPlayerId + 1) % this.numOfPlayers;
        } else {
            this.currentPlayerId = (this.currentPlayerId - 1 + this.numOfPlayers) % this.numOfPlayers;
        }
    }

    /**
     * reverse rotation
     */
    public void reverse() {
        this.rotateUp = !this.rotateUp;
    }

    /**
     * Check whether there is a card that has the same color and the same type
     * @return the owned matched card, null if none
     */
    public int isCardInHand(Card card) {
        ArrayList<Card> currentHand = players[currentPlayerId].getHand();
        for (int i = 0; i < currentHand.size(); i++) {
            Card currentCard = currentHand.get(i);
            if (currentCard.getType() == card.getType() && currentCard.getColor() == card.getColor()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return convert cards in hand to an array of corresponding PNGs
     */
    public String[] printCardInHandPNG(int id) {
        ArrayList<Card> hand = players[id].getHand();
        String[] pngs = new String[hand.size()];
        for (int i = 0; i < hand.size(); i++) {
            pngs[i] = hand.get(i).printPNG();
        }
        return pngs;
    }
}
