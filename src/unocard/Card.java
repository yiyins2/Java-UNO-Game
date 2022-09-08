package unocard;

/**
 * Parent class for NumberCard, ReverseCard, SkipCard, DrawTwoCard,
 * WildCard, and WildDrawFourCard.
 * @author Yiyin Shen
 */
public abstract class Card {
    public Color color;
    public Type type;
    public boolean effectDone;

    public Card(Color color, Type type) {
        this.color = color;
        this.type = type;
        this.effectDone = false;
    }

    public Color getColor() {
        return this.color;
    }

    public Type getType() { return this.type; }

    public void setColor(Color color) { this.color = color; }

    public boolean getEffectDone() {
        return this.effectDone;
    }

    public void setEffectDone() { this.effectDone = true; }

    public void setEffectNotDone() {
        this.effectDone = false;
    }

    /**
     * @return true if color is WILD, false otherwise
     */
    public static boolean isWild(Color color) {
        return color == Color.WILD;
    }

    /**
     * @return true if type is ZERO through NINE, false otherwise
     */
    public static boolean isNumber(Type type) {
        return type != Type.SKIP &&
                type != Type.REVERSE &&
                type != Type.DRAW_TWO &&
                type != Type.WILD &&
                type != Type.DRAW_FOUR;
    }

    /**
     * @return true if given card is wild
     */
    public boolean isWild() {
        return isWild(this.color);
    }

    public boolean isNumber() { return isNumber(this.type); }

    /**
     * @return true if this.card matches card; false otherwise
     */
    public abstract boolean isMatched(Card card);

    /**
     * print card information
     */
    public String printCard() {
        return this.getColor().toString() + "+" + this.getType().toString();
    }

    /**
     * @return The PNG corresponding to the card
     */
    public abstract String printPNG();
}