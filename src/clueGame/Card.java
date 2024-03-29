package clueGame;

import java.util.ArrayList;

public class Card {
    private String cardName;
    private CardType type;

    public Card(String name, CardType type) {
        cardName = name;
        this.type = type;
    }

    public boolean equals(Card target) {
        return false;
    }

    public CardType getCardType() {
        return this.type;
    }
}
