package unocard;

/**
 * @author Yiyin Shen
 */
public class WildCard extends Card {
    public WildCard() {
        super(Color.WILD, Type.WILD);
    }

    /**
     * Wild cards can match any card.
     * @return true if this.card matches card
     */
    @Override
    public boolean isMatched(Card card) {
        if ((card.getType() == Type.DRAW_TWO || card.getType() == Type.DRAW_FOUR) && card.getEffectDone() == false) {
            return false;
        }
        return true;
    }

    @Override
    public String printPNG() {
        return "/cardImages/wild.png";
    }
}
