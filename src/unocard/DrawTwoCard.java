package unocard;

/**
 * @author Yiyin Shen
 */
public class DrawTwoCard extends Card {

    public DrawTwoCard(Color color) {
        super(color, Type.DRAW_TWO);
        assert !isWild(color);
    }

    /**
     * Draw two cards can match cards with the same color (also wild cards which declared color)
     * and any other draw two cards.
     * @return true if this.card matches card
     */
    @Override
    public boolean isMatched(Card card) {
        if (card.getType() == Type.DRAW_FOUR && card.getEffectDone() == false) {
            return false;
        }
        return this.color == card.color || card.type == Type.DRAW_TWO;
    }

    @Override
    public String printPNG() {
        switch (this.color) {
            case YELLOW:
                return "/cardImages/yellowdrawtwo.png";
            case RED:
                return "/cardImages/reddrawtwo.png";
            case BLUE:
                return "/cardImages/bluedrawtwo.png";
            case GREEN:
                return "/cardImages/greendrawtwo.png";
            default:
                return null;
        }
    }
}
