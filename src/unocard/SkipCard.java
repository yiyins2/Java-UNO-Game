package unocard;

/**
 * @author Yiyin Shen
 */
public class SkipCard extends Card {
    public SkipCard(Color color) {
        super(color, Type.SKIP);
        assert !isWild(color);
    }

    /**
     * Skip card can match cards with the same color (also wild cards which declared color).
     * @return true if this.card matches card
     */
    @Override
    public boolean isMatched(Card card) {
        if ((card.getType() == Type.DRAW_TWO || card.getType() == Type.DRAW_FOUR) && card.getEffectDone() == false) {
            return false;
        }
        return this.color == card.color;
    }

    @Override
    public String printPNG() {
        switch (this.color) {
            case YELLOW:
                return "/cardImages/yellowskip.png";
            case RED:
                return "/cardImages/redskip.png";
            case BLUE:
                return "/cardImages/blueskip.png";
            case GREEN:
                return "/cardImages/greenskip.png";
            default:
                return null;
        }
    }
}
