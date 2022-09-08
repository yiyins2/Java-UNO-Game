package unocard;

/**
 * @author Yiyin Shen
 */
public class NumberCard extends Card {
    public NumberCard(Color color, Type type) {
        super(color, type);
        assert !isWild(color) && isNumber(type);
    }

    /**
     * Number cards can match cards with the same number or the same color (also wild cards which declared color).
     * @return true if this.card matches top card
     */
    @Override
    public boolean isMatched(Card card) {
        if ((card.getType() == Type.DRAW_TWO || card.getType() == Type.DRAW_FOUR) && card.getEffectDone() == false) {
            return false;
        }
        if (isNumber(card.type)) {
            return this.color == card.color || this.type == card.type;
        } else {
            return this.color == card.color;
        }
    }

    @Override
    public String printPNG() {
        String str;
        switch (this.color) {
            case YELLOW:
                str = "/cardImages/yellow";
                break;
            case RED:
                str = "/cardImages/red";
                break;
            case BLUE:
                str = "/cardImages/blue";
                break;
            case GREEN:
                str = "/cardImages/green";
                break;
            default:
                str = "";
        }
        switch (this.type) {
            case ZERO:
                str += "0.png";
                break;
            case ONE:
                str += "1.png";
                break;
            case TWO:
                str += "2.png";
                break;
            case THREE:
                str += "3.png";
                break;
            case FOUR:
                str += "4.png";
                break;
            case FIVE:
                str += "5.png";
                break;
            case SIX:
                str += "6.png";
                break;
            case SEVEN:
                str += "7.png";
                break;
            case EIGHT:
                str += "8.png";
                break;
            case NINE:
                str += "9.png";
                break;
            default:
        }
        return str;
    }
}