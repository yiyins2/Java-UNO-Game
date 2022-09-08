package unogame;

import org.junit.jupiter.api.Test;
import unocard.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AITest {
    @Test
    void baselineAI() {
        Player p = new BaselineAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.RED, Type.EIGHT));
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        p.setHand(hand);
        Card bestCard = p.chooseCard(new NumberCard(Color.RED, Type.THREE));
        assertTrue(bestCard.getColor() == Color.RED && bestCard.getType() == Type.EIGHT);
    }

    @Test
    void baselineAINoCardToPlay() {
        Player p = new BaselineAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        p.setHand(hand);
        Card bestCard = p.chooseCard(new NumberCard(Color.RED, Type.THREE));
        assertTrue(bestCard == null);
    }

    @Test
    void baselineAIChooseColor() {
        Player p = new BaselineAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        p.setHand(hand);
        assertTrue(p.chooseColor() == Color.GREEN);
    }

    @Test
    void strategicAI() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        hand.add(new DrawTwoCard(Color.YELLOW));
        p.setHand(hand);
        Card bestCard = p.chooseCard(new DrawTwoCard(Color.RED));
        assertTrue(bestCard.getColor() == Color.YELLOW && bestCard.getType() == Type.DRAW_TWO);
    }

    @Test
    void strategicAITopCardIsBlack() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        hand.add(new DrawTwoCard(Color.YELLOW));
        p.setHand(hand);
        Card bestCard = p.chooseCard(new WildDrawFourCard());
        assertTrue(bestCard == null);
    }

    @Test
    void strategicAIPopularNumCard() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        hand.add(new NumberCard(Color.RED, Type.THREE));
        hand.add(new NumberCard(Color.RED, Type.FIVE));
        hand.add(new DrawTwoCard(Color.YELLOW));
        hand.add(new WildDrawFourCard());
        hand.add(new WildCard());
        p.setHand(hand);
        Card bestCard = p.chooseCard(new NumberCard(Color.YELLOW, Type.FIVE));
        assertTrue(bestCard.getColor() == Color.RED && bestCard.getType() == Type.FIVE);
    }

    @Test
    void strategicAIPopularOtherCard() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        hand.add(new NumberCard(Color.RED, Type.THREE));
        hand.add(new DrawTwoCard(Color.BLUE));
        p.setHand(hand);
        Card bestCard = p.chooseCard(new NumberCard(Color.BLUE, Type.ONE));
        assertTrue(bestCard.getColor() == Color.BLUE && bestCard.getType() == Type.DRAW_TWO);
    }

    @Test
    void strategicAINoCardToPlay() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        p.setHand(hand);
        Card bestCard = p.chooseCard(new NumberCard(Color.BLUE, Type.ONE));
        assertTrue(bestCard == null);
    }

    @Test
    void strategicAIPlayWild() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new WildCard());
        p.setHand(hand);
        Card bestCard = p.chooseCard(new NumberCard(Color.BLUE, Type.ONE));
        assertTrue(bestCard.getColor() == Color.WILD && bestCard.getType() == Type.WILD);
    }

    @Test
    void strategicAIPlayWildDrawFour() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        hand.add(new WildDrawFourCard());
        p.setHand(hand);
        Card bestCard = p.chooseCard(new NumberCard(Color.BLUE, Type.ONE));
        assertTrue(bestCard.getColor() == Color.WILD && bestCard.getType() == Type.DRAW_FOUR);
    }

    @Test
    void strategicAIPlayWildDrawFourAfterStreak() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.GREEN, Type.FIVE));
        hand.add(new WildDrawFourCard());
        p.setHand(hand);
        Card bestCard = p.chooseCard(new DrawTwoCard(Color.RED));
        assertTrue(bestCard.getColor() == Color.WILD && bestCard.getType() == Type.DRAW_FOUR);
    }

    @Test
    void strategicAIChooseColor() {
        Player p = new StrategicAIPlayer();
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new NumberCard(Color.BLUE, Type.FIVE));
        hand.add(new NumberCard(Color.BLUE, Type.THREE));
        hand.add(new DrawTwoCard(Color.GREEN));
        p.setHand(hand);
        assertTrue(p.chooseColor() == Color.BLUE);
    }
}
