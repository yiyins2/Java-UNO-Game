package unocard;

/**
 * @author Yiyin Shen
 */
public class ReverseCard extends Card {

    public ReverseCard(Color color) {
        super(color, Type.REVERSE);
        assert !isWild(color);
    }

    /**
     * Reverse card can match cards with the same color (also wild cards which declared color).
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
                return "/cardImages/yellowreverse.png";
            case RED:
                return "/cardImages/redreverse.png";
            case BLUE:
                return "/cardImages/bluereverse.png";
            case GREEN:
                return "/cardImages/greenreverse.png";
            default:
                return null;
        }
    }
}
