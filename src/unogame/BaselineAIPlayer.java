package unogame;

import unocard.Card;
import unocard.Color;

import java.util.ArrayList;
import java.util.Random;

public class BaselineAIPlayer extends Player {
    @Override
    public Card chooseCard(Card topCard) {
        ArrayList<Integer> validCardIdx = validCardsInHandIdx(topCard);
        if (validCardIdx.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        int randIdx = validCardIdx.get(rand.nextInt(validCardIdx.size()));
        return getHand().get(randIdx);
    }

    @Override
    public Color chooseColor() { return getHand().get(0).getColor(); }
}
