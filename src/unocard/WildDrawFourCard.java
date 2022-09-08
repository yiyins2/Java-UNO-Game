package unocard;

/**
 * @author Yiyin Shen
 */
public class WildDrawFourCard extends Card {
    public WildDrawFourCard() {
        super(Color.WILD, Type.DRAW_FOUR);
    }

    /**
     * Wild draw four card can match any card.
     * @return true if this.card matches card
     */
    @Override
    public boolean isMatched(Card card) {
        return true;
    }

    @Override
    public String printPNG() {
        return "/cardImages/wilddrawfour.png";
    }
}
