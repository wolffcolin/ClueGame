package clueGame;

import java.util.ArrayList;

public class Card {
    private String cardName;
    private CardType type;

    //constructor
    public Card(String name, CardType type) {
        cardName = name;
        this.type = type;
    }

    //checks if this card has the same type (weapon,room or person) as another
    public boolean equals(Card target) {
        if (this.getCardType() == target.getCardType()) {
            return true;
        }
        
        return false;
    }

    //returns card type
    public CardType getCardType() {
        return this.type;
    }
}
