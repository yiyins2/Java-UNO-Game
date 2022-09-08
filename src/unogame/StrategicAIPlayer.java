package unogame;

import unocard.Card;
import unocard.Color;
import unocard.Type;
import unocard.WildCard;

import java.util.*;

public class StrategicAIPlayer extends Player {
    @Override
    /**
     * choose the best card in hand
     */
    public Card chooseCard(Card topCard) {
        // if top card is wild draw four and no wild draw four in hand, play none
        if (topCard.getType() == Type.DRAW_FOUR && topCard.getColor() == Color.WILD && !topCard.getEffectDone()) {
            return null;
        }
        ArrayList<Card> hand = getHand();
        List<Color> colors = colorRank();

        // play draw two or draw four if top card is draw two
        if (topCard.getType() == Type.DRAW_TWO && !topCard.getEffectDone()) {
            ArrayList<Integer> drawTwoIdx = new ArrayList<Integer>();
            int drawfourIdx = -1;
            for (int i = 0; i < getHand().size(); i++) {
                if (hand.get(i).getType() == Type.DRAW_TWO) {
                    drawTwoIdx.add(i);
                }
                if (hand.get(i).getType() == Type.DRAW_FOUR) {
                    drawfourIdx = i;
                }
            }
            for (Color color: colors) {
                for (Integer i: drawTwoIdx) {
                    if (hand.get(i).getColor() == color) {
                        return hand.get(i);
                    }
                }
            }
            if (drawfourIdx != -1) {
                return hand.get(drawfourIdx);
            }
        }
        ArrayList<Integer> validNumIdx = new ArrayList<>();
        ArrayList<Integer> validOtherIdx = new ArrayList<>();
        int wildIdx = -1;
        int wildDrawFourIdx = -1;
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (card.isMatched(topCard)) {
                if (card.isNumber()) {
                    validNumIdx.add(i);
                } else {
                    validOtherIdx.add(i);
                }
                if (card.getColor() == Color.WILD && card.getType() == Type.DRAW_FOUR) {
                    wildDrawFourIdx = i;
                }
                if (card.getColor() == Color.WILD && card.getType() == Type.WILD) {
                    wildIdx = i;
                }
            }
        }
        // play valid popular color num color
        for (Color color: colors) {
            for (Integer i: validNumIdx) {
                if (hand.get(i).getColor() == color) {
                    return hand.get(i);
                }
            }
        }
        // play valid popular color special color
        for (Color color: colors) {
            for (Integer i: validOtherIdx) {
                if (hand.get(i).getColor() == color) {
                    return hand.get(i);
                }
            }
        }
        // play draw wild card
        if (wildIdx != -1) {
            return hand.get(wildIdx);
        }
        if (wildDrawFourIdx != -1) {
            return hand.get(wildDrawFourIdx);
        }
        return null;
    }

    /**
     * rank the color occurrence in hand descending
     * @return the rank
     */
    public List<Color> colorRank() {
        Map<Color, Integer> colorCount = new HashMap<>();
        ArrayList<Card> hand = getHand();
        List<Color> colors = Arrays.asList(Color.GREEN, Color.YELLOW, Color.RED, Color.BLUE);
        for (Color color: colors) {
            colorCount.put(color, 0);
        }
        colorCount.put(Color.WILD, 0);
        for (Card card: hand) {
            colorCount.put(card.getColor(), colorCount.get(card.getColor()) + 1);
        }
        colors.sort(new Comparator<Color>() {
            @Override
            public int compare(Color o1, Color o2) {
                return colorCount.get(o2).compareTo(colorCount.get(o1));
            }
        });
        return colors;
    }

    @Override
    /**
     * choose the most popular color in hand
     */
    public Color chooseColor() {
        return colorRank().get(0);
    }
}
